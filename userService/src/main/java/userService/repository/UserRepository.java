package userService.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import userService.domain.User;

import java.util.List;


public interface UserRepository extends JpaRepository<User,Long> {

    List<User> findByClientId(String clientId);

    List<User> findByAccessToken(String accessToken);

    List<User> findByRefreshToken(String refreshToken);
}
