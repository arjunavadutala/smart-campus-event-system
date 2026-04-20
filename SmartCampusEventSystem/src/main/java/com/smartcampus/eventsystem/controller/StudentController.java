package com.smartcampus.eventsystem.controller;

import com.smartcampus.eventsystem.entity.Event;
import com.smartcampus.eventsystem.entity.Registration;
import com.smartcampus.eventsystem.entity.User;
import com.smartcampus.eventsystem.service.EventService;
import com.smartcampus.eventsystem.service.RegistrationService;
import com.smartcampus.eventsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private EventService eventService;

    @Autowired
    private RegistrationService registrationService;

    @Autowired
    private UserService userService;

    private User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return userService.findByUsername(auth.getName()).orElse(null);
    }

    @GetMapping("/events")
    public String viewEvents(Model model) {
        model.addAttribute("events", eventService.getAllEvents());
        return "student/dashboard";
    }

    @PostMapping("/events/register/{id}")
    public String registerForEvent(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            Event event = eventService.getEventById(id).orElseThrow(() -> new RuntimeException("Event not found"));
            User currentUser = getCurrentUser();
            registrationService.registerForEvent(event, currentUser);
            redirectAttributes.addFlashAttribute("successMessage", "Successfully registered for " + event.getTitle());
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/student/events";
    }

    @GetMapping("/my-registrations")
    public String myRegistrations(Model model) {
        User currentUser = getCurrentUser();
        List<Registration> registrations = registrationService.getRegistrationsForUser(currentUser);
        model.addAttribute("registrations", registrations);
        return "student/my-registrations";
    }

    @PostMapping("/feedback/{registrationId}")
    public String submitFeedback(@PathVariable Long registrationId, @RequestParam("feedback") String feedback, RedirectAttributes redirectAttributes) {
        try {
            registrationService.addFeedback(registrationId, feedback);
            redirectAttributes.addFlashAttribute("successMessage", "Feedback submitted successfully.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/student/my-registrations";
    }

    @GetMapping("/profile")
    public String viewProfile(Model model) {
        User currentUser = getCurrentUser();
        List<Registration> registrations = registrationService.getRegistrationsForUser(currentUser);
        model.addAttribute("user", currentUser);
        model.addAttribute("registrationCount", registrations.size());
        return "student/profile";
    }
}
