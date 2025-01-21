package co.muhu.eventManagement.repository;

import co.muhu.eventManagement.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket,Long> {
}
