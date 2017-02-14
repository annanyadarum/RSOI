package ServerSv.web;

import ServerSv.web.model.request.VisitorRequest;
import ServerSv.web.model.response.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import java.net.URI;

@RestController
@RequestMapping("/visitor")
public class VisitorRestController {
    private final String VisitorUrl = "http://localhost:8084/visitor/";
    private final String BookUrl = "http://localhost:8085/book/";
    private final String AuthorUrl = "http://localhost:8086/author/";
    private final String checkTokenUrl = "http://localhost:8082/user/checkToken";


    @RequestMapping(value="/{id}", method = RequestMethod.GET)
    public VisitorResponse getVisitor(@PathVariable Long id) {
        RestTemplate restTemplate = new RestTemplate();
        String address = VisitorUrl + Long.toString(id);
        URI uri = UriComponentsBuilder.fromUriString(address).build().toUri();
        VisitorResponseSimple visitorResponseSimple = restTemplate.getForObject(uri, VisitorResponseSimple.class);
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

        return visitorResponse;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ListOfVisitorsResponses getAll() {
        RestTemplate restTemplate = new RestTemplate();
        URI uri = UriComponentsBuilder.fromUriString(VisitorUrl).build().toUri();
        ResponseEntity<ListOfVisitorsResponsesSimple> response = restTemplate.getForEntity(uri, ListOfVisitorsResponsesSimple.class);
        ListOfVisitorsResponsesSimple listOfVisitorsResponsesSimple = response.getBody();
        ListOfVisitorsResponses listOfBuffetResponces = new ListOfVisitorsResponses();

        for (VisitorResponseSimple visitorResponseSimple : listOfVisitorsResponsesSimple.getVisitorResponseSimples()) {
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
        }

        return listOfBuffetResponces;
    }

    @RequestMapping(value = "/{startIndex}/{finishIndex}", method = RequestMethod.GET)
    public ListOfVisitorsResponses getAllPagination(@PathVariable Integer startIndex, @PathVariable Integer finishIndex) {
        RestTemplate restTemplate = new RestTemplate();
        URI uri = UriComponentsBuilder.fromUriString(VisitorUrl + startIndex + "/" + finishIndex).build().toUri();
        ResponseEntity<ListOfVisitorsResponsesSimple> response = restTemplate.getForEntity(uri, ListOfVisitorsResponsesSimple.class);
        ListOfVisitorsResponsesSimple listOfVisitorsResponsesSimple = response.getBody();
        ListOfVisitorsResponses listOfBuffetResponces = new ListOfVisitorsResponses();

        for (VisitorResponseSimple visitorResponseSimple : listOfVisitorsResponsesSimple.getVisitorResponseSimples()) {
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
        }

        return listOfBuffetResponces;
    }

    @RequestMapping(method = RequestMethod.POST)
    public Long createVisitor(HttpEntity<VisitorRequest> fullVisitorRequest, HttpServletResponse response) {
        VisitorRequest visitorRequest = fullVisitorRequest.getBody();
        HttpHeaders header = fullVisitorRequest.getHeaders();
        String authHeader = header.get("Authorization").toString();
        String accesstoken = authHeader.substring(authHeader.indexOf('[')+1,authHeader.lastIndexOf(']'));

        RestTemplate restTemplate = new RestTemplate();
        URI uri = UriComponentsBuilder.fromUriString(checkTokenUrl).build().toUri();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Token",accesstoken);
        HttpEntity httpEntity = new HttpEntity<>(null, headers);

        ResponseEntity<Boolean> responseFlag = restTemplate.exchange(uri, HttpMethod.GET, httpEntity, Boolean.class);

        if (responseFlag.getBody()) {
            RestTemplate restTemplateVisitor = new RestTemplate();
            URI uriVisitor = UriComponentsBuilder.fromUriString(VisitorUrl).build().toUri();
            HttpEntity<VisitorRequest> httpEntityVisitor = new HttpEntity<>(visitorRequest, null);

            ResponseEntity<Long> responseVisitor = restTemplateVisitor.exchange(uriVisitor, HttpMethod.POST, httpEntityVisitor, Long.class);
            response.setStatus(HttpStatus.CREATED.value());
            return responseVisitor.getBody();
        }
        else
        {
            response.setStatus(401);//HttpStatus.UNAUTHORIZED.value());
            return 0L;
        }

    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PATCH)
    public Long updateVisitor(@PathVariable Long id, HttpEntity<VisitorRequest> fullVisitorRequest, HttpServletResponse response) {
        VisitorRequest visitorRequest = fullVisitorRequest.getBody();
        HttpHeaders header = fullVisitorRequest.getHeaders();
        String authHeader = header.get("Authorization").toString();
        String accesstoken = authHeader.substring(authHeader.indexOf('[')+1,authHeader.lastIndexOf(']'));

        RestTemplate restTemplate = new RestTemplate();
        URI uri = UriComponentsBuilder.fromUriString(checkTokenUrl).build().toUri();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Token",accesstoken);
        HttpEntity httpEntity = new HttpEntity<>(null, headers);

        ResponseEntity<Boolean> responseFlag = restTemplate.exchange(uri, HttpMethod.GET, httpEntity, Boolean.class);

        if (responseFlag.getBody()) {

            RestTemplate restTemplateVisitor = new RestTemplate();
            URI uriVisitor = UriComponentsBuilder.fromUriString(VisitorUrl).build().toUri();
            HttpEntity<VisitorRequest> httpEntityVisitor = new HttpEntity<>(visitorRequest, null);

            ResponseEntity<Long> responseVisitor = restTemplateVisitor.exchange(uriVisitor, HttpMethod.PUT, httpEntityVisitor, Long.class);
            response.setStatus(HttpStatus.CREATED.value());
            return responseVisitor.getBody();
        }
        else
        {
            response.setStatus(401);//HttpStatus.UNAUTHORIZED.value());
            return 0L;
        }
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteVisitor(@PathVariable Long id, HttpEntity<VisitorRequest> fullVisitorRequest, HttpServletResponse response) {
        VisitorRequest visitorRequest = fullVisitorRequest.getBody();
        HttpHeaders header = fullVisitorRequest.getHeaders();
        String authHeader = header.get("Authorization").toString();
        String accesstoken = authHeader.substring(authHeader.indexOf('[')+1,authHeader.lastIndexOf(']'));

        RestTemplate restTemplate = new RestTemplate();
        URI uri = UriComponentsBuilder.fromUriString(checkTokenUrl).build().toUri();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Token",accesstoken);
        HttpEntity httpEntity = new HttpEntity<>(null, headers);

        ResponseEntity<Boolean> responseFlag = restTemplate.exchange(uri, HttpMethod.GET, httpEntity, Boolean.class);

        if (responseFlag.getBody()) {

            RestTemplate restTemplateVisitor = new RestTemplate();
            URI uriVisitor = UriComponentsBuilder.fromUriString(VisitorUrl+id).build().toUri();
            HttpEntity httpEntityVisitor = new HttpEntity<>(null, null);

            restTemplateVisitor.exchange(uriVisitor, HttpMethod.DELETE, httpEntityVisitor, Long.class);
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
    @RequestMapping(value = "/{visitorId}/Book/{bookId}", method = RequestMethod.POST)
    public Long addBookToVisitor(@PathVariable Long visitorId, @PathVariable Long bookId, HttpEntity<VisitorRequest> fullVisitorRequest, HttpServletResponse response) {
        VisitorRequest visitorRequest = fullVisitorRequest.getBody();
        HttpHeaders header = fullVisitorRequest.getHeaders();
        String authHeader = header.get("Authorization").toString();
        String accesstoken = authHeader.substring(authHeader.indexOf('[')+1,authHeader.lastIndexOf(']'));

        RestTemplate restTemplate = new RestTemplate();
        URI uri = UriComponentsBuilder.fromUriString(checkTokenUrl).build().toUri();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Token",accesstoken);
        HttpEntity httpEntity = new HttpEntity<>(null, headers);

        ResponseEntity<Boolean> responseFlag = restTemplate.exchange(uri, HttpMethod.GET, httpEntity, Boolean.class);

        if (responseFlag.getBody()) {
            RestTemplate restTemplateVisitor = new RestTemplate();
            URI uriVisitor = UriComponentsBuilder.fromUriString(VisitorUrl+visitorId+"/Book/"+bookId).build().toUri();
            HttpEntity httpEntityVisitor = new HttpEntity<>(null, null);

            restTemplateVisitor.exchange(uriVisitor, HttpMethod.POST, httpEntityVisitor, Long.class);
            response.setStatus(HttpStatus.CREATED.value());
            return visitorId;
        }
        else
        {
            response.setStatus(401);//HttpStatus.UNAUTHORIZED.value());
            return visitorId;
        }
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value = "/{visitorId}/Book/{bookId}", method = RequestMethod.DELETE)
    public Long deleteBookFromVisitor(@PathVariable Long visitorId, @PathVariable Long bookId, HttpEntity<VisitorRequest> fullVisitorRequest, HttpServletResponse response) {
        VisitorRequest visitorRequest = fullVisitorRequest.getBody();
        HttpHeaders header = fullVisitorRequest.getHeaders();
        String authHeader = header.get("Authorization").toString();
        String accesstoken = authHeader.substring(authHeader.indexOf('[')+1,authHeader.lastIndexOf(']'));

        RestTemplate restTemplate = new RestTemplate();
        URI uri = UriComponentsBuilder.fromUriString(checkTokenUrl).build().toUri();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Token",accesstoken);
        HttpEntity httpEntity = new HttpEntity<>(null, headers);

        ResponseEntity<Boolean> responseFlag = restTemplate.exchange(uri, HttpMethod.GET, httpEntity, Boolean.class);

        if (responseFlag.getBody()) {

            RestTemplate restTemplateVisitor = new RestTemplate();
            URI uriVisitor = UriComponentsBuilder.fromUriString(VisitorUrl+visitorId+"/Book/"+bookId).build().toUri();
            HttpEntity httpEntityVisitor = new HttpEntity<>(null, null);

            restTemplateVisitor.exchange(uriVisitor, HttpMethod.DELETE, httpEntityVisitor, Long.class);
            response.setStatus(HttpStatus.CREATED.value());
            return visitorId;
        }
        else
        {
            response.setStatus(401);//HttpStatus.UNAUTHORIZED.value());
            return visitorId;
        }
    }
}
