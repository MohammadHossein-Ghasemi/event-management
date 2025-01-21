package co.muhu.eventManagement.repository;

import co.muhu.eventManagement.entity.Venue;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VenueRepository extends JpaRepository<Venue,Long> {
}
