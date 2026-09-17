package com.vityarthi.campuspulse;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Scanner;

public class ConsoleMenu {
    private final EventService service;
    private final ReportService reports;
    private final Scanner scanner;
    public ConsoleMenu(EventService service, Scanner scanner) { this.service = service; this.reports = new ReportService(service); this.scanner = scanner; }
    public void run() {
        System.out.println("\n=== CampusPulse Event Manager ===");
        boolean running = true;
        while (running) {
            System.out.println("\n1. List events  2. Create event  3. Register student  4. Cancel registration  5. Search  6. Reports  7. Cancel event  0. Exit");
            try {
                switch (read("Choose: ")) {
                    case "1" -> service.getEvents().forEach(System.out::println);
                    case "2" -> createEvent();
                    case "3" -> register();
                    case "4" -> cancelRegistration();
                    case "5" -> service.search(read("Search title, category, or date: ")).forEach(System.out::println);
                    case "6" -> System.out.println(reports.buildSummary());
                    case "7" -> { service.cancelEvent(read("Event ID: ")); System.out.println("Event cancelled."); }
                    case "0" -> running = false;
                    default -> System.out.println("Choose a listed option.");
                }
            } catch (Exception exception) { System.out.println("Error: " + exception.getMessage()); }
        }
        System.out.println("Goodbye.");
    }
    private void createEvent() throws IOException { Event event = service.createEvent(read("Title: "), read("Category: "), LocalDate.parse(read("Date (YYYY-MM-DD): ")), read("Venue: "), Integer.parseInt(read("Capacity: "))); System.out.println("Created event " + event.getId()); }
    private void register() throws IOException { service.registerStudent(read("Event ID: "), read("Student ID: "), read("Student name: ")); System.out.println("Registration successful."); }
    private void cancelRegistration() throws IOException { service.cancelRegistration(read("Event ID: "), read("Student ID: ")); System.out.println("Registration cancelled."); }
    private String read(String prompt) { System.out.print(prompt); return scanner.nextLine().trim(); }
}
