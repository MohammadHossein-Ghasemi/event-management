package co.muhu.eventManagement.repository;

import co.muhu.eventManagement.entity.FeedBack;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;

import java.util.List;

public interface FeedBackRepository extends JpaRepository<FeedBack,Long> {
    boolean existsById(@NonNull Long id);

    @Query("SELECT f FROM feedback f LEFT JOIN f.event e WHERE e.id = :eventId")
    List<FeedBack> findAllByEventId(@Param("eventId") Long eventID);

    @Query("SELECT f FROM feedback f LEFT JOIN f.participant p WHERE p.id = :participantId")
    List<FeedBack> findAllByParticipantId(@Param("participantId")Long participantId);
}
