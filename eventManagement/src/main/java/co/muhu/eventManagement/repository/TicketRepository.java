package co.muhu.eventManagement.repository;

import co.muhu.eventManagement.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket,Long> {
    boolean existsById(@NonNull Long id);

    @Query("SELECT t FROM ticket t LEFT JOIN t.event e WHERE e.id= :eventId")
    List<Ticket> findAllByEventId(@Param("eventId") Long eventId);

    @Query("SELECT t FROM ticket t LEFT JOIN t.participant p WHERE p.id= :participantId")
    List<Ticket> findAllByParticipantId(@Param("participantId") Long participantId);
}
