package application.ports.in;

import infrastructure.rest.UserRegistrationRequest;
import infrastructure.rest.UserRegistrationResponse;

public interface RegisterUserUseCase {

    UserRegistrationResponse registerUser(UserRegistrationRequest request);

}