package co.muhu.eventManagement.repository;

import co.muhu.eventManagement.entity.Participant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParticipantRepository extends JpaRepository<Participant,Long> {
}
