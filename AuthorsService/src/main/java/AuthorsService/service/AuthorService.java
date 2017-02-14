package AuthorsService.service;

import AuthorsService.domain.Author;
import AuthorsService.web.model.Request.AuthorRequest;

import java.util.List;

/**
 * Created by grigory on 1/24/17.
 */
public interface AuthorService {
    Author getById(Long id);

    List<Author> getAll();

    Author save(AuthorRequest bookRequest);

    Author update(Long id, AuthorRequest bookRequest);

    void delete(Long id);
}
