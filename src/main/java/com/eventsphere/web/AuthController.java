package com.eventsphere.web;

import com.eventsphere.model.AppUser;
import com.eventsphere.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;

    @GetMapping("/login")
    public String login(){ return "auth/login"; }

    @GetMapping("/register")
    public String registerForm(Model model){
        model.addAttribute("user", new AppUser());
        return "auth/register";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("user") AppUser user, BindingResult br, Model model){
        if(userService.existsUsername(user.getUsername())){
            br.rejectValue("username","exists","Username already taken");
        }
        if(userService.existsEmail(user.getEmail())){
            br.rejectValue("email","exists","Email already registered");
        }
        if(br.hasErrors()) return "auth/register";
        userService.register(user, false);
        model.addAttribute("msg","Registration successful. Please login.");
        return "auth/login";
    }
}
