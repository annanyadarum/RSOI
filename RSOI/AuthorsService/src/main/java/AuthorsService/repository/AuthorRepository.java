package AuthorsService.repository;

import AuthorsService.domain.Author;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by grigory on 1/24/17.
 */
public interface AuthorRepository extends JpaRepository<Author,Long> {
}
