package com.eventsphere.service;

import com.eventsphere.model.Event;
import com.eventsphere.repository.EventRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EventService {
    private final EventRepository events;

    public List<Event> listAll(){ return events.findAll(); }

    public List<Event> search(String q){
        return events.findByNameContainingIgnoreCase(q);
    }

    public List<Event> byDate(LocalDate date){
        return events.findByDateTimeBetween(date.atStartOfDay(), LocalDateTime.of(date, LocalTime.MAX));
    }

    @Transactional
    public Event save(Event e){ return events.save(e); }

    public Event get(Long id){ return events.findById(id).orElseThrow(); }

    public void delete(Long id){ events.deleteById(id); }
}
