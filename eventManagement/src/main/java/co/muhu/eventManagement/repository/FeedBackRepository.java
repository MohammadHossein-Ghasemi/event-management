package co.muhu.eventManagement.repository;

import co.muhu.eventManagement.entity.FeedBack;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

import java.util.List;

public interface FeedBackRepository extends JpaRepository<FeedBack,Long> {
    boolean existsById(@NonNull Long id);
    List<FeedBack> findAllByEventId(Long eventID);
    List<FeedBack> findAllByParticipantId(Long participantId);
}
