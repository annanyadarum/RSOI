package userService.web.model.Response;

import com.google.common.base.MoreObjects;
import userService.domain.User;

import java.util.List;


public class UserResponse {
    private String lastName;
    private String firstName;
    private  String middleName;
    private  String passwordHash;
    private String ClientId;
    private String SecretKey;
    private  String accessToken;
    private String refreshToken;
    private Long expiresIn;
    private List<Long> orders;

    public UserResponse() {
        lastName = "";
        firstName = "";
        middleName = "";
        passwordHash = "";
        ClientId = "";
        SecretKey = "";
        accessToken = "";
        refreshToken = "";
        expiresIn = 0L;
        orders = null;
    }

    public UserResponse(User user) {
        lastName = user.getLastName();
        firstName = user.getFirstName();
        middleName = user.getMiddleName();
        passwordHash = user.getPasswordHash();
        ClientId = user.getClientId();
        SecretKey = user.getSecretKey();
        accessToken = user.getAccessToken();
        refreshToken = user.getRefreshToken();
        expiresIn = user.getExpiresIn();
    }

    public UserResponse(UserResponse user) {
        lastName = user.getLastName();
        firstName = user.getFirstName();
        middleName = user.getMiddleName();
        passwordHash = user.getPasswordHash();
        ClientId = user.getClientId();
        SecretKey = user.getSecretKey();
        accessToken = user.getAccessToken();
        refreshToken = user.getRefreshToken();
        expiresIn = user.getExpiresIn();
        if (!user.getOrders().isEmpty())
            for(Long order: user.getOrders()) {
                orders.add(order);
            }
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
        return ClientId;
    }

    public String getSecretKey() {
        return SecretKey;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public List<Long> getOrders() {
        return orders;
    }

    public Long getExpiresIn() {
        return expiresIn;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("lastName", lastName)
                .add("firstName", firstName)
                .add("middleName", middleName)
                .add("passwordHash", passwordHash)
                .add("ClientId", ClientId)
                .add("SecretKey", SecretKey)
                .add("accessToken", accessToken)
                .add("refreshToken", refreshToken)
                .add("expiresIn", expiresIn)
                .add("orders", orders)
                .toString();
    }
}
