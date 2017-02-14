package userService.service;

import userService.domain.User;
import userService.web.model.Request.UserRequest;

import java.util.List;


public interface UserService {

    User getById(Long id);

    User findByClientId(String clientId);

    boolean checkUpToTimeToken(String token);

    List<User> getAll();

    User save(UserRequest userRequest);

    User update(Long id, UserRequest userRequest);

    void delete(Long id);
}
