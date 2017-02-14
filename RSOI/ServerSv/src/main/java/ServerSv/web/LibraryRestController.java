package ServerSv.web;

import ServerSv.web.model.request.LibraryRequest;
import ServerSv.web.model.response.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import java.net.URI;

@RestController
@RequestMapping("/library")
public class LibraryRestController {
    private final String LibraryUrl = "http://localhost:8083/library/";
    private final String VisitorUrl = "http://localhost:8084/visitor/";
    private final String BookUrl = "http://localhost:8085/book/";
    private final String AuthorUrl = "http://localhost:8086/author/";
    private final String checkTokenUrl = "http://localhost:8086/user/checkToken";


    @RequestMapping(value="/{id}", method = RequestMethod.GET)
    public LibraryResponse getBuffet(@PathVariable Long id) {
        RestTemplate restTemplate = new RestTemplate();
        String address = LibraryUrl + Long.toString(id);
        URI uri = UriComponentsBuilder.fromUriString(address).build().toUri();
        LibraryResponseSimple libraryResponseSimple = restTemplate.getForObject(uri, LibraryResponseSimple.class);
        LibraryResponse libraryResponse = new LibraryResponse(libraryResponseSimple);

        for (Long visitorId: libraryResponseSimple.getVisitors()) {
            String addressVisitor = VisitorUrl + Long.toString(visitorId);
            URI uriVisitor = UriComponentsBuilder.fromUriString(addressVisitor).build().toUri();
            VisitorResponseSimple visitorResponseSimple = restTemplate.getForObject(uriVisitor, VisitorResponseSimple.class);
            VisitorResponse visitorResponse = new VisitorResponse(visitorResponseSimple);

            for (Long bookId: visitorResponseSimple.getBooks()) {
                String addressBook = BookUrl + Long.toString(bookId);
                URI uriBook = UriComponentsBuilder.fromUriString(addressBook).build().toUri();
                BookResponseSimple bookResponseSimple = restTemplate.getForObject(uriBook, BookResponseSimple.class);
                BookResponse bookResponse = new BookResponse(bookResponseSimple);

                if (bookResponseSimple.getAuthors() != null)
                    for (Long authorId: bookResponseSimple.getAuthors()) {
                        String addressAuthor = AuthorUrl+ Long.toString(authorId);
                        URI uriAuthor = UriComponentsBuilder.fromUriString(addressAuthor).build().toUri();
                        bookResponse.addAuthorToBook(restTemplate.getForObject(uriAuthor, AuthorResponse.class));
                    }
                visitorResponse.addBookToVisitor(bookResponse);
            }
            libraryResponse.addVisitorToLibrary(visitorResponse);
        }

        for (Long bookId: libraryResponseSimple.getBooks()) {
            String addressBook = BookUrl + Long.toString(bookId);
            URI uriBook = UriComponentsBuilder.fromUriString(addressBook).build().toUri();
            BookResponseSimple bookResponseSimple = restTemplate.getForObject(uriBook, BookResponseSimple.class);
            BookResponse bookResponse = new BookResponse(bookResponseSimple);

            if (bookResponseSimple.getAuthors() != null)
                for (Long authorId: bookResponseSimple.getAuthors()) {
                    String addressAuthor = AuthorUrl+ Long.toString(authorId);
                    URI uriAuthor = UriComponentsBuilder.fromUriString(addressAuthor).build().toUri();
                    bookResponse.addAuthorToBook(restTemplate.getForObject(uriAuthor, AuthorResponse.class));
                }
            libraryResponse.addBookToLibrary(bookResponse);
        }
        return libraryResponse;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ListOfLibraryResponses getAll() {
        RestTemplate restTemplate = new RestTemplate();
        URI uri = UriComponentsBuilder.fromUriString(LibraryUrl).build().toUri();
        ResponseEntity<ListOfLibraryResponseSimple> response = restTemplate.getForEntity(uri, ListOfLibraryResponseSimple.class);
        ListOfLibraryResponseSimple listOfLibraryResponseSimple = response.getBody();
        ListOfLibraryResponses listOfLibraryResponses = new ListOfLibraryResponses();

        for (LibraryResponseSimple libraryResponseSimple : listOfLibraryResponseSimple.getLibraryResponseSimples()) {
            LibraryResponse libraryResponse = new LibraryResponse(libraryResponseSimple);
            for (Long visitorId: libraryResponseSimple.getVisitors()) {
                String addressVisitor = VisitorUrl + Long.toString(visitorId);
                URI uriVisitor = UriComponentsBuilder.fromUriString(addressVisitor).build().toUri();
                VisitorResponseSimple visitorResponseSimple = restTemplate.getForObject(uriVisitor, VisitorResponseSimple.class);
                VisitorResponse visitorResponse = new VisitorResponse(visitorResponseSimple);

                for (Long bookId: visitorResponseSimple.getBooks()) {
                    String addressBook = BookUrl + Long.toString(bookId);
                    URI uriBook = UriComponentsBuilder.fromUriString(addressBook).build().toUri();
                    BookResponseSimple bookResponseSimple = restTemplate.getForObject(uriBook, BookResponseSimple.class);
                    BookResponse bookResponse = new BookResponse(bookResponseSimple);

                    if (bookResponseSimple.getAuthors() != null)
                        for (Long authorId: bookResponseSimple.getAuthors()) {
                            String addressAuthor = AuthorUrl+ Long.toString(authorId);
                            URI uriAuthor = UriComponentsBuilder.fromUriString(addressAuthor).build().toUri();
                            bookResponse.addAuthorToBook(restTemplate.getForObject(uriAuthor, AuthorResponse.class));
                        }
                    visitorResponse.addBookToVisitor(bookResponse);
                }
                libraryResponse.addVisitorToLibrary(visitorResponse);
            }

            for (Long bookId: libraryResponseSimple.getBooks()) {
                String addressBook = BookUrl + Long.toString(bookId);
                URI uriBook = UriComponentsBuilder.fromUriString(addressBook).build().toUri();
                BookResponseSimple bookResponseSimple = restTemplate.getForObject(uriBook, BookResponseSimple.class);
                BookResponse bookResponse = new BookResponse(bookResponseSimple);

                if (bookResponseSimple.getAuthors() != null)
                    for (Long authorId: bookResponseSimple.getAuthors()) {
                        String addressAuthor = AuthorUrl+ Long.toString(authorId);
                        URI uriAuthor = UriComponentsBuilder.fromUriString(addressAuthor).build().toUri();
                        bookResponse.addAuthorToBook(restTemplate.getForObject(uriAuthor, AuthorResponse.class));
                    }
                libraryResponse.addBookToLibrary(bookResponse);
            }
            listOfLibraryResponses.addLibraryResponse(libraryResponse);
        }
        return listOfLibraryResponses;
    }

    @RequestMapping(value = "/{startIndex}/{finishIndex}", method = RequestMethod.GET)
    public ListOfLibraryResponses getAllPagination(@PathVariable Integer startIndex, @PathVariable Integer finishIndex) {
        RestTemplate restTemplate = new RestTemplate();
        URI uri = UriComponentsBuilder.fromUriString(LibraryUrl + startIndex + "/" + finishIndex).build().toUri();
        ResponseEntity<ListOfLibraryResponseSimple> response = restTemplate.getForEntity(uri, ListOfLibraryResponseSimple.class);
        ListOfLibraryResponseSimple listOfLibraryResponseSimple = response.getBody();
        ListOfLibraryResponses listOfLibraryResponses = new ListOfLibraryResponses();

        for (LibraryResponseSimple libraryResponseSimple : listOfLibraryResponseSimple.getLibraryResponseSimples()) {
            LibraryResponse libraryResponse = new LibraryResponse(libraryResponseSimple);
            for (Long visitorId: libraryResponseSimple.getVisitors()) {
                String addressVisitor = VisitorUrl + Long.toString(visitorId);
                URI uriVisitor = UriComponentsBuilder.fromUriString(addressVisitor).build().toUri();
                VisitorResponseSimple visitorResponseSimple = restTemplate.getForObject(uriVisitor, VisitorResponseSimple.class);
                VisitorResponse visitorResponse = new VisitorResponse(visitorResponseSimple);

                for (Long bookId: visitorResponseSimple.getBooks()) {
                    String addressBook = BookUrl + Long.toString(bookId);
                    URI uriBook = UriComponentsBuilder.fromUriString(addressBook).build().toUri();
                    BookResponseSimple bookResponseSimple = restTemplate.getForObject(uriBook, BookResponseSimple.class);
                    BookResponse bookResponse = new BookResponse(bookResponseSimple);

                    if (bookResponseSimple.getAuthors() != null)
                        for (Long authorId: bookResponseSimple.getAuthors()) {
                            String addressAuthor = AuthorUrl+ Long.toString(authorId);
                            URI uriAuthor = UriComponentsBuilder.fromUriString(addressAuthor).build().toUri();
                            bookResponse.addAuthorToBook(restTemplate.getForObject(uriAuthor, AuthorResponse.class));
                        }
                    visitorResponse.addBookToVisitor(bookResponse);
                }
                libraryResponse.addVisitorToLibrary(visitorResponse);
            }

            for (Long bookId: libraryResponseSimple.getBooks()) {
                String addressBook = BookUrl + Long.toString(bookId);
                URI uriBook = UriComponentsBuilder.fromUriString(addressBook).build().toUri();
                BookResponseSimple bookResponseSimple = restTemplate.getForObject(uriBook, BookResponseSimple.class);
                BookResponse bookResponse = new BookResponse(bookResponseSimple);

                if (bookResponseSimple.getAuthors() != null)
                    for (Long authorId: bookResponseSimple.getAuthors()) {
                        String addressAuthor = AuthorUrl+ Long.toString(authorId);
                        URI uriAuthor = UriComponentsBuilder.fromUriString(addressAuthor).build().toUri();
                        bookResponse.addAuthorToBook(restTemplate.getForObject(uriAuthor, AuthorResponse.class));
                    }
                libraryResponse.addBookToLibrary(bookResponse);
            }
            listOfLibraryResponses.addLibraryResponse(libraryResponse);
        }
        return listOfLibraryResponses;
    }

    @RequestMapping(method = RequestMethod.POST)
    public Long createLibrary(HttpEntity<LibraryRequest> fullLibraryRequest, HttpServletResponse response) {
        LibraryRequest libraryRequest = fullLibraryRequest.getBody();
        HttpHeaders header = fullLibraryRequest.getHeaders();
        String authHeader = header.get("Authorization").toString();
        String accesstoken = authHeader.substring(authHeader.indexOf('[')+1,authHeader.lastIndexOf(']'));

        RestTemplate restTemplate = new RestTemplate();
        URI uri = UriComponentsBuilder.fromUriString(checkTokenUrl).build().toUri();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Token",accesstoken);
        HttpEntity httpEntity = new HttpEntity<>(null, headers);

        ResponseEntity<Boolean> responseFlag = restTemplate.exchange(uri, HttpMethod.GET, httpEntity, Boolean.class);

        if (responseFlag.getBody()) {
            RestTemplate restTemplateLibrary = new RestTemplate();
            URI uriLibrary = UriComponentsBuilder.fromUriString(LibraryUrl).build().toUri();
            HttpEntity<LibraryRequest> httpEntityLibrary = new HttpEntity<>(libraryRequest, null);

            ResponseEntity<Long> responseLibrary = restTemplateLibrary.exchange(uriLibrary, HttpMethod.POST, httpEntityLibrary, Long.class);
            response.setStatus(HttpStatus.CREATED.value());
            return responseLibrary.getBody();
        }
        else
        {
            response.setStatus(401);//HttpStatus.UNAUTHORIZED.value());
            return 0L;
        }

    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PATCH)
    public Long updateLibrary(@PathVariable Long id, HttpEntity<LibraryRequest> fullLibraryRequest, HttpServletResponse response) {
        LibraryRequest libraryRequest = fullLibraryRequest.getBody();
        HttpHeaders header = fullLibraryRequest.getHeaders();
        String authHeader = header.get("Authorization").toString();
        String accesstoken = authHeader.substring(authHeader.indexOf('[')+1,authHeader.lastIndexOf(']'));

        RestTemplate restTemplate = new RestTemplate();
        URI uri = UriComponentsBuilder.fromUriString(checkTokenUrl).build().toUri();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Token",accesstoken);
        HttpEntity httpEntity = new HttpEntity<>(null, headers);

        ResponseEntity<Boolean> responseFlag = restTemplate.exchange(uri, HttpMethod.GET, httpEntity, Boolean.class);

        if (responseFlag.getBody()) {

            RestTemplate restTemplateLibrary = new RestTemplate();
            URI uriLibrary = UriComponentsBuilder.fromUriString(LibraryUrl).build().toUri();
            HttpEntity<LibraryRequest> httpEntityLibrary = new HttpEntity<>(libraryRequest, null);

            ResponseEntity<Long> responseLibrary = restTemplateLibrary.exchange(uriLibrary, HttpMethod.PUT, httpEntityLibrary, Long.class);
            response.setStatus(HttpStatus.CREATED.value());
            return responseLibrary.getBody();
        }
        else
        {
            response.setStatus(401);//HttpStatus.UNAUTHORIZED.value());
            return 0L;
        }
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteLibrary(@PathVariable Long id, HttpEntity<LibraryRequest> fullLibraryRequest, HttpServletResponse response) {
        LibraryRequest libraryRequest = fullLibraryRequest.getBody();
        HttpHeaders header = fullLibraryRequest.getHeaders();
        String authHeader = header.get("Authorization").toString();
        String accesstoken = authHeader.substring(authHeader.indexOf('[')+1,authHeader.lastIndexOf(']'));

        RestTemplate restTemplate = new RestTemplate();
        URI uri = UriComponentsBuilder.fromUriString(checkTokenUrl).build().toUri();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Token",accesstoken);
        HttpEntity httpEntity = new HttpEntity<>(null, headers);

        ResponseEntity<Boolean> responseFlag = restTemplate.exchange(uri, HttpMethod.GET, httpEntity, Boolean.class);;

        if (responseFlag.getBody()) {

            RestTemplate restTemplateLibrary = new RestTemplate();
            URI uriLibrary = UriComponentsBuilder.fromUriString(LibraryUrl+id).build().toUri();
            HttpEntity httpEntityLibrary = new HttpEntity<>(null, null);

            restTemplateLibrary.exchange(uriLibrary, HttpMethod.DELETE, httpEntityLibrary, Long.class);
            response.setStatus(HttpStatus.NO_CONTENT.value());
            return;
        }
        else
        {
            response.setStatus(401);//HttpStatus.UNAUTHORIZED.value());
            return;
        }
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value = "/{libraryId}/Book/{bookId}", method = RequestMethod.POST)
    public Long addBookToLibrary(@PathVariable Long libraryId, @PathVariable Long bookId, HttpEntity<LibraryRequest> fullLibraryRequest, HttpServletResponse response) {
        LibraryRequest libraryRequest = fullLibraryRequest.getBody();
        HttpHeaders header = fullLibraryRequest.getHeaders();
        String authHeader = header.get("Authorization").toString();
        String accesstoken = authHeader.substring(authHeader.indexOf('[')+1,authHeader.lastIndexOf(']'));

        RestTemplate restTemplate = new RestTemplate();
        URI uri = UriComponentsBuilder.fromUriString(checkTokenUrl).build().toUri();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Token",accesstoken);
        HttpEntity httpEntity = new HttpEntity<>(null, headers);

        ResponseEntity<Boolean> responseFlag = restTemplate.exchange(uri, HttpMethod.GET, httpEntity, Boolean.class);;

        if (responseFlag.getBody()) {
            RestTemplate restTemplateLibrary = new RestTemplate();
            URI uriLibrary = UriComponentsBuilder.fromUriString(LibraryUrl+libraryId+"/Book/"+bookId).build().toUri();
            HttpEntity httpEntityLibrary = new HttpEntity<>(null, null);

            restTemplateLibrary.exchange(uriLibrary, HttpMethod.POST, httpEntityLibrary, Long.class);
            response.setStatus(HttpStatus.CREATED.value());
            return libraryId;
        }
        else
        {
            response.setStatus(401);//HttpStatus.UNAUTHORIZED.value());
            return libraryId;
        }
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value = "//{libraryId}/Book/{bookId}", method = RequestMethod.DELETE)
    public Long deleteBookFromLibrary(@PathVariable Long libraryId, @PathVariable Long bookId, HttpEntity<LibraryRequest> fullLibraryRequest, HttpServletResponse response) {
        LibraryRequest libraryRequest = fullLibraryRequest.getBody();
        HttpHeaders header = fullLibraryRequest.getHeaders();
        String authHeader = header.get("Authorization").toString();
        String accesstoken = authHeader.substring(authHeader.indexOf('[')+1,authHeader.lastIndexOf(']'));

        RestTemplate restTemplate = new RestTemplate();
        URI uri = UriComponentsBuilder.fromUriString(checkTokenUrl).build().toUri();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Token",accesstoken);
        HttpEntity httpEntity = new HttpEntity<>(null, headers);

        ResponseEntity<Boolean> responseFlag = restTemplate.exchange(uri, HttpMethod.GET, httpEntity, Boolean.class);

        if (responseFlag.getBody()) {

            RestTemplate restTemplateLibrary = new RestTemplate();
            URI uriLibrary = UriComponentsBuilder.fromUriString(LibraryUrl+libraryId+"/Book/"+bookId).build().toUri();
            HttpEntity httpEntityLibrary = new HttpEntity<>(null, null);

            restTemplateLibrary.exchange(uriLibrary, HttpMethod.DELETE, httpEntityLibrary, Long.class);
            response.setStatus(HttpStatus.CREATED.value());
            return libraryId;
        }
        else
        {
            response.setStatus(401);//HttpStatus.UNAUTHORIZED.value());
            return libraryId;
        }
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value = "/{libraryId}/Visitor/{visitorId}", method = RequestMethod.POST)
    public Long addVisitorToLibrary(@PathVariable Long libraryId, @PathVariable Long visitorId, HttpEntity<LibraryRequest> fullLibraryRequest, HttpServletResponse response) {
        LibraryRequest libraryRequest = fullLibraryRequest.getBody();
        HttpHeaders header = fullLibraryRequest.getHeaders();
        String authHeader = header.get("Authorization").toString();
        String accesstoken = authHeader.substring(authHeader.indexOf('[')+1,authHeader.lastIndexOf(']'));

        RestTemplate restTemplate = new RestTemplate();
        URI uri = UriComponentsBuilder.fromUriString(checkTokenUrl).build().toUri();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Token",accesstoken);
        HttpEntity httpEntity = new HttpEntity<>(null, headers);

        ResponseEntity<Boolean> responseFlag = restTemplate.exchange(uri, HttpMethod.GET, httpEntity, Boolean.class);

        if (responseFlag.getBody()) {
            RestTemplate restTemplateLibrary = new RestTemplate();
            URI uriLibrary = UriComponentsBuilder.fromUriString(LibraryUrl+libraryId+"/Visitor/"+visitorId).build().toUri();
            HttpEntity httpEntityLibrary = new HttpEntity<>(null, null);

            restTemplateLibrary.exchange(uriLibrary, HttpMethod.POST, httpEntityLibrary, Long.class);
            response.setStatus(HttpStatus.CREATED.value());
            return libraryId;
        }
        else
        {
            response.setStatus(401);//HttpStatus.UNAUTHORIZED.value());
            return libraryId;
        }
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value = "/{libraryId}/Visitor/{visitorId}", method = RequestMethod.DELETE)
    public Long deleteVisitorFromLibrary(@PathVariable Long libraryId, @PathVariable Long visitorId, HttpEntity<LibraryRequest> fullLibraryRequest, HttpServletResponse response) {
        LibraryRequest libraryRequest = fullLibraryRequest.getBody();
        HttpHeaders header = fullLibraryRequest.getHeaders();
        String authHeader = header.get("Authorization").toString();
        String accesstoken = authHeader.substring(authHeader.indexOf('[')+1,authHeader.lastIndexOf(']'));

        RestTemplate restTemplate = new RestTemplate();
        URI uri = UriComponentsBuilder.fromUriString(checkTokenUrl).build().toUri();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Token",accesstoken);
        HttpEntity httpEntity = new HttpEntity<>(null, headers);

        ResponseEntity<Boolean> responseFlag = restTemplate.exchange(uri, HttpMethod.GET, httpEntity, Boolean.class);

        if (responseFlag.getBody()) {

            RestTemplate restTemplateLibrary = new RestTemplate();
            URI uriLibrary = UriComponentsBuilder.fromUriString(LibraryUrl+libraryId+"/Visitor/"+visitorId).build().toUri();
            HttpEntity httpEntityLibrary = new HttpEntity<>(null, null);

            restTemplateLibrary.exchange(uriLibrary, HttpMethod.DELETE, httpEntityLibrary, Long.class);
            response.setStatus(HttpStatus.CREATED.value());
            return libraryId;
        }
        else
        {
            response.setStatus(401);//HttpStatus.UNAUTHORIZED.value());
            return libraryId;
        }
    }
}
