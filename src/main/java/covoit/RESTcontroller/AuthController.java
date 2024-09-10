package covoit.RESTcontroller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import covoit.dtos.LoginRequestDto;
import covoit.dtos.LoginResponse;
import covoit.entities.UserAccount;
import covoit.exception.AnomalieException;
import covoit.services.JwtService;
import covoit.services.UserAccountService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserAccountService userAccountService;
	private JwtService jwtService;
    // Injection des services
    public AuthController(UserAccountService authService, JwtService jwtService) {
        this.userAccountService = authService; 
        this.jwtService = jwtService;
    }
    public AuthController(UserAccountService userAccountService) {
        this.userAccountService = userAccountService;
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto loginRequestDto) {
        try {
            // Authentifier l'utilisateur
            UserAccount user = userAccountService.authenticate(loginRequestDto.getUsername(), loginRequestDto.getPassword());

            // Générer le token
            String token = jwtService.generateToken(user.asUserDetails());

            // Retourner la réponse avec le token
            return ResponseEntity.ok()
                                 .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                                 .body(new LoginResponse("Login successful"));
        } catch (AnomalieException e) {
            // Retourner une erreur 401 Unauthorized si l'authentification échoue
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                                 .body(new LoginResponse(e.getMessage()));
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
