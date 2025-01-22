package co.muhu.eventManagement.repository;

import co.muhu.eventManagement.entity.Organizer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Optional;

public interface OrganizerRepository extends JpaRepository<Organizer,Long> {
    boolean existsById(@NonNull Long id);
    Optional<Organizer> findByEventId(Long eventId);
}
