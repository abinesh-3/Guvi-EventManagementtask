package com.eventsphere.service;

import com.eventsphere.model.Event;
import com.eventsphere.repository.EventRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EventServiceTest {
    @Mock
    private EventRepository events;
    @InjectMocks
    private EventService eventService;

    private Event event;

    @BeforeEach
    void setUp() {
        event = Event.builder()
                .id(1L)
                .name("Tech Conference")
                .category("Technology")
                .dateTime(LocalDateTime.now().plusDays(1))
                .venue("Main Hall")
                .description("A tech event")
                .capacity(100)
                .build();
    }

    @Test
    void testListAll() {
        when(events.findAll()).thenReturn(Arrays.asList(event));
        List<Event> result = eventService.listAll();
        assertEquals(1, result.size());
        assertEquals(event, result.get(0));
        verify(events).findAll();
    }

    @Test
    void testSearch() {
        when(events.findByNameContainingIgnoreCase("Tech")).thenReturn(Arrays.asList(event));
        List<Event> result = eventService.search("Tech");
        assertEquals(1, result.size());
        assertEquals(event, result.get(0));
        verify(events).findByNameContainingIgnoreCase("Tech");
    }

    @Test
    void testByDate() {
        LocalDate date = event.getDateTime().toLocalDate();
        LocalDateTime start = date.atStartOfDay();
        LocalDateTime end = LocalDateTime.of(date, LocalTime.MAX);
        when(events.findByDateTimeBetween(start, end)).thenReturn(Arrays.asList(event));
        List<Event> result = eventService.byDate(date);
        assertEquals(1, result.size());
        assertEquals(event, result.get(0));
        verify(events).findByDateTimeBetween(start, end);
    }

    @Test
    void testSave() {
        when(events.save(event)).thenReturn(event);
        Event saved = eventService.save(event);
        assertEquals(event, saved);
        verify(events).save(event);
    }

    @Test
    void testGet() {
        when(events.findById(1L)).thenReturn(Optional.of(event));
        Event found = eventService.get(1L);
        assertEquals(event, found);
        verify(events).findById(1L);
    }

    @Test
    void testGet_NotFound() {
        when(events.findById(2L)).thenReturn(Optional.empty());
        assertThrows(Exception.class, () -> eventService.get(2L));
        verify(events).findById(2L);
    }

    @Test
    void testDelete() {
        doNothing().when(events).deleteById(1L);
        eventService.delete(1L);
        verify(events).deleteById(1L);
    }
}
