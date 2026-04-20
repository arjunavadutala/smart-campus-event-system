package com.smartcampus.eventsystem.controller;

import com.smartcampus.eventsystem.entity.Event;
import com.smartcampus.eventsystem.service.EventService;
import com.smartcampus.eventsystem.service.RegistrationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private EventService eventService;

    @Autowired
    private RegistrationService registrationService;

    @GetMapping("/dashboard")
    public String dashboard(
            @RequestParam(required = false) String department,
            @RequestParam(required = false) String eventType,
            Model model) {
        List<Event> events = eventService.getFilteredEvents(department, eventType);
        
        // Calculate stats
        Map<Long, Long> eventStats = new HashMap<>();
        for (Event e : events) {
            eventStats.put(e.getId(), registrationService.getRegistrationCount(e));
        }

        model.addAttribute("events", events);
        model.addAttribute("eventStats", eventStats);
        model.addAttribute("selectedDept", department);
        model.addAttribute("selectedType", eventType);
        return "admin/dashboard";
    }

    @GetMapping("/events/new")
    public String newEventForm(Model model) {
        model.addAttribute("event", new Event());
        return "admin/event-form";
    }

    @PostMapping("/events/save")
    public String saveEvent(@Valid @ModelAttribute("event") Event event, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "admin/event-form";
        }
        eventService.saveEvent(event);
        return "redirect:/admin/dashboard";
    }

    @GetMapping("/events/edit/{id}")
    public String editEventForm(@PathVariable Long id, Model model) {
        Event event = eventService.getEventById(id).orElseThrow(() -> new IllegalArgumentException("Invalid event Id:" + id));
        model.addAttribute("event", event);
        return "admin/event-form";
    }

    @GetMapping("/events/delete/{id}")
    public String deleteEvent(@PathVariable Long id) {
        eventService.deleteEvent(id);
        return "redirect:/admin/dashboard";
    }
}
