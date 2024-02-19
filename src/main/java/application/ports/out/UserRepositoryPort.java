package application.ports.out;

import domain.entities.UserEntity;
import domain.vo.UserId;

public interface UserRepositoryPort {
    UserId saveUser(UserEntity userEntity);
}