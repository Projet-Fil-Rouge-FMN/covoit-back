package covoit.config;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import covoit.ConvoitBackApplication;
import covoit.repository.UserAccountRepository;
import covoit.services.JwtService;
import covoit.services.UserAccountService;

@SpringBootTest(classes = ConvoitBackApplication.class)
@AutoConfigureMockMvc
class SecurityConfigTest {

    @InjectMocks
    private SecurityConfig securityConfig;

    @MockBean
    private UserAccountRepository userAccountRepository;

    @MockBean
    private UserAccountService userAccountService;

    @MockBean
    private JwtService jwtService;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                "admin", 
                "password", 
                List.of(new SimpleGrantedAuthority("ROLE_ADMIN"))
        );

        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(authentication);
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testDeleteUserById() throws Exception {
        int validUserId = 2;

        when(userAccountService.deleteUserById(validUserId)).thenReturn(true);

        mockMvc.perform(delete("/user/delete/" + validUserId))
               .andExpect(status().isOk())
               .andExpect(content().string("User deleted successfully"));

        verify(userAccountService, times(1)).deleteUserById(validUserId);
    }
    
    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    void testDeleteUserByIdFail() throws Exception {
        int otherUserId = 1; // This should be an ID that the authenticated user cannot delete

        // Execute the DELETE request with a different user ID
        mockMvc.perform(delete("/user/delete/" + otherUserId))
               .andExpect(status().isForbidden()); // Expecting a 403 Forbidden status
    }



    @Test
    void testCorsConfigurationSource() {
        assertNotNull(securityConfig.corsConfigurationSource(), "Cors configuration source should not be null");
    }

    @Test
    void testPasswordEncoder() {
        assertNotNull(securityConfig.passwordEncoder(), "PasswordEncoder should not be null");
    }

    @Test
    void testObject() {
        assertNotNull(securityConfig, "SecurityConfig object should not be null");
    }

    @Test
    void testFindAllUsers() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/user")
               .accept(MediaType.APPLICATION_JSON))
               .andExpect(MockMvcResultMatchers.status().isOk())
               .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }
}
