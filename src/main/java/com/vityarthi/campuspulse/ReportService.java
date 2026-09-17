package com.vityarthi.campuspulse;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class ReportService {
    private final EventService service;
    public ReportService(EventService service) { this.service = service; }
    public String buildSummary() {
        long active = service.getEvents().stream().filter(Event::isActive).count();
        long totalSeats = service.getEvents().stream().filter(Event::isActive).mapToLong(Event::getCapacity).sum();
        long registrations = service.getRegistrations().size();
        Map<String, Long> byCategory = service.getEvents().stream().collect(Collectors.groupingBy(Event::getCategory, LinkedHashMap::new, Collectors.counting()));
        StringBuilder report = new StringBuilder("\n--- CampusPulse Report ---\n");
        report.append("Active events: ").append(active).append("\nTotal capacity: ").append(totalSeats).append("\nTotal registrations: ").append(registrations).append("\n");
        report.append("Events by category: ").append(byCategory).append("\n");
        for (Event event : service.getEvents()) report.append(event.getId()).append(" | ").append(event.getTitle()).append(" | ").append(service.registrationCount(event.getId())).append("/").append(event.getCapacity()).append(" registered\n");
        return report.toString();
    }
}
