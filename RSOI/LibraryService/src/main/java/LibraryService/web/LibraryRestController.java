package LibraryService.web;

import LibraryService.domain.Library;
import LibraryService.service.LibraryService;
import LibraryService.web.model.Request.LibraryRequest;
import LibraryService.web.model.Response.LibraryResponse;
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
@RequestMapping("/library")
public class LibraryRestController {
    @Autowired
    private LibraryService libraryService;

    @RequestMapping(value="/{id}", method = RequestMethod.GET)
    public LibraryResponse getLibrary(@PathVariable Long id) {
        return new LibraryResponse(libraryService.getById(id));
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<LibraryResponse> getAll() {
        return libraryService.getAll()
                .stream()
                .map(LibraryResponse::new)
                .collect(Collectors.toList());
    }

    @RequestMapping(method = RequestMethod.POST)
    public Long createLibrary(LibraryRequest libraryRequest, HttpServletResponse response) {
        Library library = libraryService.save(libraryRequest);
        response.setStatus(HttpStatus.CREATED.value());
        return library.getId();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PATCH)
    public Long updateLibrary(@PathVariable Long id, LibraryRequest libraryRequest, HttpServletResponse response) {
        Library library = libraryService.update(id, libraryRequest);
        response.setStatus(HttpStatus.CREATED.value());
        return library.getId();
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteLibrary(@PathVariable Long id, LibraryRequest libraryRequest, HttpServletResponse response) {
        libraryService.delete(id);
        response.setStatus(HttpStatus.NO_CONTENT.value());
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value = "/{libraryId}/book/{bookId}", method = RequestMethod.POST)
    public void addBookToLibrary(@PathVariable Long libraryId, @PathVariable Long bookId, HttpServletResponse response) {
        Long updatedLibraryId = libraryService.addBookToLibrary(libraryId,bookId);
        response.addHeader(HttpHeaders.LOCATION,"/library/"+updatedLibraryId);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value = "/{libraryId}/Book/{bookId}", method = RequestMethod.DELETE)
    public void deleteBookFromLibrary(@PathVariable Long libraryId, @PathVariable Long bookId, HttpServletResponse response) {
        Long updatedLibraryId = libraryService.deleteBookFromLibrary(libraryId,bookId);
        response.addHeader(HttpHeaders.LOCATION,"/library/"+updatedLibraryId);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value = "/{libraryId}/Visitor/{visitorId}", method = RequestMethod.POST)
    public void addVisitorToLibrary(@PathVariable Long libraryId, @PathVariable Long visitorId, HttpServletResponse response) {
        Long updatedLibraryId = libraryService.addVisitorToLibrary(libraryId,visitorId);
        response.addHeader(HttpHeaders.LOCATION,"/library/"+updatedLibraryId);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value = "/{libraryId}/Visitor/{visitorId}", method = RequestMethod.DELETE)
    public void deleteVisitorFromLibrary(@PathVariable Long libraryId, @PathVariable Long visitorId, HttpServletResponse response) {
        Long updatedLibraryId = libraryService.deleteVisitorFromLibrary(libraryId,visitorId);
        response.addHeader(HttpHeaders.LOCATION,"/library/"+updatedLibraryId);
    }

}
