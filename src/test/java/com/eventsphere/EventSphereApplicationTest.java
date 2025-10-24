package com.eventsphere;

import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import static org.junit.jupiter.api.Assertions.*;

public class EventSphereApplicationTest {
    @Test
    void testMainRuns() {
        assertDoesNotThrow(() -> EventSphereApplication.main(new String[]{}));
    }
}
