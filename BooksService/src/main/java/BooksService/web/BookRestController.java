package BooksService.web;

import BooksService.domain.Book;
import BooksService.service.BookService;
import BooksService.web.model.Request.BookRequest;
import BooksService.web.model.Response.BookResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by grigory on 1/24/17.
 */
@RestController
@RequestMapping("/book")
public class BookRestController {
    @Autowired
    private BookService bookService;

    @RequestMapping(value="/{id}", method = RequestMethod.GET)
    public BookResponse getBook(@PathVariable Long id) {
        return new BookResponse(bookService.getById(id));
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<BookResponse> getAll() {
        return bookService.getAll()
                .stream()
                .map(BookResponse::new)
                .collect(Collectors.toList());
    }

    @RequestMapping(method = RequestMethod.POST)
    public Long createBook(BookRequest bookRequest, HttpServletResponse response) {
        Book book = bookService.save(bookRequest);
        response.setStatus(HttpStatus.CREATED.value());
        return book.getId();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PATCH)
    public Long updateBook(@PathVariable Long id, BookRequest bookRequest, HttpServletResponse response) {
        Book book = bookService.update(id, bookRequest);
        response.setStatus(HttpStatus.CREATED.value());
        return book.getId();
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteBook(@PathVariable Long id,BookRequest bookRequest, HttpServletResponse response) {
        bookService.delete(id);
        response.setStatus(HttpStatus.NO_CONTENT.value());
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value = "/{bookId}/author/{authorId}", method = RequestMethod.POST)
    public void addAuthorToBook(@PathVariable Long bookId, @PathVariable Long authorId, HttpServletResponse response) {
        Long updatedBookId = bookService.addAuthorToBook(bookId,authorId);
        response.addHeader(HttpHeaders.LOCATION,"/book/"+updatedBookId);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value = "/{bookId}/author/{authorId}", method = RequestMethod.DELETE)
    public void deleteAuthorFromBook(@PathVariable Long bookId, @PathVariable Long authorId, HttpServletResponse response) {
        Long updatedBookId = bookService.deleteAuthorFromBook(bookId,authorId);
        response.addHeader(HttpHeaders.LOCATION,"/book/"+updatedBookId);
    }
}
