package covoit.config;

import covoit.services.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Filtre de sécurité pour l'authentification basée sur JWT (JSON Web Token).
 * Vérifie la présence d'un token JWT dans l'en-tête de la requête, valide le token,
 * extrait les informations d'authentification et définit le contexte de sécurité.
 */
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

    /**
     * Filtre les requêtes HTTP pour vérifier et extraire l'authentification à partir d'un token JWT.
     *
     * @param request La requête HTTP.
     * @param response La réponse HTTP.
     * @param chain La chaîne de filtres.
     * @throws IOException En cas d'erreur d'entrée/sortie.
     * @throws ServletException En cas d'erreur de servlet.
     */
    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest request,@NotNull HttpServletResponse response,@NotNull FilterChain chain)
            throws ServletException, IOException {
        try {
            String token = getJwtFromRequest(request);

            if (token != null && jwtService.verifyToken(token) != null) {
                String username = jwtService.getUsernameFromToken(token);
                Collection<SimpleGrantedAuthority> authorities = getAuthoritiesFromToken(token);

                Authentication authentication = new UsernamePasswordAuthenticationToken(username, null, authorities);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception ex) {
            SecurityContextHolder.clearContext();
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
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
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    /**
     * Extrait les rôles d'un token JWT et les convertit en une collection d'autorités Spring Security.
     *
     * @param token Le token JWT dont extraire les rôles.
     * @return Une collection d'autorités représentant les rôles extraits du token.
     */
    private Collection<SimpleGrantedAuthority> getAuthoritiesFromToken(String token) {
        String[] roles = jwtService.getRolesFromToken(token);
        return Arrays.stream(roles)
                     .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                     .collect(Collectors.toList());
    }
}
