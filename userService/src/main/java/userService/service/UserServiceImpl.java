package userService.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import userService.domain.User;
import userService.repository.UserRepository;
import userService.web.model.Request.UserRequest;

import javax.persistence.EntityNotFoundException;
import java.util.List;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;



@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public User getById(Long id) {
        User user = userRepository.findOne(id);
        if (user == null) {
            throw new EntityNotFoundException("User '{" + id + "}' not found");
        }
        return user;
    }

    @Override
    @Transactional(readOnly = true)
    public User findByClientId(String clientId) {
        List<User> users = userRepository.findByClientId(clientId);
        User user = users.get(0);
        if (user == null) {
            throw new EntityNotFoundException("User 'clientId={" + clientId + "}' not found");
        }
        return user;
    }

    @Override
    public boolean checkUpToTimeToken(String token) {
        List<User> users = userRepository.findByAccessToken(token);
        User user = users.get(0);
        if (user == null) {
            throw new EntityNotFoundException("User with token 'token={" + token + "}' not found");
        }

        if (user.getExpiresIn() > System.currentTimeMillis())
            return true;

        return false;
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    @Transactional
    public User save(UserRequest userRequest) {
        User user = new User(userRequest.getLastName(),userRequest.getFirstName(),userRequest.getMiddleName(),
        userRequest.getClientId(),userRequest.getPasswordHash());
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public User update(Long id, UserRequest userRequest) {
        User user = userRepository.findOne(id);
        if (user == null) {
            throw new EntityNotFoundException("User '{" + id + "}' not found");
        }

        user.setLastName(userRequest.getLastName());
        user.setFirstName(userRequest.getFirstName());
        user.setMiddleName(userRequest.getMiddleName());
        user.setClientId(userRequest.getClientId() != null ? userRequest.getClientId() : user.getClientId());
        user.setPasswordHash(userRequest.getPasswordHash() != null ? userRequest.getPasswordHash() : user.getPasswordHash());

        return userRepository.save(user);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        userRepository.delete(id);
    }

}
