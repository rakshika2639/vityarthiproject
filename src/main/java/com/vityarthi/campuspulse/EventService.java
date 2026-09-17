package com.vityarthi.campuspulse;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class EventService {
    private final EventRepository repository;
    private final List<Event> events;
    private final List<Registration> registrations;

    public EventService(EventRepository repository) throws IOException {
        this.repository = repository;
        events = new ArrayList<>(repository.loadEvents());
        registrations = new ArrayList<>(repository.loadRegistrations());
    }

    public Event createEvent(String title, String category, LocalDate date, String venue, int capacity) throws IOException {
        Event event = new Event(UUID.randomUUID().toString().substring(0, 8), InputValidator.required(title, "Title"), InputValidator.required(category, "Category"), InputValidator.futureOrToday(date), InputValidator.required(venue, "Venue"), InputValidator.positive(capacity, "Capacity"), true);
        events.add(event); save(); return event;
    }
    public void updateEvent(String id, String title, String category, LocalDate date, String venue, int capacity) throws IOException {
        Event event = findEvent(id); int booked = registrationsFor(id).size();
        if (capacity < booked) throw new IllegalArgumentException("Capacity cannot be less than current registrations (" + booked + ").");
        event.update(InputValidator.required(title, "Title"), InputValidator.required(category, "Category"), InputValidator.futureOrToday(date), InputValidator.required(venue, "Venue"), InputValidator.positive(capacity, "Capacity")); save();
    }
    public void cancelEvent(String id) throws IOException { findEvent(id).cancel(); save(); }
    public Registration registerStudent(String eventId, String studentId, String studentName) throws IOException {
        Event event = findEvent(eventId);
        if (!event.isActive()) throw new IllegalArgumentException("This event is cancelled.");
        if (registrationsFor(eventId).size() >= event.getCapacity()) throw new IllegalArgumentException("This event is full.");
        if (registrations.stream().anyMatch(item -> item.eventId().equals(eventId) && item.studentId().equalsIgnoreCase(studentId))) throw new IllegalArgumentException("Student is already registered.");
        Registration registration = new Registration(eventId, InputValidator.required(studentId, "Student ID"), InputValidator.required(studentName, "Student name"), LocalDateTime.now());
        registrations.add(registration); save(); return registration;
    }
    public void cancelRegistration(String eventId, String studentId) throws IOException {
        boolean removed = registrations.removeIf(item -> item.eventId().equals(eventId) && item.studentId().equalsIgnoreCase(studentId));
        if (!removed) throw new IllegalArgumentException("Registration not found.");
        save();
    }
    public Event findEvent(String id) { return events.stream().filter(event -> event.getId().equalsIgnoreCase(id)).findFirst().orElseThrow(() -> new IllegalArgumentException("Event not found.")); }
    public List<Event> getEvents() { return List.copyOf(events); }
    public List<Registration> getRegistrations() { return List.copyOf(registrations); }
    public List<Event> search(String query) { String normalized = query.toLowerCase(); return events.stream().filter(event -> event.getTitle().toLowerCase().contains(normalized) || event.getCategory().toLowerCase().contains(normalized) || event.getDate().toString().contains(normalized)).collect(Collectors.toList()); }
    public long registrationCount(String eventId) { return registrationsFor(eventId).size(); }
    private List<Registration> registrationsFor(String eventId) { return registrations.stream().filter(item -> item.eventId().equalsIgnoreCase(eventId)).toList(); }
    private void save() throws IOException { repository.saveEvents(events); repository.saveRegistrations(registrations); }
}
