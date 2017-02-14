package BooksService.service;

import BooksService.domain.Book;
import BooksService.repository.BookRepository;
import BooksService.web.model.Request.BookRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

/**
 * Created by grigory on 1/24/17.
 */
@Service
public class BookServiceImpl implements BookService{
    @Autowired
    private BookRepository bookRepository;

    @Override
    public Book getById(Long id) {
        Book book = bookRepository.findOne(id);
        if (book == null) {
            throw new EntityNotFoundException("Book '{" + id + "}' not found");
        }
        return book;
    }

    @Override
    public List<Book> getAll() {
        return bookRepository.findAll();
    }

    @Override
    public Book save(BookRequest bookRequest) {
        Book book = new Book(bookRequest.getName(), bookRequest.getYear(), bookRequest.getPublisher(), null);
        return bookRepository.save(book);
    }

    @Override
    public Book update(Long id, BookRequest bookRequest) {
        Book book = bookRepository.findOne(id);
        if (book == null) {
            throw new EntityNotFoundException("Book '{" + id + "}' not found");
        }

        book.setName(bookRequest.getName() != null ? bookRequest.getName() : book.getName());
        book.setYear(bookRequest.getYear() != null ? bookRequest.getYear() : book.getYear());
        book.setPublisher(bookRequest.getPublisher() != null ? bookRequest.getPublisher() : book.getPublisher());
        return bookRepository.save(book);
    }

    @Override
    public void delete(Long id) {
        bookRepository.delete(id);
    }

    @Override
    public Long addAuthorToBook(Long authorId, Long bookId) {
        Book book = bookRepository.findOne(bookId);
        if (book == null) {
            throw new EntityNotFoundException("Book '{" + id + "}' not found");
        }

        book.addAuthor(authorId);
        bookRepository.save(book);
        return book.getId();
    }

    @Override
    public Long deleteAuthorFromBook(Long authorId, Long bookId) {
        Book book = bookRepository.findOne(bookId);
        if (book == null) {
            throw new EntityNotFoundException("Book '{" + id + "}' not found");
        }

        book.deleteAuthor(authorId);
        bookRepository.save(book);
        return book.getId();
    }
}
