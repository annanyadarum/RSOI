package VisitorsService.service;

import VisitorsService.domain.Visitor;
import VisitorsService.web.model.Request.VisitorRequest;

import java.util.List;

/**
 * Created by grigory on 1/24/17.
 */
public interface VisitorService {

    Visitor getById(Long id);

    List<Visitor> getAll();

    Visitor save(VisitorRequest visitorRequest);

    Visitor update(Long id, VisitorRequest visitorRequest);

    void delete(Long id);

    Long addBookToVisitor(Long visitorId, Long bookId);

    Long deleteBookFromVisitor(Long visitorId, Long bookId);
}
