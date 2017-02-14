package userService.domain;

import java.util.Random;


public class AuthorizationData {

    private String clientId;
    private String csrf;
    private String responseType;
    private String authorizationCode;
    private String backUrl;

    public AuthorizationData() {
        this.clientId = "";
        this.csrf = "";
        this.responseType = "";
        this.authorizationCode = "";
        this.backUrl = "";
    }

    public AuthorizationData(String clientId, String csrf, String responseType, String backUrl) {
        this.clientId = clientId;
        this.csrf = csrf;
        this.responseType = responseType;
        this.authorizationCode = "";
        this.backUrl = backUrl;
    }

    public AuthorizationData(String clientId, String csrf, String responseType, String authorizationCode, String backUrl) {
        this.clientId = clientId;
        this.csrf = csrf;
        this.responseType = responseType;
        this.authorizationCode = authorizationCode;
        this.backUrl = backUrl;
    }

    public String getClientId() {
        return clientId;
    }

    public AuthorizationData setClientId(String clientId) {
        this.clientId = clientId;
        return this;
    }

    public String getCsrf() {
        return csrf;
    }

    public AuthorizationData setCsrf(String csrf) {
        this.csrf = csrf;
        return this;
    }

    public String getResponseType() {
        return responseType;
    }

    public AuthorizationData setResponseType(String responseType) {
        this.responseType = responseType;
        return this;
    }

    public String getAuthorizationCode() {
        return authorizationCode;
    }

    public AuthorizationData setAuthorizationCode(String authorizationCode) {
        this.authorizationCode = authorizationCode;
        return this;
    }

    public String generateAuthorizationCode() {
        this.authorizationCode = generateHeximalString(16);
        return authorizationCode;
    }

    public String generateHeximalString(int hexLength) {
        Random randomService = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < hexLength; i++) {
            sb.append(Integer.toHexString(randomService.nextInt()));
        }
        sb.setLength(hexLength);
        return sb.toString();
    }

    public String getBackUrl() {
        return backUrl;
    }

    public AuthorizationData setBackUrl(String backUrl) {
        this.backUrl = backUrl;
        return this;
    }
}
