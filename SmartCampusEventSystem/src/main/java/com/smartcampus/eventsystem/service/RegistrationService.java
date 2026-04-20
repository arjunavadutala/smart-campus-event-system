package com.smartcampus.eventsystem.service;

import com.smartcampus.eventsystem.entity.Event;
import com.smartcampus.eventsystem.entity.Registration;
import com.smartcampus.eventsystem.entity.User;
import com.smartcampus.eventsystem.repository.RegistrationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RegistrationService {

    @Autowired
    private RegistrationRepository registrationRepository;

    public Registration registerForEvent(Event event, User user) {
        Optional<Registration> existing = registrationRepository.findByEventAndUser(event, user);
        if (existing.isPresent()) {
            throw new RuntimeException("User already registered for this event.");
        }

        long currentCount = registrationRepository.countByEvent(event);
        if (currentCount >= event.getMaxAttendees()) {
            throw new RuntimeException("Event is already full.");
        }

        Registration registration = new Registration(event, user);
        return registrationRepository.save(registration);
    }

    public List<Registration> getRegistrationsForUser(User user) {
        return registrationRepository.findByUser(user);
    }

    public Registration addFeedback(Long registrationId, String feedback) {
        Optional<Registration> registrationOpt = registrationRepository.findById(registrationId);
        if (registrationOpt.isPresent()) {
            Registration registration = registrationOpt.get();
            registration.setFeedback(feedback);
            return registrationRepository.save(registration);
        }
        throw new RuntimeException("Registration not found");
    }

    public long getRegistrationCount(Event event) {
        return registrationRepository.countByEvent(event);
    }
}
