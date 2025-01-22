package co.muhu.eventManagement.repository;

import co.muhu.eventManagement.entity.Venue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

public interface VenueRepository extends JpaRepository<Venue,Long> {
    boolean existsById(@NonNull Long id);
}
