package BooksService.service;

import BooksService.domain.Book;
import BooksService.web.model.Request.BookRequest;

import java.util.List;

/**
 * Created by grigory on 1/24/17.
 */
public interface BookService {

    Book getById(Long id);

    List<Book> getAll();

    Book save(BookRequest bookRequest);

    Book update(Long id, BookRequest bookRequest);

    void delete(Long id);

    Long addAuthorToBook(Long authorId, Long bookId);

    Long deleteAuthorFromBook(Long authorId, Long bookId);
}
