package co.muhu.eventManagement.repository;

import co.muhu.eventManagement.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event,Long> {
}
