package com.eventsphere.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.eventsphere.model.Registration;

public interface RegistrationRepository extends JpaRepository<Registration, Long> {
    long countByEventId(Long eventId);
}
