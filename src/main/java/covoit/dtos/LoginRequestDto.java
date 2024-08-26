package covoit.dtos;

import org.springframework.stereotype.Repository;

@Repository
public class LoginRequestDto {
    private String userName;
    private String password;

  


    // Getters and Setters
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}