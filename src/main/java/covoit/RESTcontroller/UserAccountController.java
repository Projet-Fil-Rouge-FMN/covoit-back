package covoit.RESTcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import covoit.dtos.UserAccountDto;
import covoit.entities.UserAccount;
import covoit.services.UserAccountService;


@RestController
@RequestMapping(value = "/user", produces = "application/json")
public class UserAccountController {

    @Autowired
    private UserAccountService userAccountService;
 

    @GetMapping
    public ResponseEntity<List<UserAccountDto>> findAll() {
        List<UserAccountDto> users = userAccountService.findAll();
        return ResponseEntity.ok(users);
    }
    @GetMapping("/{id}")
    public ResponseEntity<UserAccountDto> findUserById(@PathVariable int id) {
        UserAccountDto user = userAccountService.findById(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }
    @PostMapping("/register")
    public ResponseEntity<String> create(@RequestBody UserAccountDto userAccountDto) {
        // Convertir UserAccountDto en UserAccount	
        UserAccount userAccount = userAccountDto.toBean(userAccountDto);
        // Appeler la méthode create avec l'entité UserAccount
        userAccountService.create(userAccount);
        return ResponseEntity.ok("User created successfully");
    }

    @PutMapping("/update/{id}")
    public void update(@PathVariable int id, @RequestBody UserAccountDto userDto) {
        userAccountService.update(id, userDto);
    }
    

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable int id) {
    	    try {
    	        boolean deleted = userAccountService.deleteUserById(id);
    	        if (deleted) {
    	            return ResponseEntity.ok("User deleted successfully");
    	        } else {
    	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
    	        }
    	    } catch (Exception e) {
    	        // Log the exception details
    	        e.printStackTrace();
    	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete user");
    	    }
    	}
 
    @DeleteMapping("/{userId}/cancel-carpool")
    public void cancelCarpool(@PathVariable int userId, @RequestParam int carpoolId) {
     //  userAccountService.deleteBookingCarpool(carpoolId, userId);
    }
}