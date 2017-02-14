package AuthorsService.service;

import AuthorsService.domain.Author;
import AuthorsService.repository.AuthorRepository;
import AuthorsService.web.model.Request.AuthorRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

/**
 * Created by grigory on 1/24/17.
 */
@Service
public class AuthorServiceImpl implements AuthorService {
    @Autowired
    private AuthorRepository authorRepository;

    @Override
    public Author getById(Long id) {
        Author author = authorRepository.findOne(id);
        if (author == null) {
            throw new EntityNotFoundException("Author '{" + id + "}' not found");
        }
        return author;
    }

    @Override
    public List<Author> getAll() {
        return authorRepository.findAll();
    }

    @Override
    public Author save(AuthorRequest authorRequest) {
        Author author = new Author(authorRequest.getLastName(), authorRequest.getFirstName(), authorRequest.getMiddleName(), authorRequest.getCountry());
        return authorRepository.save(author);
    }

    @Override
    public Author update(Long id, AuthorRequest authorRequest) {
        Author author = authorRepository.findOne(id);
        if (author == null) {
            throw new EntityNotFoundException("Author '{" + id + "}' not found");
        }

        author.setLastName(authorRequest.getLastName() != null ? authorRequest.getLastName() : author.getLastName());
        author.setFirstName(authorRequest.getFirstName() != null ? authorRequest.getFirstName() : author.getFirstName());
        author.setMiddleName(authorRequest.getMiddleName() != null ? authorRequest.getMiddleName() : author.getMiddleName());
        author.setCountry(authorRequest.getCountry() != null ? authorRequest.getCountry() : author.getCountry());
        return authorRepository.save(author);
    }

    @Override
    public void delete(Long id) {
        authorRepository.delete(id);
    }
}
