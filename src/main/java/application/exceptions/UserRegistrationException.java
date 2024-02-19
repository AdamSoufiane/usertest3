package application.exceptions;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserRegistrationException extends RuntimeException {

    public UserRegistrationException(String message) {
        super(message);
    }

    public UserRegistrationException(String message, Throwable cause) {
        super(message, cause);
    }

}