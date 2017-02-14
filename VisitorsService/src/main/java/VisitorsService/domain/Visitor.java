package VisitorsService.domain;

import com.google.common.base.MoreObjects;

import javax.persistence.*;
import java.util.List;

/**
 * Created by grigory on 1/24/17.
 */
@Entity
@Table(name = "RSOISv_visitor")
public class Visitor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="visitor_id")
    private  Long id;

    @Column
    private String lastName;

    @Column
    private String firstName;

    @Column
    private String middleName;

    @Column
    @ElementCollection(targetClass=Long.class)
    private List<Long> books;

    public Visitor() {
        this.lastName = "";
        this.firstName = "";
        this.middleName = "";
        this.books = null;
    }

    public Visitor(String lastName, String firstName, String middleName, List<Long> books) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.middleName = middleName;
        this.books = books;
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

    public List<Long> getBooks() {
        return books;
    }

    public void setBooks(List<Long> books) {
        this.books = books;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("lastName", lastName)
                .add("firstName", firstName)
                .add("middleName", middleName)
                .add("books", books)
                .toString();
    }

    public Visitor addBook(Long bookId) {
        this.books.add(bookId);
        return this;
    }

    public Visitor deleteBook(Long bookId) {
        this.books.remove(bookId);
        return this;
    }
}
