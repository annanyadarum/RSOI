package ServerSv.web.model.response;

import java.util.List;

/**
 * Created by grigory on 1/24/17.
 */
public class ListOfBooksResponsesSimple {

    private List<BookResponseSimple> bookResponseSimples;

    public ListOfBooksResponsesSimple() {
        this.bookResponseSimples = null;
    }

    public ListOfBooksResponsesSimple(List<BookResponseSimple> bookResponseSimples) {
        this.bookResponseSimples = bookResponseSimples;
    }

    public List<BookResponseSimple> getBookResponseSimples() {
        return bookResponseSimples;
    }

    public ListOfBooksResponsesSimple setBookResponseSimples(List<BookResponseSimple> bookResponseSimples) {
        this.bookResponseSimples = bookResponseSimples;
        return this;
    }
}
