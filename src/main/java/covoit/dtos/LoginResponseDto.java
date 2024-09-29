package covoit.dtos;

import covoit.entities.UserAccount;

public class LoginResponseDto {
    private String token;
    private UserAccount user;

    public LoginResponseDto(String token, UserAccount user) {
        this.token = token;
        this.user = user;
    }

    // Getters
    public String getToken() {
        return token;
    }

    public UserAccount getUser() {
        return user;
    }
}
