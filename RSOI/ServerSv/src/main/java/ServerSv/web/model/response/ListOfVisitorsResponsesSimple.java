package ServerSv.web.model.response;

import java.util.List;

/**
 * Created by grigory on 1/24/17.
 */
public class ListOfVisitorsResponsesSimple {
    private List<VisitorResponseSimple> visitorResponseSimples;

    public ListOfVisitorsResponsesSimple() {
        this.visitorResponseSimples = null;
    }

    public ListOfVisitorsResponsesSimple(List<VisitorResponseSimple> visitorResponseSimples) {
        this.visitorResponseSimples = visitorResponseSimples;
    }

    public List<VisitorResponseSimple> getVisitorResponseSimples() {
        return visitorResponseSimples;
    }

    public ListOfVisitorsResponsesSimple setVisitorResponseSimples(List<VisitorResponseSimple> visitorResponseSimples) {
        this.visitorResponseSimples = visitorResponseSimples;
        return this;
    }
}
