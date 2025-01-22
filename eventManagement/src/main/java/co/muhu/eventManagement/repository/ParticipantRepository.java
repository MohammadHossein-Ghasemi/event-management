package co.muhu.eventManagement.repository;

import co.muhu.eventManagement.entity.Event;
import co.muhu.eventManagement.entity.Participant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

import java.util.List;

public interface ParticipantRepository extends JpaRepository<Participant,Long> {
    boolean existsById(@NonNull Long id);
    List<Participant> findAllByEventSetId(Long eventId);
}
