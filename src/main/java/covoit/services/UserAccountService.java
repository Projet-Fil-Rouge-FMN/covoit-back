
package covoit.services;

import java.util.ArrayList;
import java.util.List;

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

import org.springframework.stereotype.Service;

/**
 * Service interface for managing user accounts.
 */
@Service
public class UserAccountService {

	@Autowired
	private UserAccountRepository repository;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

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
		UserAccount userAccount = repository.findById(id);
		if (userAccount == null) {
			return null;
		}
		return new UserAccountDto().toDto(userAccount);
	}

	/**
	 * Update the User corresponding to the id given
	 * 
	 * @param id : Id given
	 * @return A confirmation message
	 */
	public boolean update(int id, UserAccountDto userDto) {
		UserAccount userDB = repository.findById(id);

		if (userDB == null) {
			return false;
		}

		// Mettre à jour uniquement les nécessaires
		UserAccount change = userDto.toBean(userDto);
		userDB.setUserName(change.getUserName());
		userDB.setLastName(change.getLastName());
		userDB.setEmail(change.getEmail());
		userDB.setDriverLicence(change.isDriverLicence());
		userDB.setPassword(change.getPassword());
		userDB.setAuthorities(change.getAuthorities());

		repository.save(userDB);
		return true;
	}

	public void create(UserAccount userAccount) {
		// Logique pour créer un utilisateur
		if (repository.findByUserName(userAccount.getUserName()) != null) {
			throw new RuntimeException("User already exists");
		}
		System.out.println(userAccount);
		userAccount.setPassword(new BCryptPasswordEncoder().encode(userAccount.getPassword()));
		repository.save(userAccount);
	}

	/**
	 * Delete the Brand corresponding to the id given
	 * 
	 * @param id : Id given
	 * @return A confirmation message
	 */
	public boolean deleteBrand(int id) {
		UserAccount userDB = repository.findById(id);
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
		// Obtenez l'utilisateur courant à partir de SecurityContext
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Object principal = authentication.getPrincipal();

		int currentUserId;
		if (principal instanceof UserDetails) {
			// Extraire l'ID comme ceci :
			UserDetails userDetails = (UserDetails) principal;
			// Assurez-vous que la méthode getId() existe et renvoie un int
			currentUserId = ((CustomUserDetails) userDetails).getId();
		} else {
			throw new RuntimeException("L'utilisateur courant n'est pas authentifié.");
		}

		// Vérifiez si l'utilisateur essaie de supprimer son propre compte
		if (currentUserId == id) {
			throw new RuntimeException("Vous ne pouvez pas supprimer votre propre compte.");
		}

		// Recherchez l'utilisateur à supprimer
		UserAccount userDB = repository.findById(id);
		if (userDB == null) {
			return false;
		}
		repository.deleteById(id);
		return true;
	}

}
