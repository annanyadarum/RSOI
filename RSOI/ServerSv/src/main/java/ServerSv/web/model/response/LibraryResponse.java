package ServerSv.web.model.response;

import ServerSv.domain.Library;
import com.google.common.base.MoreObjects;

import java.util.List;

/**
 * Created by grigory on 1/24/17.
 */
public class LibraryResponse {
    private String name;
    private String location;
    private List<BookResponse> books;
    private List<VisitorResponse> visitors;


    public LibraryResponse() {
        this.name = "";
        this.location = "";
        this.books = null;
        this.visitors = null;
    }

    public LibraryResponse(String name, String location, List<BookResponse> books, List<VisitorResponse> visitors) {
        this.name = name;
        this.location = location;
        this.books = books;
        this.visitors = visitors;
    }

    public LibraryResponse(LibraryResponse libraryResponse) {
        this.name = libraryResponse.getName();
        this.location = libraryResponse.getLocation();
        this.books = libraryResponse.getBooks();
        this.visitors = libraryResponse.getVisitors();
    }

    public LibraryResponse(LibraryResponseSimple libraryResponseSimple) {
        this.name = libraryResponseSimple.getName();
        this.location = libraryResponseSimple.getLocation();
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

    public List<BookResponse> getBooks() {
        return books;
    }

    public void setBooks(List<BookResponse> books) {
        this.books = books;
    }

    public List<VisitorResponse> getVisitors() {
        return visitors;
    }

    public void setVisitors(List<VisitorResponse> visitors) {
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


    public void addVisitorToLibrary(VisitorResponse visitorResponse) {
        if (visitorResponse != null)
            visitors.add(visitorResponse);
    }

    public void addBookToLibrary(BookResponse bookResponse) {
        if (bookResponse != null)
            books.add(bookResponse);
    }
}
