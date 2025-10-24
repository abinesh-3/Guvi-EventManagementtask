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

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class HomeControllerTest {
    @Mock
    private EventService eventService;
    @Mock
    private Model model;
    @InjectMocks
    private HomeController homeController;

    private Event event;

    @BeforeEach
    void setUp() {
        event = Event.builder().id(1L).name("Test Event").build();
    }

    @Test
    void testHome() {
        when(eventService.listAll()).thenReturn(Arrays.asList(event));
        String view = homeController.home(model);
        verify(model).addAttribute(eq("events"), any());
        assertEquals("index", view);
    }

    @Test
    void testSearch() {
        when(eventService.search("test")).thenReturn(Arrays.asList(event));
        String view = homeController.search("test", model);
        verify(model).addAttribute(eq("events"), any());
        verify(model).addAttribute("query", "test");
        assertEquals("index", view);
    }
}
