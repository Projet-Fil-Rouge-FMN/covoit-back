package covoit.RESTcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import covoit.dtos.CarpoolBookingDto;
import covoit.dtos.CarpoolDto;
import covoit.dtos.UserAccountDto;
import covoit.entities.UserAccount;
import covoit.exception.AnomalieException;
import covoit.repository.CarpoolRepository;
import covoit.services.UserAccountService;

/**
 * Controller for managing user accounts.
 * This class provides endpoints for CRUD operations on user accounts and other related actions.
 */
@RestController
@RequestMapping(value = "/user", produces = "application/json")
public class UserAccountController {

    @Autowired
    private UserAccountService userAccountService;
    
    @Autowired
    private CarpoolRepository carpoolRepository;

    /**
     * Retrieves all user accounts.
     * 
     * @return a list of UserAccountDto representing all users
     */
    @GetMapping("/")
    public List<UserAccountDto> findAll() {
        return userAccountService.findAll();
    }

    /**
     * Retrieves a user account by its ID.
     * 
     * @param id the ID of the user account to find
     * @return the UserAccountDto of the user with the specified ID
     */
    @GetMapping("/{id}")
    public UserAccountDto findById(@PathVariable int id) {
        return userAccountService.findById(id);
    }

    /**
     * Registers a new user.
     * 
     * @param userAccountDto the data of the user to register
     * @return a response indicating whether the user was created successfully or not
     */
    @PostMapping("/register")
    public ResponseEntity<String> create(@RequestBody UserAccountDto userAccountDto) {
        try {
            // Convert UserAccountDto to UserAccount entity
            UserAccount userAccount = userAccountDto.toBean(userAccountDto);
            // Call the service method to create the user account
            userAccountService.create(userAccount);
            return ResponseEntity.ok("User created successfully");
        } catch (AnomalieException e) {
            // Return an error response with a message indicating failure
            return ResponseEntity.status(400).body("User creation failed: " + e.getMessage());
        } catch (Exception e) {
            // Handle unexpected exceptions
            return ResponseEntity.status(500).body("An unexpected error occurred: " + e.getMessage());
        }
    }

    /**
     * Updates an existing user account.
     * 
     * @param id the ID of the user account to update
     * @param userDto the UserAccountDto containing the updated data
     */
    @PostMapping("/update/{id}")
    public void update(@PathVariable int id, @RequestBody UserAccountDto userDto) {
        userAccountService.update(id, userDto);
    }

    /**
     * Deletes a user account by its ID.
     * 
     * @param id the ID of the user account to delete
     */
    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable int id) {
        userAccountService.delete(id);
    }

    /**
     * Annule une réservation de covoiturage pour un utilisateur.
     * 
     * @param bookingDto les informations de réservation à annuler
     * @return une réponse indiquant si l'annulation a réussi
     */
    @DeleteMapping("/cancel-carpool")
    public ResponseEntity<String> cancelCarpool(@RequestBody CarpoolBookingDto bookingDto) {
        try {
            userAccountService.cancelCarpoolBooking(bookingDto);
            return ResponseEntity.ok("Carpool booking cancelled successfully");
        } catch (AnomalieException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An unexpected error occurred: " + e.getMessage());
        }
    }
}
