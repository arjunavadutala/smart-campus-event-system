package com.smartcampus.eventsystem.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

@Entity
@Table(name = "events")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Title is required")
    @Size(min = 5, max = 100, message = "Title must be between 5 and 100 characters")
    @Column(nullable = false)
    private String title;

    @NotNull(message = "Description is required")
    @Size(min = 10, max = 500, message = "Description must be at least 10 characters")
    @Column(nullable = false, length = 500)
    private String description;

    @NotNull(message = "Date is required")
    @Column(nullable = false)
    private LocalDate eventDate;

    @NotNull(message = "Department is required")
    @Column(nullable = false)
    private String department;

    @NotNull(message = "Event type is required")
    @Column(nullable = false)
    private String eventType; // Workshop, Seminar, Cultural

    @NotNull(message = "Max attendees is required")
    @Min(value = 1, message = "Max attendees must be greater than 0")
    @Column(nullable = false)
    private Integer maxAttendees;

    public Event() {
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public LocalDate getEventDate() { return eventDate; }
    public void setEventDate(LocalDate eventDate) { this.eventDate = eventDate; }
    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }
    public String getEventType() { return eventType; }
    public void setEventType(String eventType) { this.eventType = eventType; }
    public Integer getMaxAttendees() { return maxAttendees; }
    public void setMaxAttendees(Integer maxAttendees) { this.maxAttendees = maxAttendees; }
}
