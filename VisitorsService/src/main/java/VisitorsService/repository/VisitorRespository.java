package VisitorsService.repository;

import VisitorsService.domain.Visitor;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by grigory on 1/24/17.
 */
public interface VisitorRespository extends JpaRepository<Visitor,Long> {
}
