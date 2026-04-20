package com.smartcampus.eventsystem.repository;

import com.smartcampus.eventsystem.entity.Event;
import com.smartcampus.eventsystem.entity.Registration;
import com.smartcampus.eventsystem.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RegistrationRepository extends JpaRepository<Registration, Long> {
    List<Registration> findByUser(User user);
    List<Registration> findByEvent(Event event);
    Optional<Registration> findByEventAndUser(Event event, User user);
    long countByEvent(Event event);
}
