package ServerSv.web.model.response;

import ServerSv.domain.Book;
import com.google.common.base.MoreObjects;

import java.util.List;

/**
 * Created by grigory on 1/24/17.
 */
public class BookResponse {
    private String name;
    private Long year;
    private String publisher;
    private List<AuthorResponse> authors;

    public BookResponse() {
        this.name = "";
        this.year = 0L;
        this.publisher = "";
        this.authors = null;
    }

    public BookResponse(String name, Long year, String publisher, List<AuthorResponse> authors) {
        this.name = name;
        this.year = year;
        this.publisher = publisher;
        this.authors = authors;
    }

    public BookResponse(BookResponse bookResponse) {
        this.name = bookResponse.getName();
        this.year = bookResponse.getYear();
        this.publisher = bookResponse.getPublisher();
        this.authors = bookResponse.getAuthors();
    }

    public BookResponse(BookResponseSimple bookResponseSimple) {
        this.name = bookResponseSimple.getName();
        this.year = bookResponseSimple.getYear();
        this.publisher = bookResponseSimple.getPublisher();
        this.authors = null;
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

    public List<AuthorResponse> getAuthors() {
        return authors;
    }

    public void setAuthors(List<AuthorResponse> authors) {
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

    public void addAuthorToBook(AuthorResponse authorResponse) {
        if (authorResponse != null)
            authors.add(authorResponse);
    }
}
