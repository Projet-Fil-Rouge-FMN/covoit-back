package covoit.config;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashMap;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import covoit.RESTcontroller.UserAccountController;
import covoit.dtos.UserAccountDto;
import covoit.entities.UserAccount;
import covoit.repository.UserAccountRepository;
import covoit.services.JwtService;
import covoit.services.UserAccountService;

@SpringBootTest
@AutoConfigureMockMvc
class JwtAuthenticationFilterTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private UserAccountRepository userAccountRepository; // Mock du repository
    @MockBean
    private UserAccountService userAccountService;
    @Test
    public void testJwtFilter_ValidToken() throws Exception {
        String token = "valid_token";
        DecodedJWT decodedJWT = Mockito.mock(DecodedJWT.class);
        
        // Configurez les méthodes du mock DecodedJWT
        Mockito.when(decodedJWT.getClaims()).thenReturn(new HashMap<>()); // Exemple, ajustez selon vos besoins

        Mockito.when(jwtService.verifyToken(token)).thenReturn(decodedJWT);
        Mockito.when(jwtService.getUsernameFromToken(token)).thenReturn("user");
        Mockito.when(jwtService.getRolesFromToken(token)).thenReturn(new String[]{"USER"});

        mockMvc.perform(get("/user/1").header("Authorization", "Bearer " + token))
               .andExpect(status().isOk());
    }
}