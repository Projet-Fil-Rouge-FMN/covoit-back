package covoit.RESTcontroller;

import covoit.dtos.LoginRequestDto;
import covoit.dtos.UserAccountDto;
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

    /**
     * Endpoint pour la connexion des utilisateurs.
     * 
     * @param loginRequest un objet contenant le nom d'utilisateur et le mot de passe
     * @return un objet UserAccountDto si la connexion est réussie, sinon un message d'erreur
     */
    @PostMapping("/login")
   
    public ResponseEntity<?> login(@RequestBody LoginRequestDto loginRequest) {
        try {
            UserAccountDto authenticatedUser = userAccountService.login(loginRequest);
            return ResponseEntity.ok(authenticatedUser); // Répond avec le DTO de l'utilisateur authentifié
        } catch (AnomalieException e) {
            return ResponseEntity.status(401).body(e.getMessage()); // Répond avec une erreur 401 en cas d'échec
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
