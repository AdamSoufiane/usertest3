package domain.entities;

import lombok.Data;
import domain.vo.UserId;

@Data
public class UserEntity {

    private final UserId id;
    private String username;
    private String email;
    private String password;

    // Assuming validation logic is handled externally or by the UserId class
}