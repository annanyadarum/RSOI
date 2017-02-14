package LibraryService.service;

import LibraryService.domain.Library;
import LibraryService.web.model.Request.LibraryRequest;

import java.util.List;

/**
 * Created by grigory on 1/24/17.
 */
public interface LibraryService {

    Library getById(Long id);

    List<Library> getAll();

    Library save(LibraryRequest libraryRequest);

    Library update(Long id, LibraryRequest libraryRequest);

    void delete(Long id);

    Long addBookToLibrary(Long libraryId, Long bookId);

    Long deleteBookFromLibrary(Long libraryId, Long bookId);

    Long addVisitorToLibrary(Long libraryId, Long visitoId);

    Long deleteVisitorFromLibrary(Long libraryId, Long visitorId);
}
