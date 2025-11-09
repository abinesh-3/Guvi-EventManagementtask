package com.eventsphere.web;

import com.eventsphere.model.Event;
import com.eventsphere.service.EmailService;
import com.eventsphere.service.EventService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/events")
public class EventController {
    private final EventService eventService;
    private final EmailService email;

    @GetMapping
    public String list(Model model){
        model.addAttribute("events", eventService.listAll());
        return "events/list";
    }

    @GetMapping("/{id}")
    public String details(@PathVariable Long id, Model model){
        model.addAttribute("event", eventService.get(id));
        return "events/details";
    }

    // @PostMapping("/{id}/register")
    // public String register(@PathVariable Long id, @AuthenticationPrincipal User user, Model model){
    //     Event event = eventService.get(id);
    //     email.send(user.getUsername(), "Event Registration - " + event.getName(),
    //             "You are registered for " + event.getName() + " at " + event.getVenue());
    //     model.addAttribute("event", event);
    //     model.addAttribute("registered", true);
    //     return "events/details";
    // }
}
