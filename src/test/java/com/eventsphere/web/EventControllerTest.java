package com.eventsphere.web;

import com.eventsphere.model.Event;
import com.eventsphere.service.EmailService;
import com.eventsphere.service.EventService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.User;
import org.springframework.ui.Model;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EventControllerTest {
    @Mock
    private EventService eventService;
    @Mock
    private EmailService emailService;
    @Mock
    private Model model;
    @InjectMocks
    private EventController eventController;

    private Event event;
    private User user;

    @BeforeEach
    void setUp() {
        event = Event.builder().id(1L).name("Test Event").venue("Main Hall").build();
        user = new User("user@example.com", "password", Arrays.asList());
    }

    @Test
    void testList() {
        when(eventService.listAll()).thenReturn(Arrays.asList(event));
        String view = eventController.list(model);
        verify(model).addAttribute(eq("events"), any());
        assertEquals("events/list", view);
    }

    @Test
    void testDetails() {
        when(eventService.get(1L)).thenReturn(event);
        String view = eventController.details(1L, model);
        verify(model).addAttribute("event", event);
        assertEquals("events/details", view);
    }

    @Test
    void testRegister() {
        when(eventService.get(1L)).thenReturn(event);
        String view = eventController.register(1L, user, model);
        verify(emailService).send(eq(user.getUsername()), contains("Event Registration"), contains("You are registered"));
        verify(model).addAttribute("event", event);
        verify(model).addAttribute("registered", true);
        assertEquals("events/details", view);
    }
}
