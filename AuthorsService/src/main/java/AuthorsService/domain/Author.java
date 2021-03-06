package AuthorsService.domain;

import com.google.common.base.MoreObjects;

import javax.persistence.*;

/**
 * Created by grigory on 1/24/17.
 */
@Entity
@Table(name = "RSOISv_author")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="author_id")
    private  Long id;

    @Column
    private String lastName;

    @Column
    private String firstName;

    @Column
    private String middleName;

    @Column
    private String country;

    public Author() {
        this.lastName = "";
        this.firstName = "";
        this.middleName = "";
        this.country = "";
    }

    public Author(String lastName, String firstName, String middleName, String country) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.middleName = middleName;
        this.country = country;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
                .add("id", id)
                .add("lastName", lastName)
                .add("firstName", firstName)
                .add("middleName", middleName)
                .add("country", country)
                .toString();
    }
}
