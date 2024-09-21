package covoit.config;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.StringUtils;

import com.auth0.jwt.exceptions.JWTVerificationException;

import covoit.services.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    public JwtAuthenticationFilter(JwtService jwtService, UserDetailsService userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        String token = getJwtFromRequest(request);
        if (StringUtils.hasText(token) && jwtService.validateToken(token)) {
            try {
                String username = jwtService.getUsernameFromToken(token);
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null,
                        getAuthoritiesFromToken(token));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (JWTVerificationException e) {
                // Handle the exception (optional: log it)
            }
        }
        chain.doFilter(request, response);
    }


    /**
     * Extrait le token JWT de l'en-tête de la requête HTTP.
     *
     * @param request La requête HTTP.
     * @return Le token JWT extrait, ou null si aucun token n'est présent.
     */
    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    /**
     * Extrait les rôles d'un token JWT et les convertit en une collection
     * d'autorités Spring Security.
     *
     * @param token Le token JWT dont extraire les rôles.
     * @return Une collection d'autorités représentant les rôles extraits du token.
     * @throws JWTVerificationException Si le token est invalide.
     */
    private Collection<SimpleGrantedAuthority> getAuthoritiesFromToken(String token) throws JWTVerificationException {
        String[] roles;
        try {
            roles = jwtService.getRolesFromToken(token);
        } catch (JWTVerificationException e) {
            // Handle the exception (optional: log it)
            throw e; // Rethrow if you want to propagate it
        }
        return Arrays.stream(roles)
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .collect(Collectors.toList());
    }
    @Autowired
    private AuthenticationTrustResolver authenticationTrustResolver;

    public boolean isAnonymous(Authentication authentication) {
        return authenticationTrustResolver.isAnonymous(authentication);
    }

}
