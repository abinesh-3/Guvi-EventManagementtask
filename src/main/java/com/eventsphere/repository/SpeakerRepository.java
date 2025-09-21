package com.eventsphere.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.eventsphere.model.Speaker;

public interface SpeakerRepository extends JpaRepository<Speaker, Long> {}
