package VisitorsService.service;

import VisitorsService.domain.Visitor;
import VisitorsService.repository.VisitorRespository;
import VisitorsService.web.model.Request.VisitorRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

/**
 * Created by grigory on 1/24/17.
 */
@Service
public class VisitorServiceImpl implements VisitorService {
    @Autowired
    private VisitorRespository visitorRespository;

    @Override
    @Transactional(readOnly = true)
    public Visitor getById(Long id) {
        Visitor visitor = visitorRespository.findOne(id);
        if (visitor == null) {
            throw new EntityNotFoundException("Visitor '{" + id + "}' not found");
        }
        return visitor;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Visitor> getAll() {
        return visitorRespository.findAll();
    }

    @Override
    @Transactional
    public Visitor save(VisitorRequest visitorRequest) {
        Visitor visitor = new Visitor(visitorRequest.getLastName(), visitorRequest.getFirstName(),visitorRequest.getMiddleName(),null);
        return visitorRespository.save(visitor);
    }

    @Override
    @Transactional
    public Visitor update(Long id, VisitorRequest visitorRequest) {
        Visitor visitor = visitorRespository.findOne(id);
        if (visitor == null) {
            throw new EntityNotFoundException("Visitor '{" + id + "}' not found");
        }

        visitor.setLastName(visitorRequest.getLastName() != null ? visitorRequest.getLastName() : visitor.getLastName());
        visitor.setFirstName(visitorRequest.getFirstName() != null ? visitorRequest.getFirstName() : visitor.getFirstName());
        visitor.setMiddleName(visitorRequest.getMiddleName() != null ? visitorRequest.getMiddleName() : visitor.getMiddleName());
        return visitorRespository.save(visitor);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        visitorRespository.delete(id);
    }

    @Override
    @Transactional
    public Long addBookToVisitor(Long visitorId, Long bookId) {
        Visitor visitor = visitorRespository.findOne(visitorId);
        if (visitor == null) {
            throw new EntityNotFoundException("Visitor '{" + id + "}' not found");
        }

        visitor.addBook(bookId);
        visitorRespository.save(visitor);
        return visitor.getId();
    }

    @Override
    @Transactional
    public Long deleteBookFromVisitor(Long visitorId, Long bookId) {
        Visitor visitor = visitorRespository.findOne(visitorId);
        if (visitor == null) {
            throw new EntityNotFoundException("Visitor '{" + id + "}' not found");
        }

        visitor.deleteBook(bookId);
        visitorRespository.save(visitor);
        return visitor.getId();
    }
}
