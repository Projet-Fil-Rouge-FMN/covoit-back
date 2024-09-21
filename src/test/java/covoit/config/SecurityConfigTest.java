package covoit.config;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.cors.CorsConfigurationSource;
import static org.mockito.Mockito.*;
import org.mockito.Mockito;

import covoit.RESTcontroller.UserAccountController;
import covoit.dtos.UserAccountDto;
import covoit.entities.CustomUserDetails;
import covoit.repository.UserAccountRepository;
import covoit.services.JwtService;
import covoit.services.UserAccountService;

@SpringBootTest
@AutoConfigureMockMvc
class SecurityConfigTest {

    @InjectMocks
    private SecurityConfig securityConfig;

    @Mock
    private CorsConfigurationSource corsConfigurationSource;

    @Mock
    private UserAccountRepository userAccountRepository;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @MockBean
    private JwtService jwtService;
    
    @MockBean
    private UserAccountService userAccountService;
    
    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        // Crée une authentication avec un utilisateur et un rôle
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                "admin", 
                "password", 
                List.of(new SimpleGrantedAuthority("ROLE_ADMIN"))
        );

        // Crée un contexte de sécurité et l'assigne
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(authentication);
        SecurityContextHolder.setContext(securityContext);
    }


    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    void testFindUserById() throws Exception {
        // Mocking service response
        UserAccountDto user = new UserAccountDto();
        user.setId(1);
        user.setUserName("user");
        when(userAccountService.findById(1)).thenReturn(user);

        mockMvc.perform(MockMvcRequestBuilders.get("/user/1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.userName").value("user"));
    }
    
    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testDeleteUserById() throws Exception {
   
        mockMvc.perform(delete("/user/1"))
         .andExpect(status().isOk()) // attend un statut 200
         .andExpect(content().string("User deleted successfully"));
    }
    
    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    void testDeleteUserByIdFail() throws Exception {
        int validUserId = 2; // ID attendu par le service
        int incorrectUserId = 1; // ID incorrect pour la suppression

        // Mock du comportement du service pour l'ID attendu
        when(userAccountService.deleteUserById(validUserId)).thenReturn(true);
        // Mock du comportement du service pour l'ID incorrect
        when(userAccountService.deleteUserById(incorrectUserId)).thenReturn(false); // Simuler une échec

        // Exécution de la requête de suppression avec l'ID incorrect
        mockMvc.perform(MockMvcRequestBuilders.delete("/user/" + incorrectUserId))
               .andExpect(MockMvcResultMatchers.status().isNotFound()); // Attendre un statut 404 ou autre erreur
    }



      
    @Test
    void testCorsConfigurationSource() {
        assertNotNull(securityConfig.corsConfigurationSource(), "Cors configuration source should not be null");
    }

    @Test
    void testUserDetailsService() {
        assertNotNull(securityConfig.userDetailsService(userAccountRepository), "UserDetailsService should not be null");
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
