package ServerSv.web.model.response;

import java.util.List;

/**
 * Created by grigory on 1/24/17.
 */
public class ListOfLibraryResponseSimple {
    private List<LibraryResponseSimple> libraryResponseSimples;

    public ListOfLibraryResponseSimple() {
        this.libraryResponseSimples = null;
    }

    public ListOfLibraryResponseSimple(List<LibraryResponseSimple> libraryResponseSimples) {
        this.libraryResponseSimples = libraryResponseSimples;
    }

    public List<LibraryResponseSimple> getLibraryResponseSimples() {
        return libraryResponseSimples;
    }

    public ListOfLibraryResponseSimple setLibraryResponseSimples(List<LibraryResponseSimple> libraryResponseSimples) {
        this.libraryResponseSimples = libraryResponseSimples;
        return this;
    }
}
