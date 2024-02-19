package infrastructure.adapters;

import domain.entities.UserEntity;
import application.ports.out.UserRepositoryPort;
import domain.vo.UserId;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@Repository
@AllArgsConstructor
public class PostgresUserRepositoryAdapter implements UserRepositoryPort {

    private static final Logger logger = LoggerFactory.getLogger(PostgresUserRepositoryAdapter.class);
    private final EntityManager entityManager;

    @Override
    @Transactional
    public UserId saveUser(UserEntity userEntity) {
        try {
            entityManager.persist(userEntity);
            entityManager.flush(); // Ensure ID is assigned
            return userEntity.getId();
        } catch (DataAccessException e) {
            logger.error("Failed to persist user entity", e);
            throw new UserPersistenceException("Failed to persist user entity", e);
        }
    }

}

class UserPersistenceException extends RuntimeException {

    public UserPersistenceException(String message, Throwable cause) {
        super(message, cause);
    }

}