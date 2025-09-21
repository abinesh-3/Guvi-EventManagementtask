package com.eventsphere.web;

import com.eventsphere.model.Event;
import com.eventsphere.service.EventService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {
    private final EventService eventService;

    @GetMapping
    public String dashboard(Model model){
        model.addAttribute("events", eventService.listAll());
        return "admin/dashboard";
    }

    @GetMapping("/events/new")
    public String newEvent(Model model){
        model.addAttribute("event", new Event());
        return "admin/event-form";
    }

    @PostMapping("/events")
    public String create(@Valid @ModelAttribute Event event, BindingResult br){
        if(br.hasErrors()) return "admin/event-form";
        eventService.save(event);
        return "redirect:/admin";
    }

    @GetMapping("/events/{id}/edit")
    public String edit(@PathVariable Long id, Model model){
        model.addAttribute("event", eventService.get(id));
        return "admin/event-form";
    }

    @PostMapping("/events/{id}/delete")
    public String delete(@PathVariable Long id){
        eventService.delete(id);
        return "redirect:/admin";
    }
}
