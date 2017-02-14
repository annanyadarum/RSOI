package ServerSv.web.model.response;

import java.util.List;

/**
 * Created by grigory on 1/24/17.
 */
public class ListOfVisitorsResponses {
    private List<VisitorResponse> visitorResponses;

    public ListOfVisitorsResponses() {
        this.visitorResponses = null;
    }

    public ListOfVisitorsResponses(List<VisitorResponse> visitorResponses) {
        this.visitorResponses = visitorResponses;
    }

    public List<VisitorResponse> getVisitorResponses() {
        return visitorResponses;
    }

    public ListOfVisitorsResponses setVisitorResponses(List<VisitorResponse> visitorResponses) {
        this.visitorResponses = visitorResponses;
        return this;
    }

    public void addVisitorResponse (VisitorResponse visitorResponses) {
        if (visitorResponses != null)
            this.visitorResponses.add(visitorResponses);
    }
}
