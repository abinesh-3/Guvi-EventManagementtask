package com.eventsphere;

import com.eventsphere.model.Event;
import com.eventsphere.service.EventService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class EventSphereApplicationTests {

    @Autowired
    EventService events;

    @Test
    void saveAndFetchEvent(){
        Event e = Event.builder()
                .name("TestConf")
                .venue("Hall A")
                .dateTime(LocalDateTime.now().plusDays(1))
                .capacity(100)
                .description("Testing 123")
                .build();
        Event saved = events.save(e);
        assertThat(events.get(saved.getId()).getName()).isEqualTo("TestConf");
    }
}
