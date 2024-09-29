package covoit.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;

import covoit.entities.UserAccount;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.stream.Collectors;

/**
 * Service pour la gestion des tokens JWT (JSON Web Tokens).
 * Permet de créer, vérifier, et extraire des informations des tokens JWT.
 */
@Service
public class JwtService {

    private  String SECRET_KEY;

    private static final long JWT_EXPIRATION_TIME = 86400000; // 1 jour en millisecondes
    /**
     * Constructeur du service JWT.
     *
     * @param secretKey La clé secrète utilisée pour signer les tokens JWT.
     */
    @Autowired
    public JwtService(@Value("${jwt.secret}") String secretKey) {
        this.SECRET_KEY = secretKey; // Initialisation dans le constructeur
    }
    public boolean validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer("YourAppName") // Changez pour un émetteur plus significatif
                    .build();
            verifier.verify(token);
            return true;
        } catch (JWTVerificationException exception) {
            // Logger l'exception ici si nécessaire
            return false;
        }
    }

    public String generateToken(UserAccount user) {
        String token = null; // Initialisation de la variable
        try {
            token = JWT.create()
                    .withSubject(user.getUserName())
                    .withClaim("email", user.getEmail())
                    .withArrayClaim("roles", user.getAuthorities().stream()
                            .map(GrantedAuthority::getAuthority)
                            .toArray(String[]::new))
                    .withIssuedAt(new Date())
                    .withExpiresAt(new Date(System.currentTimeMillis() + JWT_EXPIRATION_TIME))
                    .sign(Algorithm.HMAC256(SECRET_KEY));
            
            System.out.println("Generated Token: " + token); // Affichage du token généré
        } catch (Exception e) {
            e.printStackTrace();
        }
        return token; // Retourne le token généré
    }


    public DecodedJWT verifyToken(String token) throws JWTCreationException {
        Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
        JWTVerifier verifier = JWT.require(algorithm).build();
        try {
            return verifier.verify(token);
        } catch (JWTDecodeException e) {
            throw new JWTCreationException("Invalid token", e);
        }
    }

    public String getUsernameFromToken(String token) throws JWTCreationException {
        return verifyToken(token).getSubject();
    }

    public String[] getRolesFromToken(String token) throws JWTCreationException {
        try {
            return verifyToken(token).getClaim("roles").asArray(String.class);
        } catch (JWTDecodeException e) {
            // Gérer l'exception et éventuellement relancer une exception personnalisée
            throw new JWTCreationException("Failed to extract roles from token", e);
        }
    }
}
