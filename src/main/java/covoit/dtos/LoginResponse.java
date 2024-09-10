package covoit.dtos;

public class LoginResponse {
    private String message;

    // Constructeur
    public LoginResponse(String message) {
        this.message = message;
    }

    // Getter
    public String getMessage() {
        return message;
    }

    // Setter
    public void setMessage(String message) {
        this.message = message;
    }
}
