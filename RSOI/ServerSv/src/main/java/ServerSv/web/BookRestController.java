package ServerSv.web;

import ServerSv.web.model.request.BookRequest;
import ServerSv.web.model.response.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import java.net.URI;

@RestController
@RequestMapping("/book")
public class BookRestController {
    private final String BookUrl = "http://localhost:8085/book/";
    private final String AuthorUrl = "http://localhost:8086/author/";
    private final String checkTokenUrl = "http://localhost:8086/user/checkToken";

    @RequestMapping(value="/{id}", method = RequestMethod.GET)
    public BookResponse getBuffet(@PathVariable Long id) {
        RestTemplate restTemplate = new RestTemplate();
        String address = BookUrl + Long.toString(id);
        URI uri = UriComponentsBuilder.fromUriString(address).build().toUri();
        BookResponseSimple bookResponseSimple = restTemplate.getForObject(uri, BookResponseSimple.class);
        BookResponse bookResponse = new BookResponse(bookResponseSimple);

        for (Long authorId: bookResponseSimple.getAuthors()) {
            String addressAuthor = AuthorUrl + Long.toString(authorId);
            URI uriAuthor = UriComponentsBuilder.fromUriString(addressAuthor).build().toUri();
            AuthorResponse authorResponse = restTemplate.getForObject(uriAuthor, AuthorResponse.class);
            bookResponse.addAuthorToBook(authorResponse);
        }
        return bookResponse;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ListOfBooksResponses getAll() {
        RestTemplate restTemplate = new RestTemplate();
        URI uri = UriComponentsBuilder.fromUriString(BookUrl).build().toUri();
        ResponseEntity<ListOfBooksResponsesSimple> response = restTemplate.getForEntity(uri, ListOfBooksResponsesSimple.class);
        ListOfBooksResponsesSimple listOfBooksResponsesSimple = response.getBody();
        ListOfBooksResponses listOfBooksResponses = new ListOfBooksResponses();

        for (BookResponseSimple bookResponseSimple : listOfBooksResponsesSimple.getBookResponseSimples()) {
            BookResponse bookResponse = new BookResponse(bookResponseSimple);
            for (Long authorId: bookResponseSimple.getAuthors()) {
                String addressAuthor = AuthorUrl + Long.toString(authorId);
                URI uriAuthor = UriComponentsBuilder.fromUriString(addressAuthor).build().toUri();
                AuthorResponse authorResponse = restTemplate.getForObject(uriAuthor, AuthorResponse.class);
                bookResponse.addAuthorToBook(authorResponse);
            }
            listOfBooksResponses.addBuffetResponse(bookResponse);
        }

        return listOfBooksResponses;
    }

    @RequestMapping(value = "/{startIndex}/{finishIndex}", method = RequestMethod.GET)
    public ListOfBooksResponses getAllPagination(@PathVariable Integer startIndex, @PathVariable Integer finishIndex) {
        RestTemplate restTemplate = new RestTemplate();
        URI uri = UriComponentsBuilder.fromUriString(BookUrl + startIndex + "/" + finishIndex).build().toUri();
        ResponseEntity<ListOfBooksResponsesSimple> response = restTemplate.getForEntity(uri, ListOfBooksResponsesSimple.class);
        ListOfBooksResponsesSimple listOfBooksResponsesSimple = response.getBody();
        ListOfBooksResponses listOfBooksResponses = new ListOfBooksResponses();

        for (BookResponseSimple bookResponseSimple : listOfBooksResponsesSimple.getBookResponseSimples()) {
            BookResponse bookResponse = new BookResponse(bookResponseSimple);
            for (Long authorId: bookResponseSimple.getAuthors()) {
                String addressAuthor = AuthorUrl + Long.toString(authorId);
                URI uriAuthor = UriComponentsBuilder.fromUriString(addressAuthor).build().toUri();
                AuthorResponse authorResponse = restTemplate.getForObject(uriAuthor, AuthorResponse.class);
                bookResponse.addAuthorToBook(authorResponse);
            }
            listOfBooksResponses.addBuffetResponse(bookResponse);
        }

        return listOfBooksResponses;
    }

    @RequestMapping(method = RequestMethod.POST)
    public Long createBook(HttpEntity<BookRequest> fullBookRequest, HttpServletResponse response) {
        BookRequest bookRequest = fullBookRequest.getBody();
        HttpHeaders header = fullBookRequest.getHeaders();
        String authHeader = header.get("Authorization").toString();
        String accesstoken = authHeader.substring(authHeader.indexOf('[')+1,authHeader.lastIndexOf(']'));

        RestTemplate restTemplate = new RestTemplate();
        URI uri = UriComponentsBuilder.fromUriString(checkTokenUrl).build().toUri();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Token",accesstoken);
        HttpEntity httpEntity = new HttpEntity<>(null, headers);

        ResponseEntity<Boolean> responseFlag = restTemplate.exchange(uri, HttpMethod.GET, httpEntity, Boolean.class);

        if (responseFlag.getBody()) {
            RestTemplate restTemplateBook = new RestTemplate();
            URI uriBook = UriComponentsBuilder.fromUriString(BookUrl).build().toUri();
            HttpEntity<BookRequest> httpEntityBook = new HttpEntity<>(bookRequest, null);

            ResponseEntity<Long> responseBook = restTemplateBook.exchange(uriBook, HttpMethod.POST, httpEntityBook, Long.class);
            response.setStatus(HttpStatus.CREATED.value());
            return responseBook.getBody();
        }
        else
        {
            response.setStatus(401);//HttpStatus.UNAUTHORIZED.value());
            return 0L;
        }

    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PATCH)
    public Long updateBook(@PathVariable Long id, HttpEntity<BookRequest> fullBookRequest,HttpServletResponse response) {
        BookRequest bookRequest = fullBookRequest.getBody();
        HttpHeaders header = fullBookRequest.getHeaders();
        String authHeader = header.get("Authorization").toString();
        String accesstoken = authHeader.substring(authHeader.indexOf('[')+1,authHeader.lastIndexOf(']'));

        RestTemplate restTemplate = new RestTemplate();
        URI uri = UriComponentsBuilder.fromUriString(checkTokenUrl).build().toUri();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Token",accesstoken);
        HttpEntity httpEntity = new HttpEntity<>(null, headers);

        ResponseEntity<Boolean> responseFlag = restTemplate.exchange(uri, HttpMethod.GET, httpEntity, Boolean.class);

        if (responseFlag.getBody()) {

            RestTemplate restTemplateBook = new RestTemplate();
            URI uriBook = UriComponentsBuilder.fromUriString(BookUrl).build().toUri();
            HttpEntity<BookRequest> httpEntityBook = new HttpEntity<>(bookRequest, null);

            ResponseEntity<Long> responseBook = restTemplateBook.exchange(uriBook, HttpMethod.PUT, httpEntityBook, Long.class);
            response.setStatus(HttpStatus.CREATED.value());
            return responseBook.getBody();
        }
        else
        {
            response.setStatus(401);//HttpStatus.UNAUTHORIZED.value());
            return 0L;
        }
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteBook(@PathVariable Long id, HttpEntity<BookRequest> fullBookRequest, HttpServletResponse response) {
        BookRequest bookRequest = fullBookRequest.getBody();
        HttpHeaders header = fullBookRequest.getHeaders();
        String authHeader = header.get("Authorization").toString();
        String accesstoken = authHeader.substring(authHeader.indexOf('[')+1,authHeader.lastIndexOf(']'));

        RestTemplate restTemplate = new RestTemplate();
        URI uri = UriComponentsBuilder.fromUriString(checkTokenUrl).build().toUri();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Token",accesstoken);
        HttpEntity httpEntity = new HttpEntity<>(null, headers);

        ResponseEntity<Boolean> responseFlag = restTemplate.exchange(uri, HttpMethod.GET, httpEntity, Boolean.class);

        if (responseFlag.getBody()) {

            RestTemplate restTemplateBook = new RestTemplate();
            URI uriBook = UriComponentsBuilder.fromUriString(BookUrl+id).build().toUri();
            HttpEntity httpEntityBook = new HttpEntity<>(null, null);

            restTemplateBook.exchange(uriBook, HttpMethod.DELETE, httpEntityBook, Long.class);
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
    @RequestMapping(value = "/{bookId}/author/{authorId}", method = RequestMethod.POST)
    public Long addAuthorToBook(@PathVariable Long bookId, @PathVariable Long authorId, HttpEntity<BookRequest> fullBookRequest, HttpServletResponse response) {
        BookRequest bookRequest = fullBookRequest.getBody();
        HttpHeaders header = fullBookRequest.getHeaders();
        String authHeader = header.get("Authorization").toString();
        String accesstoken = authHeader.substring(authHeader.indexOf('[')+1,authHeader.lastIndexOf(']'));

        RestTemplate restTemplate = new RestTemplate();
        URI uri = UriComponentsBuilder.fromUriString(checkTokenUrl).build().toUri();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Token",accesstoken);
        HttpEntity httpEntity = new HttpEntity<>(null, headers);

        ResponseEntity<Boolean> responseFlag = restTemplate.exchange(uri, HttpMethod.GET, httpEntity, Boolean.class);

        if (responseFlag.getBody()) {
            RestTemplate restTemplateBook = new RestTemplate();
            URI uriBook = UriComponentsBuilder.fromUriString(BookUrl+bookId+"/author/"+authorId).build().toUri();
            HttpEntity httpEntityBook = new HttpEntity<>(null, null);

            restTemplateBook.exchange(uriBook, HttpMethod.POST, httpEntityBook, Long.class);
            response.setStatus(HttpStatus.CREATED.value());
            return bookId;
        }
        else
        {
            response.setStatus(401);//HttpStatus.UNAUTHORIZED.value());
            return bookId;
        }
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value = "/{bookId}/author/{authorId}", method = RequestMethod.DELETE)
    public Long deleteAuthorFromBook(@PathVariable Long bookId, @PathVariable Long authorId, HttpEntity<BookRequest> fullBookRequest, HttpServletResponse response) {
        BookRequest bookRequest = fullBookRequest.getBody();
        HttpHeaders header = fullBookRequest.getHeaders();
        String authHeader = header.get("Authorization").toString();
        String accesstoken = authHeader.substring(authHeader.indexOf('[')+1,authHeader.lastIndexOf(']'));

        RestTemplate restTemplate = new RestTemplate();
        URI uri = UriComponentsBuilder.fromUriString(checkTokenUrl).build().toUri();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Token",accesstoken);
        HttpEntity httpEntity = new HttpEntity<>(null, headers);

        ResponseEntity<Boolean> responseFlag = restTemplate.exchange(uri, HttpMethod.GET, httpEntity, Boolean.class);

        if (responseFlag.getBody()) {

            RestTemplate restTemplateBook = new RestTemplate();
            URI uriBook = UriComponentsBuilder.fromUriString(BookUrl+bookId+"/author/"+authorId).build().toUri();
            HttpEntity httpEntityBook = new HttpEntity<>(null, null);

            restTemplateBook.exchange(uriBook, HttpMethod.DELETE, httpEntityBook, Long.class);
            response.setStatus(HttpStatus.CREATED.value());
            return bookId;
        }
        else
        {
            response.setStatus(401);//HttpStatus.UNAUTHORIZED.value());
            return bookId;
        }
    }
}
