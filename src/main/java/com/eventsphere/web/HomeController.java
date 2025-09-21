package com.eventsphere.web;

import com.eventsphere.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class HomeController {
    private final EventService eventService;

    @GetMapping("/")
    public String home(Model model){
        model.addAttribute("events", eventService.listAll());
        return "index";
    }

    @GetMapping("/search")
    public String search(@RequestParam String q, Model model){
        model.addAttribute("events", eventService.search(q));
        model.addAttribute("query", q);
        return "index";
    }
}
