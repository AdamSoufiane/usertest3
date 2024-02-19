package infrastructure.rest;

import java.io.Serializable;
import lombok.Data;

@Data
public class UserRegistrationRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    private String username;
    private String email;
    private String password;

    public UserRegistrationRequest(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

}