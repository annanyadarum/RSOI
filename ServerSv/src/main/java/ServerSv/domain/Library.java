package ServerSv.domain;

import com.google.common.base.MoreObjects;

import java.util.List;

/**
 * Created by grigory on 1/24/17.
 */
public class Library {
    private  Long id;
    private String name;
    private String location;
    private List<Long> books;
    private List<Long> visitors;


    public Library() {
        this.name = "";
        this.location = "";
        this.books = null;
        this.visitors = null;
    }

    public Library(String name, String location, List<Long> books, List<Long> visitors) {
        this.name = name;
        this.location = location;
        this.books = books;
        this.visitors = visitors;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<Long> getBooks() {
        return books;
    }

    public void setBooks(List<Long> books) {
        this.books = books;
    }

    public List<Long> getVisitors() {
        return visitors;
    }

    public void setVisitors(List<Long> visitors) {
        this.visitors = visitors;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("name", name)
                .add("location", location)
                .add("books", books)
                .add("visitors", visitors)
                .toString();
    }

    public Library addBook(Long bookId) {
        this.books.add(bookId);
        return this;
    }

    public Library deleteBook(Long bookId) {
        this.books.remove(bookId);
        return this;
    }

    public Library addVisitor(Long visitorId) {
        this.visitors.add(visitorId);
        return this;
    }

    public Library deleteVisitor(Long visitorId) {
        this.visitors.remove(visitorId);
        return this;
    }

}
