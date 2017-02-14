package ServerSv.web.model.response;

import java.util.List;

/**
 * Created by grigory on 1/24/17.
 */
public class ListOfLibraryResponses {
    private List<LibraryResponse> libraryResponses;

    public ListOfLibraryResponses() {
        this.libraryResponses = null;
    }

    public ListOfLibraryResponses(List<LibraryResponse> libraryResponses) {
        this.libraryResponses = libraryResponses;
    }

    public List<LibraryResponse> getLibraryResponses() {
        return libraryResponses;
    }

    public ListOfLibraryResponses setLibraryResponses(List<LibraryResponse> libraryResponses) {
        this.libraryResponses = libraryResponses;
        return this;
    }

    public void addLibraryResponse (LibraryResponse libraryResponse) {
        if (libraryResponse != null)
            this.libraryResponses.add(libraryResponse);
    }
}
