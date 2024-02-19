package infrastructure.rest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;
import application.ports.in.RegisterUserUseCase;
import domain.exceptions.UserNotFoundException;
import application.exceptions.UserRegistrationException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserRegistrationController {

    private final RegisterUserUseCase registerUserUseCase;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserRegistrationRequest request) {
        try {
            log.info("Starting user registration process for: {}", request.getUsername());
            UserRegistrationResponse response = registerUserUseCase.registerUser(request);
            log.info("User registration process completed successfully for: {}", request.getUsername());
            return ResponseEntity.ok(response);
        } catch (UserNotFoundException ex) {
            log.error("User registration failed for: {}", request.getUsername(), ex);
            return ResponseEntity.notFound().build();
        } catch (UserRegistrationException ex) {
            log.error("User registration failed for: {}", request.getUsername(), ex);
            Map<String, String> errorDetails = new HashMap<>();
            errorDetails.put("username", request.getUsername());
            errorDetails.put("email", request.getEmail());
            errorDetails.put("error", ex.getMessage());
            return ResponseEntity.badRequest().body(errorDetails);
        } catch (Exception ex) {
            log.error("An unexpected error occurred during user registration for: {}", request.getUsername(), ex);
            return ResponseEntity.internalServerError().build();
        }
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        log.error("Validation failed: {}", ex.getMessage());
        return ResponseEntity.badRequest().body("Validation error: " + ex.getBindingResult().getFieldError().getDefaultMessage());
    }
}