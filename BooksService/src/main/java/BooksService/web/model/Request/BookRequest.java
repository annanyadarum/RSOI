package BooksService.web.model.Request;

import BooksService.domain.Book;
import com.google.common.base.MoreObjects;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import java.util.List;

/**
 * Created by grigory on 1/24/17.
 */
public class BookRequest {
    private String name;
    private Long year;
    private String publisher;

    public BookRequest() {
        this.name = "";
        this.year = 0L;
        this.publisher = "";
    }

    public BookRequest(String name, Long year, String publisher, List<Long> authors) {
        this.name = name;
        this.year = year;
        this.publisher = publisher;
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

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("name", name)
                .add("year", year)
                .add("publisher", publisher)
                .toString();
    }
}
