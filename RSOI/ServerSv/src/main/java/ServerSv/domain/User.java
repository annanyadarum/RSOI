package ServerSv.domain;

import com.google.common.base.MoreObjects;

import java.util.Random;

/**
 * Created by grigory on 11/26/16.
 */
public class User {
    private Long id;
    private String lastName;
    private String firstName;
    private  String middleName;
    private String clientId;
    private String secretKey;
    private  String accessToken;
    private String refreshToken;
    private Long expiresIn;
    private  String passwordHash;

    public Long getId() {
        return id;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public String getClientId() {
        return clientId;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public User setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public User setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public User setMiddleName(String middleName) {
        this.middleName = middleName;
        return this;
    }

    public User setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
        return this;
    }

    public User setClientId(String clientId) {
        this.clientId = clientId;
        return this;
    }

    public User setSecretKey(String secretKey) {
        secretKey = secretKey;
        return this;
    }

    public User setAccessToken(String accessToken) {
        this.accessToken = accessToken;
        return this;
    }

    public User setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
        return this;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("lastName", lastName)
                .add("firstName", firstName)
                .add("middleName", middleName)
                .add("clientId", clientId)
                .add("SecretKey", secretKey)
                .add("accessToken", accessToken)
                .add("refreshToken", refreshToken)
                .add("expiresIn", expiresIn)
                .add("passwordHash", passwordHash)
                .toString();
    }

    public User(String lastName, String firstName, String middleName, String clientId, String passwordHash, String secretKey, String accessToken, String refreshToken, Long expiresIn) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.middleName = middleName;
        this.passwordHash = passwordHash;
        this.clientId = clientId;
        this.secretKey = secretKey;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.expiresIn = expiresIn;
    }

    public User(String lastName, String firstName, String middleName, String clientId, String passwordHash) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.middleName = middleName;
        this.passwordHash = passwordHash;
        this.clientId = clientId;
        this.secretKey = generateHeximalString(16);
        this.accessToken = generateHeximalString(16);
        this.refreshToken = generateHeximalString(16);
        this.expiresIn = System.currentTimeMillis() + 120000L; // token действует 2 минуты
    }

    public Long getExpiresIn() {
        return expiresIn;
    }

    public User setExpiresIn(Long expiresIn) {
        this.expiresIn = expiresIn;
        return this;
    }

    public User setId(Long id) {
        this.id = id;
        return this;
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

    public User() {
    }

    public void updateToken() {
        this.accessToken = generateHeximalString(16);
        this.refreshToken = generateHeximalString(16);
        this.expiresIn = System.currentTimeMillis() + 120000L; // token действует 2 минуты
    }
}
