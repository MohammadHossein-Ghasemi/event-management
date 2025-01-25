package co.muhu.eventManagement.repository;


import co.muhu.eventManagement.entity.Participant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Optional;

public interface ParticipantRepository extends JpaRepository<Participant,Long> {
    boolean existsById(@NonNull Long id);

    @Query("SELECT p FROM participant p LEFT JOIN p.eventSet e WHERE e.id= :eventId")
    List<Participant> findAllByEventSetId(@Param("eventId") Long eventId);

    @Override
    @Query("SELECT p FROM participant p" +
            " LEFT JOIN FETCH p.eventSet" +
            " LEFT JOIN FETCH p.feedBackSet " +
            "LEFT JOIN FETCH p.ticketSet " +
            "WHERE p.id= :id")
    Optional<Participant> findById(@Param("id")@NonNull Long id);
}
