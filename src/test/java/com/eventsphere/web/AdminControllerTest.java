package com.eventsphere.web;

import com.eventsphere.model.Event;
import com.eventsphere.service.EventService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AdminControllerTest {
    @Mock
    private EventService eventService;
    @Mock
    private Model model;
    @Mock
    private BindingResult bindingResult;
    @InjectMocks
    private AdminController adminController;

    private Event event;

    @BeforeEach
    void setUp() {
        event = Event.builder().id(1L).name("Test Event").build();
    }

    @Test
    void testDashboard() {
        when(eventService.listAll()).thenReturn(Arrays.asList(event));
        String view = adminController.dashboard(model);
        verify(model).addAttribute(eq("events"), any());
        assertEquals("admin/dashboard", view);
    }

    @Test
    void testNewEvent() {
        String view = adminController.newEvent(model);
        verify(model).addAttribute(eq("event"), any(Event.class));
        assertEquals("admin/event-form", view);
    }

    @Test
    void testCreate_WithErrors() {
        when(bindingResult.hasErrors()).thenReturn(true);
        String view = adminController.create(event, bindingResult);
        assertEquals("admin/event-form", view);
        verify(eventService, never()).save(any(Event.class));
    }

    @Test
    void testCreate_Success() {
        when(bindingResult.hasErrors()).thenReturn(false);
        String view = adminController.create(event, bindingResult);
        assertEquals("redirect:/admin", view);
        verify(eventService).save(event);
    }

    @Test
    void testEdit() {
        when(eventService.get(1L)).thenReturn(event);
        String view = adminController.edit(1L, model);
        verify(model).addAttribute("event", event);
        assertEquals("admin/event-form", view);
    }

    @Test
    void testDelete() {
        String view = adminController.delete(1L);
        verify(eventService).delete(1L);
        assertEquals("redirect:/admin", view);
    }
}
