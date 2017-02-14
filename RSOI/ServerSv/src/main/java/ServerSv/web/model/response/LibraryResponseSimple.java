package ServerSv.web.model.response;

import ServerSv.domain.Library;
import com.google.common.base.MoreObjects;

import java.util.List;

/**
 * Created by grigory on 1/24/17.
 */
public class LibraryResponseSimple {
    private String name;
    private String location;
    private List<Long> books;
    private List<Long> visitors;


    public LibraryResponseSimple() {
        this.name = "";
        this.location = "";
        this.books = null;
        this.visitors = null;
    }

    public LibraryResponseSimple(String name, String location, List<Long> books, List<Long> visitors) {
        this.name = name;
        this.location = location;
        this.books = books;
        this.visitors = visitors;
    }

    public LibraryResponseSimple(Library library) {
        this.name = library.getName();
        this.location = library.getLocation();
        this.books = library.getBooks();
        this.visitors = library.getVisitors();
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
                .add("name", name)
                .add("location", location)
                .add("books", books)
                .add("visitors", visitors)
                .toString();
    }

}
