package com.eventsphere.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Event {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    private String category;

    @NotNull @Future
    private LocalDateTime dateTime;

    @NotBlank
    private String venue;

    @Column(length = 2000)
    private String description;

    @ManyToMany
    private List<Speaker> speakers = new ArrayList<>();

    private int capacity;
}
