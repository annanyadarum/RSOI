package BooksService.repository;

import BooksService.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by grigory on 1/24/17.
 */
public interface BookRepository extends JpaRepository<Book,Long> {
}
