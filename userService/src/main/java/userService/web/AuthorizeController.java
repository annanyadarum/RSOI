package userService.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import userService.domain.OAuth2Token;
import userService.service.AuthorizationService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;


@RestController
@RequestMapping("/authorize")
public class AuthorizeController {

    @Autowired
    private AuthorizationService authorizationService;

    @RequestMapping(method = RequestMethod.POST)
    public void getAuthorizationCode(HttpServletRequest httpServletRequest) {
        Map<String, String[]> map = httpServletRequest.getParameterMap();
        String clientId = map.get("clientId")[0];
        String csrf = map.get("csrf")[0];
        String responseType = map.get("response_type")[0];
        String backUrl = map.get("callbackUrl")[0];
        authorizationService.saveRequestData(clientId, csrf, responseType, backUrl);
    }

    @RequestMapping(value = "/grant", method = RequestMethod.POST)
    public void sendAuthorizationCode(HttpServletResponse httpServletResponse) {
        String address = authorizationService.getAuthorizationCodeUrl();
        httpServletResponse.setHeader("Location", address);
        httpServletResponse.setStatus(302);
    }

    @RequestMapping(value = "/accessToken", method = RequestMethod.POST)
    public OAuth2Token postAccessToken(HttpServletRequest httpServletRequest) {

        String header = httpServletRequest.getHeader("Authorization");
        int authorizationCodeIndex = header.indexOf("authorizationCode=");
        int responseTypeIndex = header.indexOf("response_type=");
        int csrfIndex = header.indexOf("csrf=");
        int lastIndex = header.indexOf("&",authorizationCodeIndex) < 0 ? header.length() : header.indexOf("&",authorizationCodeIndex);
        String authorizationCode = header.substring(header.indexOf("=",authorizationCodeIndex)+1,lastIndex);

        lastIndex = header.indexOf("&",responseTypeIndex) < 0 ? header.length() : header.indexOf("&",responseTypeIndex);
        String responseType = header.substring(header.indexOf("=",responseTypeIndex)+1,lastIndex);

        lastIndex = header.indexOf("&",csrfIndex) < 0 ? header.length() : header.indexOf("&",csrfIndex);
        String csrf = header.substring(header.indexOf("=",csrfIndex)+1,lastIndex);

        return authorizationService.getAccessToken(authorizationCode,responseType,csrf);
    }


    @RequestMapping(value = "/refreshToken", method = RequestMethod.POST)
    public OAuth2Token postRefreshToken(HttpServletRequest httpServletRequest) {

        String header = httpServletRequest.getHeader("Authorization");
        int authorizationCodeIndex = header.indexOf("refreshToken=");
        int responseTypeIndex = header.indexOf("response_type=");
        int csrfIndex = header.indexOf("csrf=");
        int lastIndex = header.indexOf("&",authorizationCodeIndex) < 0 ? header.length() : header.indexOf("&",authorizationCodeIndex);
        String authorizationCode = header.substring(header.indexOf("=",authorizationCodeIndex)+1,lastIndex);

        lastIndex = header.indexOf("&",responseTypeIndex) < 0 ? header.length() : header.indexOf("&",responseTypeIndex);
        String responseType = header.substring(header.indexOf("=",responseTypeIndex)+1,lastIndex);

        lastIndex = header.indexOf("&",csrfIndex) < 0 ? header.length() : header.indexOf("&",csrfIndex);
        String csrf = header.substring(header.indexOf("=",csrfIndex)+1,lastIndex);

        return authorizationService.getRefreshToken(authorizationCode,responseType,csrf);
    }

}
