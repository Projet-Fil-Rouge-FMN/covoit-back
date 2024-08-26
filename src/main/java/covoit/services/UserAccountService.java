package covoit.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import covoit.dtos.CarpoolBookingDto;
import covoit.dtos.LoginRequestDto;
import covoit.dtos.UserAccountDto;
import covoit.entities.Carpool;
import covoit.entities.UserAccount;
import covoit.exception.AnomalieException;
import covoit.repository.CarpoolRepository;
import covoit.repository.UserAccountRepository;

/**
 * Service class for managing user accounts.
 * This class provides methods for CRUD operations on UserAccounts and handling user login.
 */
@Service
public class UserAccountService {

    @Autowired
    private UserAccountRepository repository;
    @Autowired
    private CarpoolRepository carpoolRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    /**
     * Retrieves all user accounts.
     * 
     * @return a list of UserAccountDto representing all users
     */
    public List<UserAccountDto> findAll() {
        List<UserAccount> users = repository.findAll();
        List<UserAccountDto> usersDto = new ArrayList<>();
        for (UserAccount item : users) {
            usersDto.add(new UserAccountDto().toDto(item));
        }
        return usersDto;
    }

    /**
     * Retrieves a user account by its ID.
     * 
     * @param id the ID of the user account to find
     * @return the corresponding UserAccountDto if found, otherwise null
     */
    public UserAccountDto findById(int id) {
        UserAccount userAccount = repository.findById(id);
        if (userAccount == null) {
            return null;
        }
        return new UserAccountDto().toDto(userAccount);
    }

    /**
     * Updates a user account with the given ID using the data provided in the DTO.
     * 
     * @param id the ID of the user account to update
     * @param object the UserAccountDto containing the new data
     * @return true if the update was successful, false otherwise
     */
    public boolean update(int id, UserAccountDto object) {
        UserAccount userDB = repository.findById(id);

        if (userDB == null) {
            return false;
        }

        // Copy data from DTO to entity
        userDB.setUserName(object.getUserName());
        userDB.setEmail(object.getEmail());
        userDB.setLastName(object.getLastName());
        userDB.setDriverLicence(object.isDriverLicence());

        if (!bCryptPasswordEncoder.matches(object.getPassword(), userDB.getPassword())) {
            userDB.setPassword(bCryptPasswordEncoder.encode(object.getPassword()));
        }

        repository.save(userDB);
        return true;
    }

    /**
     * Creates a new user account.
     * 
     * @param userAccount the UserAccount entity to be created
     * @throws AnomalieException if a user with the same username already exists
     */
    public void create(UserAccount userAccount) throws AnomalieException {
        if (repository.findByUserName(userAccount.getUserName()) != null) {
            throw new AnomalieException("User already exists");
        }
        userAccount.setPassword(bCryptPasswordEncoder.encode(userAccount.getPassword()));
        repository.save(userAccount);
    }

    /**
     * Deletes a user account with the given ID.
     * 
     * @param id the ID of the user account to delete
     * @return true if the deletion was successful, false otherwise
     */
    public boolean delete(int id) {
        UserAccount userDB = repository.findById(id);
        if (userDB == null) {
            return false;
        }
        repository.deleteById(id);
        return true;
    }

    /**
     * Handles the login of a user by checking the username and password.
     * 
     * @param loginRequest the LoginRequestDto containing the username and password
     * @return the UserAccountDto of the authenticated user
     * @throws AnomalieException if the username or password is incorrect
     */
    public UserAccountDto login(LoginRequestDto loginRequest) throws AnomalieException {
        UserAccount user = repository.findByUserName(loginRequest.getUserName());

        if (user == null) {
            throw new AnomalieException("Username ou mot de passe incorrect");
        }

        if (bCryptPasswordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            return new UserAccountDto().toDto(user);  // Convertit l'utilisateur en DTO pour la réponse
        } else {
            throw new AnomalieException("Username ou mot de passe incorrect");
        }
    }
    /**
     * Cancels a carpool booking for a specific user.
     * 
     * @param carpoolId the ID of the carpool to cancel
     * @param userId the ID of the user who made the booking
     * @throws AnomalieException if the carpool or user is not found or other issues occur
     */
    /**
     * Annule une réservation de covoiturage pour un utilisateur.
     * 
     * @param bookingDto les informations de réservation à annuler
     * @throws AnomalieException si l'utilisateur ou le covoiturage n'est pas trouvé
     */
    public void cancelCarpoolBooking(CarpoolBookingDto bookingDto) throws AnomalieException {
        // Recherche de l'utilisateur
        UserAccount user = repository.findById(bookingDto.getUserId());
        if (user == null) {
            throw new AnomalieException("User not found");
        }

        // Recherche du covoiturage
        Carpool carpool = carpoolRepository.findById(bookingDto.getCarpoolId());
        if (carpool == null) {
            throw new AnomalieException("Carpool not found");
        }

        // Suppression du covoiturage de l'utilisateur
        user.getCarpools().remove(carpool);
        repository.save(user);

        // Mise à jour des places disponibles du covoiturage
        carpool.setAvailableSeat(carpool.getAvailableSeat() + 1);
        carpoolRepository.save(carpool);
    }
}
