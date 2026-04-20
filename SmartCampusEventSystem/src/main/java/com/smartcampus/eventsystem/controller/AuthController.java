package com.smartcampus.eventsystem.controller;

import com.smartcampus.eventsystem.entity.User;
import com.smartcampus.eventsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") User user, Model model) {
        try {
            // Defaulting all registered users via this form to ROLE_STUDENT
            user.setRole("ROLE_STUDENT");
            userService.registerUser(user);
            return "redirect:/login?registerSuccess=true";
        } catch (Exception e) {
            model.addAttribute("error", "Registration failed. Username might be taken.");
            return "register";
        }
    }
}
