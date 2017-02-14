package userService.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import userService.domain.User;
import userService.service.UserService;
import userService.web.model.Request.UserRequest;
import userService.web.model.Response.UserResponse;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/user")
public class UserRestController {

    @Autowired
    private UserService userService;

    @RequestMapping(value="/{id}", method = RequestMethod.GET)
    public UserResponse getUser(@PathVariable Long id) {
        return new UserResponse(userService.getById(id));
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<UserResponse> getAll() {
        return userService.getAll()
                .stream()
                .map(UserResponse::new)
                .collect(Collectors.toList());
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value = "/addUser", method = RequestMethod.POST)
    public void createUser(UserRequest userRequest, HttpServletResponse response) {
        User user = userService.save(userRequest);
        response.addHeader(HttpHeaders.LOCATION,"/user/"+user.getId());
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PATCH)
    public UserResponse updateUser(@PathVariable Long id, @RequestBody UserRequest userRequest) {
        return new UserResponse(userService.update(id,userRequest));
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteUser(@PathVariable Long id) {
        userService.delete(id);
    }

    @RequestMapping(value = "/checkToken", method = RequestMethod.GET)
    public Boolean checkToken(HttpEntity<?> httpEntity) {
        HttpHeaders header = httpEntity.getHeaders();
        String tokenHeader = header.get("Token").toString();
        String accesstoken = tokenHeader.substring(tokenHeader.indexOf('[')+1,tokenHeader.lastIndexOf(']'));
        if (userService.checkUpToTimeToken(accesstoken)) {
            return true;
        }
        return false;
    }
}
