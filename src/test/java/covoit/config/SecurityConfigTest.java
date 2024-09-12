package covoit.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.cors.CorsConfigurationSource;

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
    
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Initialisation des mocks
    }

   
    @Autowired
    private MockMvc mockMvc;

    @Test
    void testFindUserById() throws Exception {
        // Mocking or setting up your service responses
        // Mockito.when(userAccountService.findById(1)).thenReturn(new UserAccountDto(...));

        mockMvc.perform(MockMvcRequestBuilders.get("/user/1")
                .header("Authorization", "Bearer valid_token_here")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(matcher(HttpStatus.OK))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.userName").value("user"));
    }

    /**
     * Match the expected response status to that of the HttpServletResponse.
     */
    private ResultMatcher matcher(HttpStatus status) {
        return result -> {
            int actualStatus = result.getResponse().getStatus();
            assertEquals(status.value(), actualStatus, "Status");
        };
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
	        mockMvc.perform(MockMvcRequestBuilders.get("/user/")
	               .accept(MediaType.APPLICATION_JSON))
	               .andExpect(MockMvcResultMatchers.status().isOk())
	               .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
	    }


	}