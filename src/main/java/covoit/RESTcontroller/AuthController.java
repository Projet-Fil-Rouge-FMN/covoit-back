package covoit.RESTcontroller;

import covoit.dtos.LoginRequestDto;
import covoit.dtos.LoginResponseDto; // Assurez-vous d'avoir cette classe
import covoit.entities.UserAccount;
import covoit.exception.AnomalieException;
import covoit.services.JwtService;
import covoit.services.UserAccountService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserAccountService userAccountService;
    private final JwtService jwtService; // Ajoutez cette ligne

    public AuthController(UserAccountService userAccountService, JwtService jwtService) { // Ajoutez jwtService ici
        this.userAccountService = userAccountService;
        this.jwtService = jwtService; // Initialisez jwtService
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto loginRequest) throws AnomalieException {
        UserAccount user = userAccountService.login(loginRequest.getUsername(), loginRequest.getPassword());

        // Vérifiez si l'utilisateur a des autorités définies
        List<GrantedAuthority> authorities = user.getAuthorities();
        System.out.println("Authorities: " + authorities);
        if (authorities == null || authorities.isEmpty()) {
            throw new AnomalieException("L'utilisateur n'a pas d'autorités définies.");
        }

        // Génération du token JWT
        String token = jwtService.generateToken(user);

        // Créez un objet de réponse avec le token et d'autres informations si nécessaire
        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        response.put("username", user.getUserName());
        response.put("authorities", authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList()));

        // Retournez une réponse avec le code HTTP 200 et les données
        return ResponseEntity.ok(response);
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
