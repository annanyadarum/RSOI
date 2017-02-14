package AuthorsService.web;

import AuthorsService.domain.Author;
import AuthorsService.service.AuthorService;
import AuthorsService.web.model.Request.AuthorRequest;
import AuthorsService.web.model.Response.AuthorResponse;
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
@RequestMapping("/author")
public class AuthorRestController {
    @Autowired
    private AuthorService authorService;

    @RequestMapping(value="/{id}", method = RequestMethod.GET)
    public AuthorResponse getAuthor(@PathVariable Long id) {
        return new AuthorResponse(authorService.getById(id));
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<AuthorResponse> getAll() {
        return authorService.getAll()
                .stream()
                .map(AuthorResponse::new)
                .collect(Collectors.toList());
    }

    @RequestMapping(method = RequestMethod.POST)
    public Long createBook(AuthorRequest authorRequest, HttpServletResponse response) {
        Author author = authorService.save(authorRequest);
        response.setStatus(HttpStatus.CREATED.value());
        return author.getId();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PATCH)
    public Long updateBook(@PathVariable Long id, AuthorRequest authorRequest, HttpServletResponse response) {
        Author author = authorService.update(id, authorRequest);
        response.setStatus(HttpStatus.CREATED.value());
        return author.getId();
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteAuthor(@PathVariable Long id, AuthorRequest authorRequest, HttpServletResponse response) {
        authorService.delete(id);
        response.setStatus(HttpStatus.NO_CONTENT.value());
    }
}