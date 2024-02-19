package application.services;

import application.exceptions.UserRegistrationException;
import application.ports.in.RegisterUserUseCase;
import application.ports.out.UserIdGeneratorPort;
import application.ports.out.UserRepositoryPort;
import domain.entities.UserEntity;
import domain.exceptions.UserNotFoundException;
import domain.vo.UserId;
import infrastructure.rest.UserRegistrationRequest;
import infrastructure.rest.UserRegistrationResponse;
import domain.enums.RegistrationStatus;
import org.apache.commons.validator.routines.EmailValidator;
import java.sql.SQLException;

public class UserRegistrationService implements RegisterUserUseCase {

    private final UserRepositoryPort userRepositoryPort;
    private final UserIdGeneratorPort userIdGenerator;

    public UserRegistrationService(UserRepositoryPort userRepositoryPort, UserIdGeneratorPort userIdGenerator) {
        this.userRepositoryPort = userRepositoryPort;
        this.userIdGenerator = userIdGenerator;
    }

    @Override
    public UserRegistrationResponse registerUser(UserRegistrationRequest request) throws UserRegistrationException {
        try {
            validateRequest(request);
            UserId userId = userIdGenerator.generate();
            UserEntity userEntity = new UserEntity(userId, request.getUsername(), request.getEmail(), request.getPassword());
            userRepositoryPort.saveUser(userEntity);
            return new UserRegistrationResponse(userId.getId(), userEntity.getUsername(), userEntity.getEmail(), RegistrationStatus.SUCCESS.toString());
        } catch (IllegalArgumentException e) {
            throw new UserRegistrationException("Invalid registration data: " + e.getMessage(), e);
        } catch (SQLException e) {
            throw new UserRegistrationException("Database error during user registration: " + e.getMessage(), e);
        } catch (UserNotFoundException e) {
            throw new UserRegistrationException("User not found: " + e.getMessage(), e);
        }
    }

    private void validateRequest(UserRegistrationRequest request) {
        if (request == null || request.getUsername() == null || request.getEmail() == null || request.getPassword() == null) {
            throw new IllegalArgumentException("Registration data cannot be null.");
        }
        if (!EmailValidator.getInstance().isValid(request.getEmail())) {
            throw new IllegalArgumentException("Invalid email format.");
        }
        if (request.getPassword().length() < 8 || !request.getPassword().matches(".*\d.*")) {
            throw new IllegalArgumentException("Password must be at least 8 characters long and contain at least one number.");
        }
        // Check for username uniqueness
        if (userRepositoryPort.existsByUsername(request.getUsername())) {
            throw new IllegalArgumentException("Username already exists.");
        }
        // Additional validation logic can be added here
    }
}

// Additional classes/enums/interfaces would be in their respective files as per the given file structure
