package ServerSv.web.model.response;

import java.util.List;

/**
 * Created by grigory on 1/24/17.
 */
public class ListOfAuthorsResponses {

    private List<AuthorResponse> authorResponses;

    public ListOfAuthorsResponses() {
        this.authorResponses = null;
    }

    public ListOfAuthorsResponses(List<AuthorResponse> authorResponses) {
        this.authorResponses = authorResponses;
    }

    public List<AuthorResponse> getAuthorResponses() {
        return authorResponses;
    }

    public ListOfAuthorsResponses setAuthorResponses(List<AuthorResponse> authorResponses) {
        this.authorResponses = authorResponses;
        return this;
    }

    public void addAuthorResponse (AuthorResponse authorResponse) {
        if (authorResponse != null)
            this.authorResponses.add(authorResponse);
    }
}
