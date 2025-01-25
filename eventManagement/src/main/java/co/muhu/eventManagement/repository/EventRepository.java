package co.muhu.eventManagement.repository;

import co.muhu.eventManagement.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;

import java.util.Optional;

public interface EventRepository extends JpaRepository<Event,Long> {
    boolean existsById(@NonNull Long id);

    @Override
    @Query("SELECT e FROM event e " +
            "LEFT JOIN FETCH e.participantSet " +
            "LEFT JOIN FETCH e.memberset " +
            "LEFT JOIN FETCH e.ticketSet " +
            "LEFT JOIN FETCH e.feedBackSet " +
            "LEFT JOIN FETCH e.organizer " +
            "LEFT JOIN FETCH e.venue " +
            "WHERE e.id = :id")
    Optional<Event> findById(@NonNull @Param("id") Long id);
}
