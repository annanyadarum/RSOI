package ServerSv.web;

import ServerSv.web.model.request.UserRequest;
import ServerSv.web.model.response.ListOfUserResponses;
import ServerSv.web.model.response.UserResponse;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UriComponentsBuilder;


import javax.servlet.http.HttpServletResponse;
import java.net.URI;


@RestController
@RequestMapping("/user")
public class UserRestController {
    private final String addUserUrl = "http://localhost:8086/user";
    private final String updateUserPageUrl = "http://localhost:8086/user/";
    private final String deleteUserPageUrl = "http://localhost:8086/user/";
    private final String addOrderToUserUrl = "http://localhost:8086/user/";
    private final String deleteOrderFromUserUrl = "http://localhost:8086/user/";
    private final String getUserUrl= "http://localhost:8086/user/";
    private final String getOrderUrl = "http://localhost:8084/order/";
    private final String itemUrl = "http://localhost:8083/item/";

    private final String checkTokenUrl = "http://localhost:8086/user/checkToken";

    @RequestMapping(value = "/addUser", method = RequestMethod.GET)
    public ModelAndView addUserPage() {
        return new ModelAndView("addUserPage");
    }


    @RequestMapping(value="/{id}", method = RequestMethod.GET)
    public UserResponse getUser(@PathVariable Long id) {
        RestTemplate restTemplate = new RestTemplate();
        String address = getUserUrl + Long.toString(id);
        URI uri = UriComponentsBuilder.fromUriString(address).build().toUri();
        UserResponse userResponse = new UserResponse(restTemplate.getForObject(uri, UserResponse.class));
        return userResponse;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ListOfUserResponses getAll() {
        RestTemplate restTemplate = new RestTemplate();
        String address = getUserUrl;
        URI uri = UriComponentsBuilder.fromUriString(address).build().toUri();
        ResponseEntity<ListOfUserResponses> response = restTemplate.getForEntity(uri, ListOfUserResponses.class);
        ListOfUserResponses listOfUserResponces= response.getBody();
        return listOfUserResponces;
    }

    @RequestMapping(value = "/{startIndex}/{finishIndex}", method = RequestMethod.GET)
    public ListOfUserResponses getAllPagination(@PathVariable Integer startIndex, @PathVariable Integer finishIndex) {
        RestTemplate restTemplate = new RestTemplate();
        URI uri = UriComponentsBuilder.fromUriString(getUserUrl + startIndex + "/" + finishIndex).build().toUri();
        ResponseEntity<ListOfUserResponses> response = restTemplate.getForEntity(uri, ListOfUserResponses.class);
        ListOfUserResponses listOfUserResponces= response.getBody();
        return listOfUserResponces;
    }

    @RequestMapping(method = RequestMethod.POST)
    public Long createUser(HttpEntity<UserRequest> fullUserRequest, HttpServletResponse response) {
        UserRequest userRequest = fullUserRequest.getBody();
        HttpHeaders header = fullUserRequest.getHeaders();
        String authHeader = header.get("Authorization").toString();
        String accesstoken = authHeader.substring(authHeader.indexOf('[')+1,authHeader.lastIndexOf(']'));

        RestTemplate restTemplate = new RestTemplate();
        URI uri = UriComponentsBuilder.fromUriString(checkTokenUrl).build().toUri();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Token",accesstoken);
        HttpEntity httpEntity = new HttpEntity<>(null, headers);

        ResponseEntity<Boolean> responseFlag = restTemplate.exchange(uri, HttpMethod.GET, httpEntity, Boolean.class);

        if (responseFlag.getBody()) {

            RestTemplate restTemplateUser = new RestTemplate();
            URI uriUser = UriComponentsBuilder.fromUriString(addUserUrl).build().toUri();
            HttpEntity<UserRequest> httpEntityItem = new HttpEntity<>(userRequest, null);

            ResponseEntity<Long> responseUser = restTemplateUser.exchange(uriUser, HttpMethod.POST, httpEntityItem, Long.class);
            response.setStatus(HttpStatus.CREATED.value());
            return responseUser.getBody();
        }
        else
        {
            response.setStatus(401);//HttpStatus.UNAUTHORIZED.value());
            return 0L;
        }

    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PATCH)
    public Long updateUser(@PathVariable Long id, HttpEntity<UserRequest> fullUserRequest, HttpServletResponse response) {
        UserRequest orderRequest = fullUserRequest.getBody();
        HttpHeaders header = fullUserRequest.getHeaders();
        String authHeader = header.get("Authorization").toString();
        String accesstoken = authHeader.substring(authHeader.indexOf('[')+1,authHeader.lastIndexOf(']'));

        RestTemplate restTemplate = new RestTemplate();
        URI uri = UriComponentsBuilder.fromUriString(checkTokenUrl).build().toUri();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Token",accesstoken);
        HttpEntity httpEntity = new HttpEntity<>(null, headers);

        ResponseEntity<Boolean> responseFlag = restTemplate.exchange(uri, HttpMethod.GET, httpEntity, Boolean.class);

        if (responseFlag.getBody()) {

            RestTemplate restTemplateUser = new RestTemplate();
            URI uriUser = UriComponentsBuilder.fromUriString(updateUserPageUrl).build().toUri();
            HttpEntity<UserRequest> httpEntityItem = new HttpEntity<>(orderRequest, null);

            ResponseEntity<Long> responseUser = restTemplateUser.exchange(uriUser, HttpMethod.PUT, httpEntityItem, Long.class);
            response.setStatus(HttpStatus.CREATED.value());
            return responseUser.getBody();
        }
        else
        {
            response.setStatus(401);//HttpStatus.UNAUTHORIZED.value());
            return 0L;
        }
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteUser(@PathVariable Long id, HttpEntity<UserRequest> fullUserRequest, HttpServletResponse response) {
        UserRequest userRequest = fullUserRequest.getBody();
        HttpHeaders header = fullUserRequest.getHeaders();
        String authHeader = header.get("Authorization").toString();
        String accesstoken = authHeader.substring(authHeader.indexOf('[')+1,authHeader.lastIndexOf(']'));

        RestTemplate restTemplate = new RestTemplate();
        URI uri = UriComponentsBuilder.fromUriString(checkTokenUrl).build().toUri();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Token",accesstoken);
        HttpEntity httpEntity = new HttpEntity<>(null, headers);

        ResponseEntity<Boolean> responseFlag = restTemplate.exchange(uri, HttpMethod.GET, httpEntity, Boolean.class);

        if (responseFlag.getBody()) {

            RestTemplate restTemplateOrder = new RestTemplate();
            URI uriUser = UriComponentsBuilder.fromUriString(deleteUserPageUrl+id).build().toUri();
            HttpEntity httpEntityItem = new HttpEntity<>(null, null);

            restTemplateOrder.exchange(uriUser, HttpMethod.DELETE, httpEntityItem, Long.class);
            response.setStatus(HttpStatus.NO_CONTENT.value());
            return;
        }
        else
        {
            response.setStatus(401);//HttpStatus.UNAUTHORIZED.value());
            return;
        }
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value = "/{userId}/Order/{orderId}", method = RequestMethod.POST)
    public Long addOrderToUser(@PathVariable Long userId, @PathVariable Long orderId, HttpEntity<UserRequest> fullUserRequest, HttpServletResponse response) {
        HttpHeaders header = fullUserRequest.getHeaders();
        String authHeader = header.get("Authorization").toString();
        String accesstoken = authHeader.substring(authHeader.indexOf('[')+1,authHeader.lastIndexOf(']'));

        RestTemplate restTemplate = new RestTemplate();
        URI uri = UriComponentsBuilder.fromUriString(checkTokenUrl).build().toUri();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Token",accesstoken);
        HttpEntity httpEntity = new HttpEntity<>(null, headers);

        ResponseEntity<Boolean> responseFlag = restTemplate.exchange(uri, HttpMethod.GET, httpEntity, Boolean.class);

        if (responseFlag.getBody()) {
            RestTemplate restTemplateOrder = new RestTemplate();
            URI uriUser = UriComponentsBuilder.fromUriString(addOrderToUserUrl+userId+"/Order/"+orderId).build().toUri();
            HttpEntity httpEntityItem = new HttpEntity<>(null, null);

            restTemplateOrder.exchange(uriUser, HttpMethod.POST, httpEntityItem, Long.class);
            response.setStatus(HttpStatus.CREATED.value());
            return orderId;
        }
        else
        {
            response.setStatus(401);//HttpStatus.UNAUTHORIZED.value());
            return orderId;
        }
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value = "/{userId}/Order/{order}", method = RequestMethod.DELETE)
    public Long deleteOrderFromUser(@PathVariable Long userId, @PathVariable Long orderId, HttpEntity<UserRequest> fullUserRequest, HttpServletResponse response) {
        HttpHeaders header = fullUserRequest.getHeaders();
        String authHeader = header.get("Authorization").toString();
        String accesstoken = authHeader.substring(authHeader.indexOf('[')+1,authHeader.lastIndexOf(']'));

        RestTemplate restTemplate = new RestTemplate();
        URI uri = UriComponentsBuilder.fromUriString(checkTokenUrl).build().toUri();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Token",accesstoken);
        HttpEntity httpEntity = new HttpEntity<>(null, headers);

        ResponseEntity<Boolean> responseFlag = restTemplate.exchange(uri, HttpMethod.GET, httpEntity, Boolean.class);

        if (responseFlag.getBody()) {

            RestTemplate restTemplateOrder = new RestTemplate();
            URI uriUser = UriComponentsBuilder.fromUriString(deleteOrderFromUserUrl+userId+"/Order/"+orderId).build().toUri();
            HttpEntity httpEntityItem = new HttpEntity<>(null, null);

            restTemplateOrder.exchange(uriUser, HttpMethod.DELETE, httpEntityItem, Long.class);
            response.setStatus(HttpStatus.CREATED.value());
            return orderId;
        }
        else
        {
            response.setStatus(401);//HttpStatus.UNAUTHORIZED.value());
            return orderId;
        }
    }
}
