package VisitorsService.web.model.Response;

import VisitorsService.domain.Visitor;
import com.google.common.base.MoreObjects;

import javax.persistence.*;
import java.util.List;

/**
 * Created by grigory on 1/24/17.
 */
public class VisitorResponse {
    private String lastName;
    private String firstName;
    private String middleName;
    private List<Long> books;

    public VisitorResponse() {
        this.lastName = "";
        this.firstName = "";
        this.middleName = "";
        this.books = null;
    }

    public VisitorResponse(String lastName, String firstName, String middleName, List<Long> books) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.middleName = middleName;
        this.books = books;
    }

    public VisitorResponse(Visitor visitor) {
        this.lastName = visitor.getLastName();
        this.firstName = visitor.getFirstName();
        this.middleName = visitor.getMiddleName();
        this.books = visitor.getBooks();
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

    public List<Long> getBooks() {
        return books;
    }

    public void setBooks(List<Long> books) {
        this.books = books;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("lastName", lastName)
                .add("firstName", firstName)
                .add("middleName", middleName)
                .add("books", books)
                .toString();
    }
}
