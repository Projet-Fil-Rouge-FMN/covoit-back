package covoit.config;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import covoit.services.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtAuthenticationFilterTest {

    @InjectMocks
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Mock
    private JwtService jwtService;

    @Mock
    private UserDetailsService userDetailsService;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(jwtAuthenticationFilter).build();
    }

    @Test
    public void testDoFilterInternal_ValidToken() throws Exception {
        String token = "valid.jwt.token";
        String username = "testuser";
        String[] roles = {"USER", "ADMIN"};

        when(request.getHeader("Authorization")).thenReturn("Bearer " + token);
        when(jwtService.validateToken(token)).thenReturn(true);
        when(jwtService.getUsernameFromToken(token)).thenReturn(username);
        when(jwtService.getRolesFromToken(token)).thenReturn(roles); // Mock des rôles
        when(userDetailsService.loadUserByUsername(username)).thenReturn(mock(UserDetails.class));

        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        // Assert the expected behavior
        // Par exemple, vérifier que l'authentification a été définie dans le contexte de sécurité
    }


    @Test
    public void testDoFilterInternal_InvalidToken() throws Exception {
        String token = "invalid.jwt.token";
        
        when(request.getHeader("Authorization")).thenReturn("Bearer " + token);
        when(jwtService.validateToken(token)).thenReturn(false);
        
        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);
        
        // Verify that no authentication is set in the security context
        // Add additional verifications and assertions as needed
    }
    
}
