package ServerSv.web.model.request;

import com.google.common.base.MoreObjects;

import java.util.List;

/**
 * Created by grigory on 1/24/17.
 */
public class VisitorRequest {
    private String lastName;
    private String firstName;
    private String middleName;

    public VisitorRequest() {
        this.lastName = "";
        this.firstName = "";
        this.middleName = "";
    }

    public VisitorRequest(String lastName, String firstName, String middleName, List<Long> books) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }


    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("lastName", lastName)
                .add("firstName", firstName)
                .add("middleName", middleName)
                .toString();
    }
}
