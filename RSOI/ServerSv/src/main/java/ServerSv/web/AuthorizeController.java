package ServerSv.web;

import ServerSv.domain.OAuth2Token;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URI;
import java.util.Map;


@RestController
@RequestMapping("/authorize")
public class AuthorizeController {
    private final String authorizationUrl = "http://localhost:8082/authorize";
    private final String accessTokenUrl = "http://localhost:8082/authorize/accessToken";
    private final String refreshTokenUrl = "http://localhost:8082/authorize/refreshToken";


    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView getAuthorizationCode(HttpServletRequest httpServletRequest) {
        Map<String, String[]> map = httpServletRequest.getParameterMap();
        String clientId = map.get("clientId")[0];
        String csrf = map.get("csrf")[0];
        String responseType = map.get("response_type")[0];
        String backUrl = map.get("callbackUrl")[0];

        String address = authorizationUrl + "?" +
                "clientId=" + clientId + "&" +
                "csrf=" + csrf + "&" +
                "response_type=" + responseType + "&" +
                "callbackUrl=" + backUrl;

        RestTemplate restTemplate = new RestTemplate();
        URI uri = UriComponentsBuilder.fromUriString(address).build().toUri();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", address);
        HttpEntity httpEntity = new HttpEntity<>(null, headers);
        restTemplate.postForLocation(uri,httpEntity);

        return new ModelAndView("grantAuthorization");
    }

    @RequestMapping(value = "/accessToken", method = RequestMethod.POST)
    public OAuth2Token postAccessToken(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        String header = httpServletRequest.getHeader("Authorization");
        RestTemplate restTemplate = new RestTemplate();
        URI uri = UriComponentsBuilder.fromUriString(accessTokenUrl).build().toUri();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization",header);

        HttpEntity httpEntity = new HttpEntity<>(null, headers);

        ResponseEntity<OAuth2Token> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, OAuth2Token.class);
        return response.getBody();
    }


    @RequestMapping(value = "/refreshToken", method = RequestMethod.POST)
    public OAuth2Token postRefreshToken(HttpServletRequest httpServletRequest) {
        String header = httpServletRequest.getHeader("Authorization");
        RestTemplate restTemplate = new RestTemplate();
        URI uri = UriComponentsBuilder.fromUriString(refreshTokenUrl).build().toUri();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization",header);

        HttpEntity httpEntity = new HttpEntity<>(null, headers);

        ResponseEntity<OAuth2Token> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, OAuth2Token.class);
        return response.getBody();
    }

}
