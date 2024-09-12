package covoit.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

/**
 * Service pour la gestion des tokens JWT (JSON Web Tokens).
 * Permet de créer, vérifier, et extraire des informations des tokens JWT.
 */
@Service
public class JwtService {

    private String SECRET_KEY;

    /**
     * Constructeur du service JWT.
     *
     * @param secretKey La clé secrète utilisée pour signer les tokens JWT.
     */
    public JwtService(@Value("${jwt.secret}") String secretKey) {
        this.SECRET_KEY = secretKey;
    }

    /**
     * Génère un token JWT avec le nom d'utilisateur et les rôles spécifiés.
     *
     * @param username Le nom d'utilisateur pour lequel générer le token.
     * @param roles Les rôles associés à l'utilisateur.
     * @return Le token JWT généré.
     */
    public String generateToken(String username, String[] role) {
        Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);

        return JWT.create()
                .withSubject(username)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 heures
                .withArrayClaim("role", role) // Ajouter les rôles dans les revendications
                .sign(algorithm);
    }

    /**
     * Vérifie et décode un token JWT.
     *
     * @param token Le token JWT à vérifier.
     * @return Le token décodé.
     * @throws JWTCreationException Si le token est invalide ou ne peut être décodé.
     */
    public DecodedJWT verifyToken(String token) throws JWTCreationException {
        Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
        JWTVerifier verifier = JWT.require(algorithm).build();

        try {
            return verifier.verify(token);
        } catch (JWTDecodeException e) {
            throw new JWTCreationException("Invalid token", e);
        }
    }

    /**
     * Extrait le nom d'utilisateur d'un token JWT.
     *
     * @param token Le token JWT dont extraire le nom d'utilisateur.
     * @return Le nom d'utilisateur extrait du token.
     * @throws JWTCreationException Si le token est invalide ou ne peut être décodé.
     */
    public String getUsernameFromToken(String token) throws JWTCreationException {
        return verifyToken(token).getSubject();
    }

    /**
     * Extrait les rôles d'un token JWT.
     *
     * @param token Le token JWT dont extraire les rôles.
     * @return Un tableau de chaînes de caractères représentant les rôles extraits du token.
     * @throws JWTCreationException Si le token est invalide ou ne peut être décodé.
     */
    public String[] getRolesFromToken(String token) throws JWTCreationException {
        return verifyToken(token).getClaim("roles").asArray(String.class);
    }
}
