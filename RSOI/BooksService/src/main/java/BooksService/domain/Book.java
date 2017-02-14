package BooksService.domain;

import com.google.common.base.MoreObjects;

import javax.persistence.*;
import java.util.List;

/**
 * Created by grigory on 1/24/17.
 */
@Entity
@Table(name = "RSOISv_book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="book_id")
    private  Long id;

    @Column
    private String name;

    @Column
    private Long year;

    @Column
    private String publisher;

    @Column
    @ElementCollection(targetClass=Long.class)
    private List<Long> authors;

    public Book() {
        this.name = "";
        this.year = 0L;
        this.publisher = "";
        this.authors = null;
    }

    public Book(String name, Long year, String publisher, List<Long> authors) {
        this.name = name;
        this.year = year;
        this.publisher = publisher;
        this.authors = authors;
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
                .add("id", id)
                .add("name", name)
                .add("year", year)
                .add("publisher", publisher)
                .add("authors", authors)
                .toString();
    }

    public Book addAuthor(Long authorId) {
        this.authors.add(authorId);
        return this;
    }

    public Book deleteAuthor(Long authorId) {
        this.authors.remove(authorId);
        return this;
    }
}
