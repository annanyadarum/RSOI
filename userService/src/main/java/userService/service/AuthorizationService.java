package userService.service;


import userService.domain.AuthorizationData;
import userService.domain.OAuth2Token;


public interface AuthorizationService {

    void saveRequestData(String clientId, String csrf, String responseType, String backUrl);

    AuthorizationData getAuthorizationData();

    String getAuthorizationCodeUrl();

    OAuth2Token getAccessToken(String authorizationCode, String responseType, String csrf);

    OAuth2Token getRefreshToken(String authorizationCode, String responseType, String csrf);
}
