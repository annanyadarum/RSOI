package ServerSv.web.model.response;

import java.util.List;

/**
 * Created by grigory on 1/24/17.
 */
public class ListOfBooksResponses {
    private List<BookResponse> bookResponses;

    public ListOfBooksResponses() {
        this.bookResponses = null;
    }

    public ListOfBooksResponses(List<BookResponse> bookResponses) {
        this.bookResponses = bookResponses;
    }

    public List<BookResponse> getBookResponses() {
        return bookResponses;
    }

    public ListOfBooksResponses setBookResponses(List<BookResponse> bookResponses) {
        this.bookResponses = bookResponses;
        return this;
    }

    public void addBuffetResponse (BookResponse bookResponse) {
        if (bookResponse != null)
            this.bookResponses.add(bookResponse);
    }
}
