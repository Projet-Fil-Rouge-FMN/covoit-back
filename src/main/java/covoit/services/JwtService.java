package covoit.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtService {

    private final String secretKey = "votre_clé_secrète"; // Remplacez par une clé secrète appropriée
    private final long expirationMillis = 86400000; // 1 jour

    // Générer un token JWT
    public String generateToken(UserDetails userDetails) {
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationMillis))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    // Extraire les informations du token
    public Claims extractClaims(String token) {
        return ((UserAccountService) Jwts.parser()
                .setSigningKey(secretKey))
                .parseClaimsJws(token)
                .getBody();
    }

    // Vérifier si le token est expiré
    public boolean isTokenExpired(String token) {
        Date expiration = extractClaims(token).getExpiration();
        return expiration.before(new Date());
    }

    // Valider le token
    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractClaims(token).getSubject();
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
