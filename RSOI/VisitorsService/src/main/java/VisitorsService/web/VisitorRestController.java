package VisitorsService.web;

import VisitorsService.domain.Visitor;
import VisitorsService.service.VisitorService;
import VisitorsService.web.model.Request.VisitorRequest;
import VisitorsService.web.model.Response.VisitorResponse;
import org.apache.tomcat.jni.Library;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by grigory on 1/24/17.
 */
@RestController
@RequestMapping("/visitor")
public class VisitorRestController {
    @Autowired
    private VisitorService visitorService;

    @RequestMapping(value="/{id}", method = RequestMethod.GET)
    public VisitorResponse getVisitor(@PathVariable Long id) {
        return new VisitorResponse(visitorService.getById(id));
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<VisitorResponse> getAll() {
        return visitorService.getAll()
                .stream()
                .map(VisitorResponse::new)
                .collect(Collectors.toList());
    }

    @RequestMapping(method = RequestMethod.POST)
    public Long createVisitor(VisitorRequest visitorRequest, HttpServletResponse response) {
        Visitor visitor = visitorService.save(visitorRequest);
        response.setStatus(HttpStatus.CREATED.value());
        return visitor.getId();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PATCH)
    public Long updateVisitor(@PathVariable Long id, VisitorRequest visitorRequest, HttpServletResponse response) {
        Visitor visitor = visitorService.update(id, visitorRequest);
        response.setStatus(HttpStatus.CREATED.value());
        return visitor.getId();
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteVisitor(@PathVariable Long id, VisitorRequest visitorRequest, HttpServletResponse response) {
        visitorService.delete(id);
        response.setStatus(HttpStatus.NO_CONTENT.value());
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value = "/{visitorId}/book/{bookId}", method = RequestMethod.POST)
    public void addBookToVisitor(@PathVariable Long visitorId, @PathVariable Long bookId, HttpServletResponse response) {
        Long updatedVisitorId = visitorService.addBookToVisitor(visitorId,bookId);
        response.addHeader(HttpHeaders.LOCATION,"/library/"+updatedVisitorId);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value = "/{visitorId}/Book/{bookId}", method = RequestMethod.DELETE)
    public void deleteBookFromVisitor(@PathVariable Long visitorId, @PathVariable Long bookId, HttpServletResponse response) {
        Long updatedVisitorId = visitorService.deleteBookFromVisitor(visitorId,bookId);
        response.addHeader(HttpHeaders.LOCATION,"/library/"+updatedVisitorId);
    }
}
