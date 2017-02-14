package ServerSv.web.model.request;

import com.google.common.base.MoreObjects;


public class UserRequest {
    private String lastName;
    private String firstName;
    private  String middleName;
    private String clientId;
    private  String passwordHash;

    public String getLastName() {
        return lastName;
    }

    public UserRequest setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public UserRequest setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getMiddleName() {
        return middleName;
    }

    public UserRequest setMiddleName(String middleName) {
        this.middleName = middleName;
        return this;
    }

    public String getClientId() {
        return clientId;
    }

    public UserRequest setClientId(String clientId) {
        this.clientId = clientId;
        return this;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public UserRequest setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
        return this;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("lastName", lastName)
                .add("firstName", firstName)
                .add("middleName", middleName)
                .add("clientId", clientId)
                .add("passwordHash", passwordHash)
                .toString();
    }

    public UserRequest() {
        this.lastName = "";
        this.firstName = "";
        this.middleName = "";
        this.clientId = "";
        this.passwordHash = "";
    }

}
