package com.smartcampus.eventsystem.config;

import com.smartcampus.eventsystem.entity.Event;
import com.smartcampus.eventsystem.entity.User;
import com.smartcampus.eventsystem.repository.EventRepository;
import com.smartcampus.eventsystem.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DatabaseSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final PasswordEncoder passwordEncoder;

    public DatabaseSeeder(UserRepository userRepository, EventRepository eventRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.eventRepository = eventRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.count() == 0) {
            User admin = new User("admin", passwordEncoder.encode("admin123"), "ROLE_ADMIN");
            User student = new User("student1", passwordEncoder.encode("student123"), "ROLE_STUDENT");
            userRepository.save(admin);
            userRepository.save(student);
            System.out.println("Default users seeded: admin / admin123  &  student1 / student123");
        }

        if (eventRepository.count() < 6) {
            Event e1 = new Event();
            e1.setTitle("Spring Boot Workshop");
            e1.setDescription("Learn the basics of Spring Boot and building REST APIs over an intensive weekend workshop.");
            e1.setEventDate(LocalDate.now().plusDays(5));
            e1.setDepartment("Computer Science");
            e1.setEventType("Workshop");
            e1.setMaxAttendees(50);

            Event e2 = new Event();
            e2.setTitle("Annual Cultural Fest");
            e2.setDescription("The biggest cultural event of the year featuring dance, music, and dramatic performances.");
            e2.setEventDate(LocalDate.now().plusDays(15));
            e2.setDepartment("Arts & Culture");
            e2.setEventType("Cultural");
            e2.setMaxAttendees(500);

            Event e3 = new Event();
            e3.setTitle("AI & Future Technologies Seminar");
            e3.setDescription("Guest lecture by industry experts on the future of AI and implications in real world.");
            e3.setEventDate(LocalDate.now().plusDays(20));
            e3.setDepartment("Information Technology");
            e3.setEventType("Seminar");
            e3.setMaxAttendees(100);

            Event e4 = new Event();
            e4.setTitle("Hackathon 2026");
            e4.setDescription("A 48-hour coding marathon to build innovative solutions for campus life.");
            e4.setEventDate(LocalDate.now().plusDays(30));
            e4.setDepartment("Computer Science");
            e4.setEventType("Hackathon");
            e4.setMaxAttendees(200);

            Event e5 = new Event();
            e5.setTitle("Alumni Meetup & Networking");
            e5.setDescription("Connect with distinguished alumni from various fields and grow your network.");
            e5.setEventDate(LocalDate.now().plusDays(40));
            e5.setDepartment("Career Center");
            e5.setEventType("Networking");
            e5.setMaxAttendees(150);

            Event e6 = new Event();
            e6.setTitle("Mental Health Awareness Camp");
            e6.setDescription("Interactive sessions on managing stress, anxiety, and improving overall wellbeing.");
            e6.setEventDate(LocalDate.now().plusDays(7));
            e6.setDepartment("Health & Wellness");
            e6.setEventType("Seminar");
            e6.setMaxAttendees(500);

            eventRepository.save(e1);
            eventRepository.save(e2);
            eventRepository.save(e3);
            eventRepository.save(e4);
            eventRepository.save(e5);
            eventRepository.save(e6);
            System.out.println("Enhanced Sample events seeded into the database.");
        }
    }
}
