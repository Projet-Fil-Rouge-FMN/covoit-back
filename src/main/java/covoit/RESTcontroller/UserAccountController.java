package covoit.RESTcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
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
import covoit.exception.AnomalieException;
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
    public ResponseEntity<UserAccountDto> findById(@PathVariable int id) {
        UserAccountDto user = userAccountService.findById(id);
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
    public ResponseEntity<Void> deleteUser(@PathVariable int id) {
        boolean deleted = userAccountService.deleteUserById(id);
        if (deleted) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
 
    @DeleteMapping("/{userId}/cancel-carpool")
    public void cancelCarpool(@PathVariable int userId, @RequestParam int carpoolId) {
     //  userAccountService.deleteBookingCarpool(carpoolId, userId);
    }
}