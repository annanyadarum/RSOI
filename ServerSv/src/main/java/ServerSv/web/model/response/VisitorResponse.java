package ServerSv.web.model.response;

import ServerSv.domain.Visitor;
import com.google.common.base.MoreObjects;

import java.util.List;

/**
 * Created by grigory on 1/24/17.
 */
public class VisitorResponse {
    private String lastName;
    private String firstName;
    private String middleName;
    private List<BookResponse> books;

    public VisitorResponse() {
        this.lastName = "";
        this.firstName = "";
        this.middleName = "";
        this.books = null;
    }

    public VisitorResponse(String lastName, String firstName, String middleName, List<BookResponse> books) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.middleName = middleName;
        this.books = books;
    }

    public VisitorResponse(VisitorResponse visitorResponse) {
        this.lastName = visitorResponse.getLastName();
        this.firstName = visitorResponse.getFirstName();
        this.middleName = visitorResponse.getMiddleName();
        this.books = visitorResponse.getBooks();
    }

    public VisitorResponse(VisitorResponseSimple visitorResponseSimple) {
        this.lastName = visitorResponseSimple.getLastName();
        this.firstName = visitorResponseSimple.getFirstName();
        this.middleName = visitorResponseSimple.getMiddleName();
        this.books = null;
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

    public List<BookResponse> getBooks() {
        return books;
    }

    public void setBooks(List<BookResponse> books) {
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

    public void addBookToVisitor(BookResponse bookResponse) {
        if (bookResponse != null)
            books.add(bookResponse);
    }
}
