package ServerSv.domain;


public class OAuth2Token {

    private String token;
    private String tokenType;
    private String refreshToken;
    private Long expiresIn;
    private String csrf;

    public OAuth2Token() {
        this.token = "";
        this.tokenType = "";
        this.refreshToken = "";
        this.expiresIn = 0L;
        this.csrf = "";
    }

    public OAuth2Token(String token, String tokenType, String refreshToken, Long expiresIn, String csrf) {
        this.token = token;
        this.tokenType = tokenType;
        this.refreshToken = refreshToken;
        this.expiresIn = expiresIn;
        this.csrf = csrf;
    }

    public String getToken() {
        return token;
    }

    public OAuth2Token setToken(String token) {
        this.token = token;
        return this;
    }

    public String getTokenType() {
        return tokenType;
    }

    public OAuth2Token setTokenType(String tokenType) {
        this.tokenType = tokenType;
        return this;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public OAuth2Token setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
        return this;
    }

    public Long getExpiresIn() {
        return expiresIn;
    }

    public OAuth2Token setExpiresIn(Long expiresIn) {
        this.expiresIn = expiresIn;
        return this;
    }

    public String getCsrf() {
        return csrf;
    }

    public OAuth2Token setCsrf(String csrf) {
        this.csrf = csrf;
        return this;
    }
}
