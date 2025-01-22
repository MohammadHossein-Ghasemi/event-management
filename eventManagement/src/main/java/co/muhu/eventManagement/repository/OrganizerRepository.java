package co.muhu.eventManagement.repository;

import co.muhu.eventManagement.entity.Organizer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;

import java.util.List;

public interface OrganizerRepository extends JpaRepository<Organizer,Long> {
    boolean existsById(@NonNull Long id);

    @Query("SELECT o FROM organizer o LEFT JOIN o.event e WHERE e.id= :eventId")
    List<Organizer> findByEventId(@Param("eventId") Long eventId);
}
