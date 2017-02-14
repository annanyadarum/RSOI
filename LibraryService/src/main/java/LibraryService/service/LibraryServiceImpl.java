package LibraryService.service;

import LibraryService.domain.Library;
import LibraryService.repository.LibraryRepository;
import LibraryService.web.model.Request.LibraryRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

/**
 * Created by grigory on 1/24/17.
 */
@Service
public class LibraryServiceImpl implements LibraryService {
    @Autowired
    private LibraryRepository libraryRepository;

    @Override
    @Transactional(readOnly = true)
    public Library getById(Long id) {
        Library library = libraryRepository.findOne(id);
        if (library == null) {
            throw new EntityNotFoundException("Library '{" + id + "}' not found");
        }
        return library;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Library> getAll() {
        return libraryRepository.findAll();
    }

    @Override
    @Transactional
    public Library save(LibraryRequest libraryRequest) {
        Library buffet = new Library(libraryRequest.getName(), libraryRequest.getLocation(),null,null);
        return libraryRepository.save(buffet);
    }

    @Override
    @Transactional
    public Library update(Long id, LibraryRequest libraryRequest) {
        Library library = libraryRepository.findOne(id);
        if (library == null) {
            throw new EntityNotFoundException("Library '{" + id + "}' not found");
        }

        library.setName(libraryRequest.getName() != null ? libraryRequest.getName() : library.getName());
        library.setLocation(libraryRequest.getLocation() != null ? libraryRequest.getLocation() : library.getLocation());

        return libraryRepository.save(library);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        libraryRepository.delete(id);
    }

    @Override
    @Transactional
    public Long addBookToLibrary(Long libraryId, Long bookId) {
        Library library = libraryRepository.findOne(libraryId);
        if (library == null) {
            throw new EntityNotFoundException("Library '{" + id + "}' not found");
        }

        library.addBook(bookId);
        libraryRepository.save(library);
        return library.getId();
    }

    @Override
    @Transactional
    public Long deleteBookFromLibrary(Long libraryId, Long bookId) {
        Library library = libraryRepository.findOne(libraryId);
        if (library == null) {
            throw new EntityNotFoundException("Library '{" + id + "}' not found");
        }

        library.deleteBook(bookId);
        libraryRepository.save(library);
        return library.getId();
    }

    @Override
    public Long addVisitorToLibrary(Long libraryId, Long visitorId) {
        Library library = libraryRepository.findOne(libraryId);
        if (library == null) {
            throw new EntityNotFoundException("Library '{" + id + "}' not found");
        }

        library.addVisitor(visitorId);
        libraryRepository.save(library);
        return library.getId();
    }

    @Override
    public Long deleteVisitorFromLibrary(Long libraryId, Long visitorId) {
        Library library = libraryRepository.findOne(libraryId);
        if (library == null) {
            throw new EntityNotFoundException("Library '{" + id + "}' not found");
        }

        library.deleteVisitor(visitorId);
        libraryRepository.save(library);
        return library.getId();
    }


}
