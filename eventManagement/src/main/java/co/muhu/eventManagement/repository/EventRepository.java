package co.muhu.eventManagement.repository;

import co.muhu.eventManagement.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

public interface EventRepository extends JpaRepository<Event,Long> {
    boolean existsById(@NonNull Long id);
}
