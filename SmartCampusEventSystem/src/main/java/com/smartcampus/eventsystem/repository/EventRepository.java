package com.smartcampus.eventsystem.repository;

import com.smartcampus.eventsystem.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {
    @Query("SELECT e FROM Event e WHERE " +
           "(:department IS NULL OR e.department = :department) AND " +
           "(:eventType IS NULL OR e.eventType = :eventType)")
    List<Event> findEventsByFilters(@Param("department") String department, @Param("eventType") String eventType);
}
