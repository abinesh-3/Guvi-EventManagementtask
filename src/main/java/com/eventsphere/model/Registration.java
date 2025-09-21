package com.eventsphere.model;

import jakarta.persistence.*;
import lombok.*;

@Entity @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Registration {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private AppUser user;

    @ManyToOne
    private Event event;

    private boolean attended = false;
}
