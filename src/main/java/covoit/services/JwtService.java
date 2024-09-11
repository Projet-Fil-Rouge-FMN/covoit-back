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

@Service
public class JwtService {

    private String SECRET_KEY;

    public JwtService(@Value("${jwt.secret}") String secretKey) {
        this.SECRET_KEY = secretKey;
    }

    public String generateToken(String username) {
        Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);

        return JWT.create()
                .withSubject(username)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 heures
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
}