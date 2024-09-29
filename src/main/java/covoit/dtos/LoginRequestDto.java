package covoit.dtos;

public class LoginRequestDto {
    private String username;
    private String password;

    // Constructeur par défaut
    public LoginRequestDto() {
    }

    // Constructeur avec paramètres
    public LoginRequestDto(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // Getters et Setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
