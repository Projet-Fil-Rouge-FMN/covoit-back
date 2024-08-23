package covoit.RESTcontroller;

import covoit.dtos.LoginRequest;
import covoit.entities.UserAccount;
import covoit.exception.AnomalieException;
import covoit.services.UserAccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserAccountService userAccountService;

    public AuthController(UserAccountService userAccountService) {
        this.userAccountService = userAccountService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest, HttpServletRequest request) {
        try {
            UserAccount authenticatedUser = userAccountService.login(loginRequest.getUserName(), loginRequest.getPassword());
            return ResponseEntity.ok(authenticatedUser);
        } catch (AnomalieException e) {
            return ResponseEntity.status(401).body(e.getMessage());
        }
    }
    

    @GetMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate(); // Invalider la session
        }
        return ResponseEntity.ok("Déconnecté");
    }

  
}
