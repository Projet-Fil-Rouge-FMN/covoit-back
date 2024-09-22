package covoit.config;

import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import covoit.ConvoitBackApplication;
import covoit.dtos.UserAccountDto;
import covoit.repository.UserAccountRepository;
import covoit.services.JwtService;
import covoit.services.UserAccountService;

@SpringBootTest(classes = ConvoitBackApplication.class)
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
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testFindUserById() throws Exception {
        int userId = 1;

        // Créez un UserAccountDto avec des données appropriées
        UserAccountDto userDto = new UserAccountDto();
        userDto.setId(userId); // Assurez-vous que tous les champs nécessaires sont définis
        userDto.setUserName("testuser");

        // Mock du comportement du service
        when(userAccountService.findById(userId)).thenReturn(userDto);

        // Exécution de la requête GET
        mockMvc.perform(MockMvcRequestBuilders.get("/user/" + userId)
               .accept(MediaType.APPLICATION_JSON))
               .andExpect(MockMvcResultMatchers.status().isOk())
               .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
               .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(userId)) // Vérifie les valeurs spécifiques
               .andExpect(MockMvcResultMatchers.jsonPath("$.userName").value("testuser"));
    }
}