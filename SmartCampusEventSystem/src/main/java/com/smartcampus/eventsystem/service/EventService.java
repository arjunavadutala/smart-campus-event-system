package com.smartcampus.eventsystem.service;

import com.smartcampus.eventsystem.entity.Event;
import com.smartcampus.eventsystem.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    public List<Event> getFilteredEvents(String department, String eventType) {
        if ((department == null || department.isEmpty()) && (eventType == null || eventType.isEmpty())) {
            return eventRepository.findAll();
        }
        return eventRepository.findEventsByFilters(
                department != null && !department.isEmpty() ? department : null,
                eventType != null && !eventType.isEmpty() ? eventType : null
        );
    }

    public Optional<Event> getEventById(Long id) {
        return eventRepository.findById(id);
    }

    public Event saveEvent(Event event) {
        return eventRepository.save(event);
    }

    public void deleteEvent(Long id) {
        eventRepository.deleteById(id);
    }
}
