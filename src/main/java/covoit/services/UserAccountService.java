
package covoit.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import covoit.dtos.UserAccountDto;
import covoit.entities.CustomUserDetails;
import covoit.entities.UserAccount;
import covoit.exception.AnomalieException;
import covoit.repository.UserAccountRepository;
import org.springframework.security.core.Authentication;



/**
 * Service interface for managing user accounts.
 */
@Service
public class UserAccountService {

	@Autowired
	private UserAccountRepository repository;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	public class UserAlreadyExistsException extends RuntimeException {
	    public UserAlreadyExistsException(String message) {
	        super(message);
	    }
	}
	public List<UserAccountDto> findAll() {
		List<UserAccount> users = new ArrayList<>();
		users = repository.findAll();
		List<UserAccountDto> usersDto = new ArrayList<>();
		for (UserAccount item : users) {
			usersDto.add(new UserAccountDto().toDto(item));
		}
		return usersDto;
	}

	public UserAccountDto findById(int id) {
	    return repository.findById(id)
	            .map(userAccount -> new UserAccountDto().toDto(userAccount))
	            .orElse(null); // Retourner null si l'utilisateur n'existe pas
	}

	/**
	 * Update the User corresponding to the id given
	 * 
	 * @param id : Id given
	 * @return A confirmation message
	 */
	public boolean update(int id, UserAccountDto userDto) {
	    return repository.findById(id).map(userDB -> {
	        // Map le DTO en entité
	        UserAccount change = userDto.toBean(userDto);
	        // Mettre à jour uniquement les champs nécessaires
	        userDB.setUserName(change.getUserName());
	        userDB.setLastName(change.getLastName());
	        userDB.setEmail(change.getEmail());
	        userDB.setDriverLicence(change.isDriverLicence());
	        userDB.setPassword(change.getPassword());
	        userDB.setAuthorities(change.getAuthorities());
	        
	        repository.save(userDB);
	        return true;
	    }).orElse(false); // Retourner false si l'utilisateur n'existe pas
	}


	/**
	 * Delete the Brand corresponding to the id given
	 * 
	 * @param id : Id given
	 * @return A confirmation message
	 */
	public boolean deleteBrand(int id) {
		Optional<UserAccount> userDB = repository.findById(id);
		if (userDB == null) {
			return false;
		}
		repository.deleteById(id);
		return true;
	}

	public UserAccount login(String username, String rawPassword) throws AnomalieException {
		// Rechercher l'utilisateur
		UserAccount user = repository.findByUserName(username);

		// Vérifier si l'utilisateur existe
		if (user == null) {
			throw new AnomalieException("Nom d'utilisateur ou mot de passe incorrect");
		}

		// Vérifier le mot de passe
		if (bCryptPasswordEncoder.matches(rawPassword, user.getPassword())) {
			return user; // Authentification réussie
		} else {
			throw new AnomalieException("Nom d'utilisateur ou mot de passe incorrect");
		}
	}

	/**
	 * Supprimer un utilisateur par ID après vérification des droits.
	 *
	 * @param id ID de l'utilisateur à supprimer
	 * @return un message de confirmation ou une exception si la suppression est
	 *         interdite
	 */
	public boolean deleteUserById(int id) {
	    System.out.println("Attempting to delete user with ID: " + id);

	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    if (authentication == null || !authentication.isAuthenticated()) {
	        System.err.println("User is not authenticated");
	        throw new RuntimeException("L'utilisateur courant n'est pas authentifié.");
	    }

	    CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
	    if (userDetails.getId() == id) {
	        System.err.println("User cannot delete their own account");
	        throw new RuntimeException("Vous ne pouvez pas supprimer votre propre compte.");
	    }

	    if (repository.existsById(id)) {
	        System.out.println("User exists, proceeding with deletion");
	        repository.deleteById(id);
	        return true;
	    } else {
	        System.err.println("User with ID " + id + " not found");
	        return false;
	    }
	}


	public void create(UserAccount userAccount) {
	    // Vérifier si l'utilisateur existe déjà en utilisant Optional
	    Optional<UserAccount> existingUser = Optional.ofNullable(repository.findByUserName(userAccount.getUserName()));
	    
	    if (existingUser.isPresent()) {
	        throw new RuntimeException("User already exists");
	    }

	    // Encoder le mot de passe avant de sauvegarder l'utilisateur
	    userAccount.setPassword(bCryptPasswordEncoder.encode(userAccount.getPassword()));
	    repository.save(userAccount);
	}

}