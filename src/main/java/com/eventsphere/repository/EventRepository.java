package com.eventsphere.repository;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.eventsphere.model.Event;

public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findByCategoryIgnoreCase(String category);
    List<Event> findByDateTimeBetween(LocalDateTime start, LocalDateTime end);
    List<Event> findByNameContainingIgnoreCase(String q);
}
