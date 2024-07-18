package covoit.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import covoit.dtos.CarpoolDTO;
import covoit.dtos.UserAccountDTO;
import covoit.services.UserAccountService;




/**
 * 
 */
@RestController
@RequestMapping("/user")
public class UserAccountController {

    @Autowired
    private UserAccountService userAccountService;

    @PostMapping("/register")
    public void registerUser(@RequestBody UserAccountDTO userDTO) {
        userAccountService.registerUser(userDTO);
    }

    @PutMapping("/update")
    public void updateUser(@RequestBody UserAccountDTO userDTO) {
        userAccountService.updateUser(userDTO);
    }

    @GetMapping("/{id}")
    public UserAccountDTO findById(@PathVariable Long id) {
        return userAccountService.findById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userAccountService.deleteUser(id);
    }

    @GetMapping("/all")
    public List<UserAccountDTO> findAllUsers() {
        return userAccountService.findAllUsers();
    }

    @PostMapping("/login")
    public void login(@RequestParam String email, @RequestParam String password) {
        userAccountService.login(email, password);
    }

    @PostMapping("/logout/{id}")
    public void logout(@PathVariable Long id) {
        userAccountService.logout(id);
    }

    @GetMapping("/{userId}/carpools")
    public List<CarpoolDTO> getCarpoolInfo(@PathVariable Long userId) {
        return userAccountService.getCarpoolInfo(userId);
    }

    @PostMapping("/{userId}/book-carpool")
    public void bookCarpool(@PathVariable Long userId, @RequestParam Long carpoolId) {
        userAccountService.bookCarpool(carpoolId, userId);
    }

    @DeleteMapping("/{userId}/cancel-carpool")
    public void cancelCarpool(@PathVariable Long userId, @RequestParam Long carpoolId) {
        userAccountService.deleteBookingCarpool(carpoolId, userId);
    }
}
