package covoit.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Service pour la gestion des tokens JWT (JSON Web Tokens).
 * Permet de créer, vérifier, et extraire des informations des tokens JWT.
 */
@Service
public class JwtService {

    private final String SECRET_KEY;

    /**
     * Constructeur du service JWT.
     *
     * @param secretKey La clé secrète utilisée pour signer les tokens JWT.
     */
    public JwtService(@Value("${jwt.secret}") String secretKey) {
        this.SECRET_KEY = secretKey;
    }

    public boolean validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer("your-issuer") // Remplacez par votre émetteur de token
                    .build();
            verifier.verify(token);
            return true;
        } catch (JWTVerificationException exception) {
            return false;
        }
    }

    public String generateToken(String username, String[] roles) {
        Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);

        return JWT.create()
                .withSubject(username)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 heures
                .withArrayClaim("roles", roles)
                .sign(algorithm);
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
        return verifyToken(token).getClaim("roles").asArray(String.class);
    }
}
