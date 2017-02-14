package ServerSv.web.model.response;

import ServerSv.domain.Author;
import com.google.common.base.MoreObjects;

/**
 * Created by grigory on 1/24/17.
 */
public class AuthorResponse {
    private String lastName;
    private String firstName;
    private String middleName;
    private String country;

    public AuthorResponse() {
        this.lastName = "";
        this.firstName = "";
        this.middleName = "";
        this.country = "";
    }

    public AuthorResponse(String lastName, String firstName, String middleName, String country) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.middleName = middleName;
        this.country = country;
    }

    public AuthorResponse(Author author) {
        this.lastName = author.getLastName();
        this.firstName = author.getFirstName();
        this.middleName = author.getMiddleName();
        this.country = author.getCountry();
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

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("lastName", lastName)
                .add("firstName", firstName)
                .add("middleName", middleName)
                .add("country", country)
                .toString();
    }
}
