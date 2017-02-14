package userService.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import userService.domain.AuthorizationData;
import userService.domain.OAuth2Token;
import userService.domain.User;
import userService.repository.UserRepository;

import java.util.List;

@Service
public class AuthorizationServiceImpl implements AuthorizationService {
    private AuthorizationData authorizationData = new AuthorizationData();

    @Autowired
    private UserRepository userRepository;


    @Override
    public void saveRequestData(String clientId, String csrf, String responseType, String backUrl) {
        authorizationData.setClientId(clientId);
        authorizationData.setCsrf(csrf);
        authorizationData.setResponseType(responseType);
        authorizationData.setBackUrl(backUrl);
    }

    @Override
    public AuthorizationData getAuthorizationData() {
        return authorizationData;
    }

    @Override
    public String getAuthorizationCodeUrl() {
        authorizationData.generateAuthorizationCode();
        return authorizationData.getBackUrl() + "?grant_type=authorization_code&" + authorizationData.getResponseType() +
                "=" + authorizationData.getAuthorizationCode() + "&csrf=" + authorizationData.getCsrf();
    }

    @Override
    public OAuth2Token getAccessToken(String authorizationCode, String responseType, String csrf) {
        String clientId = authorizationData.getClientId();
        List<User> users = userRepository.findByClientId(clientId);
        User user = users.get(0);
        OAuth2Token oAuth2Token = new OAuth2Token();
        oAuth2Token.setToken(user.getAccessToken());
        oAuth2Token.setTokenType(responseType);
        oAuth2Token.setRefreshToken(user.getRefreshToken());
        oAuth2Token.setExpiresIn(user.getExpiresIn());
        oAuth2Token.setCsrf(csrf);
        return oAuth2Token;
    }

    @Override
    public OAuth2Token getRefreshToken(String refreshToken, String responseType, String csrf) {
        //boolean Flag = new String(authorizationData.getAuthorizationCode()).equals(authorizationCode);
        List<User> users = userRepository.findByRefreshToken(refreshToken);
        User user = users.get(0);
        user.updateToken();
        userRepository.save(user);
        OAuth2Token oAuth2Token = new OAuth2Token();
        oAuth2Token.setToken(user.getAccessToken());
        oAuth2Token.setTokenType(responseType);
        oAuth2Token.setRefreshToken(user.getRefreshToken());
        oAuth2Token.setExpiresIn(user.getExpiresIn());
        oAuth2Token.setCsrf(csrf);
        return oAuth2Token;
    }


}
