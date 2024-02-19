package infrastructure.rest;

import lombok.Data;

@Data
public class UserRegistrationResponse {

    private String userId;
    private String username;
    private String email;
    private String registrationStatus;

    public UserRegistrationResponse(String userId, String username, String email, String registrationStatus) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.registrationStatus = registrationStatus;
    }
}
