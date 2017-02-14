package ServerSv.web;

import ServerSv.web.model.request.AuthorRequest;
import ServerSv.web.model.response.AuthorResponse;
import ServerSv.web.model.response.ListOfAuthorsResponses;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import java.net.URI;


@RestController
@RequestMapping("/author")
public class AuthorRestController {
    private final String AuthorUrl = "http://localhost:8086/author/";
    private final String checkTokenUrl = "http://localhost:8082/user/checkToken";


    @RequestMapping(value="/{id}", method = RequestMethod.GET)
    public AuthorResponse getAuthor(@PathVariable Integer id) {
        RestTemplate restTemplate = new RestTemplate();
        URI uri = UriComponentsBuilder.fromUriString(AuthorUrl + id).build().toUri();
        HttpHeaders headers = new HttpHeaders();
        HttpEntity httpEntity = new HttpEntity<>(null, headers);
        ResponseEntity<AuthorResponse> response = restTemplate.exchange(uri, HttpMethod.GET, httpEntity, AuthorResponse.class);
        return response.getBody();
    }

    @RequestMapping(method = RequestMethod.GET)
    public ListOfAuthorsResponses getAll() {
        RestTemplate restTemplate = new RestTemplate();
        URI uri = UriComponentsBuilder.fromUriString(AuthorUrl).build().toUri();
        HttpHeaders headers = new HttpHeaders();
        HttpEntity httpEntity = new HttpEntity<>(null, headers);
        ResponseEntity<ListOfAuthorsResponses> response = restTemplate.exchange(uri, HttpMethod.GET, httpEntity, ListOfAuthorsResponses.class);
        return response.getBody();
    }

    @RequestMapping(value = "/{startIndex}/{finishIndex}", method = RequestMethod.GET)
    public ListOfAuthorsResponses getAllPagination(@PathVariable Integer startIndex, @PathVariable Integer finishIndex) {
        RestTemplate restTemplate = new RestTemplate();
        URI uri = UriComponentsBuilder.fromUriString(AuthorUrl + startIndex + "/" + finishIndex).build().toUri();
        HttpHeaders headers = new HttpHeaders();
        HttpEntity httpEntity = new HttpEntity<>(null, headers);
        ResponseEntity<ListOfAuthorsResponses> response = restTemplate.exchange(uri, HttpMethod.GET, httpEntity, ListOfAuthorsResponses.class);
        return response.getBody();
    }

    @RequestMapping(method = RequestMethod.POST)
    public Long createAuthor(HttpEntity<AuthorRequest> fullAuthorRequest, HttpServletResponse response) {
        AuthorRequest authorRequest = fullAuthorRequest.getBody();
        HttpHeaders header = fullAuthorRequest.getHeaders();
        String authHeader = header.get("Authorization").toString();
        String accesstoken = authHeader.substring(authHeader.indexOf('[')+1,authHeader.lastIndexOf(']'));

        RestTemplate restTemplate = new RestTemplate();
        URI uri = UriComponentsBuilder.fromUriString(checkTokenUrl).build().toUri();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Token",accesstoken);
        HttpEntity httpEntity = new HttpEntity<>(null, headers);

        ResponseEntity<Boolean> responseFlag = restTemplate.exchange(uri, HttpMethod.GET, httpEntity, Boolean.class);

        if (responseFlag.getBody()) {

            RestTemplate restTemplateAuthor = new RestTemplate();
            URI uriAuthor = UriComponentsBuilder.fromUriString(AuthorUrl).build().toUri();
            HttpEntity<AuthorRequest> httpEntityAuthor = new HttpEntity<>(authorRequest, null);

            ResponseEntity<Long> responseAuthor = restTemplateAuthor.exchange(uriAuthor, HttpMethod.POST, httpEntityAuthor, Long.class);
            response.setStatus(HttpStatus.CREATED.value());
            return responseAuthor.getBody();
        }
        else
        {
            response.setStatus(401);//HttpStatus.UNAUTHORIZED.value());
            return 0L;
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Long updateAuthor(@PathVariable Integer id, HttpEntity<AuthorRequest> fullAuthorRequest, HttpServletResponse response) {
        AuthorRequest authorRequest = fullAuthorRequest.getBody();
        HttpHeaders header = fullAuthorRequest.getHeaders();
        String authHeader = header.get("Authorization").toString();
        String accesstoken = authHeader.substring(authHeader.indexOf('[')+1,authHeader.lastIndexOf(']'));

        RestTemplate restTemplate = new RestTemplate();
        URI uri = UriComponentsBuilder.fromUriString(checkTokenUrl).build().toUri();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Token",accesstoken);
        HttpEntity httpEntity = new HttpEntity<>(null, headers);

        ResponseEntity<Boolean> responseFlag = restTemplate.exchange(uri, HttpMethod.GET, httpEntity, Boolean.class);

        if (responseFlag.getBody()) {

            RestTemplate restTemplateAuthor = new RestTemplate();
            URI uriAuthor = UriComponentsBuilder.fromUriString(AuthorUrl+id).build().toUri();
            HttpEntity httpEntityItem = new HttpEntity<>(authorRequest, null);

            ResponseEntity<Long> responseItem = restTemplateAuthor.exchange(uriAuthor, HttpMethod.PUT, httpEntityItem, Long.class);
            response.setStatus(HttpStatus.CREATED.value());
            return responseItem.getBody();
        }
        else
        {
            response.setStatus(401);//HttpStatus.UNAUTHORIZED.value());
            return 0L;
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteItem(@PathVariable Integer id, HttpEntity<AuthorRequest> fullAuthorRequest, HttpServletResponse response) {
        AuthorRequest authorRequest = fullAuthorRequest.getBody();
        HttpHeaders header = fullAuthorRequest.getHeaders();
        String authHeader = header.get("Authorization").toString();
        String accesstoken = authHeader.substring(authHeader.indexOf('[')+1,authHeader.lastIndexOf(']'));

        RestTemplate restTemplate = new RestTemplate();
        URI uri = UriComponentsBuilder.fromUriString(checkTokenUrl).build().toUri();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Token",accesstoken);
        HttpEntity httpEntity = new HttpEntity<>(null, headers);

        ResponseEntity<Boolean> responseFlag = restTemplate.exchange(uri, HttpMethod.GET, httpEntity, Boolean.class);

        if (responseFlag.getBody()) {

            RestTemplate restTemplateAuthor = new RestTemplate();
            URI uriAuthor = UriComponentsBuilder.fromUriString(AuthorUrl+id).build().toUri();
            HttpEntity httpEntityAuthor = new HttpEntity<>(null, null);

            restTemplateAuthor.exchange(uriAuthor, HttpMethod.DELETE, httpEntityAuthor, Long.class);
            response.setStatus(HttpStatus.NO_CONTENT.value());
            return;
        }
        else
        {
            response.setStatus(401);//HttpStatus.UNAUTHORIZED.value());
            return;
        }
    }
}
