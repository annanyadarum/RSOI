package LibraryService.repository;

import LibraryService.domain.Library;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by grigory on 1/24/17.
 */
public interface LibraryRepository extends JpaRepository<Library,Long> {
}
