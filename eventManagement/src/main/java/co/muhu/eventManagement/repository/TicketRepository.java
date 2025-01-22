package co.muhu.eventManagement.repository;

import co.muhu.eventManagement.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket,Long> {
    boolean existsById(@NonNull Long id);

    List<Ticket> findAllByEventId(Long eventId);
    List<Ticket> findAllByParticipantId(Long participantId);
}
