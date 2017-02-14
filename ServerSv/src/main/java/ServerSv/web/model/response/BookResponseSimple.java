package ServerSv.web.model.response;

import ServerSv.domain.Book;
import com.google.common.base.MoreObjects;

import java.util.List;

/**
 * Created by grigory on 1/24/17.
 */
public class BookResponseSimple {
    private String name;
    private Long year;
    private String publisher;
    private List<Long> authors;

    public BookResponseSimple() {
        this.name = "";
        this.year = 0L;
        this.publisher = "";
        this.authors = null;
    }

    public BookResponseSimple(String name, Long year, String publisher, List<Long> authors) {
        this.name = name;
        this.year = year;
        this.publisher = publisher;
        this.authors = authors;
    }

    public BookResponseSimple(Book book) {
        this.name = book.getName();
        this.year = book.getYear();
        this.publisher = book.getPublisher();
        this.authors = book.getAuthors();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getYear() {
        return year;
    }

    public void setYear(Long year) {
        this.year = year;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public List<Long> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Long> authors) {
        this.authors = authors;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("name", name)
                .add("year", year)
                .add("publisher", publisher)
                .add("authors", authors)
                .toString();
    }
}
