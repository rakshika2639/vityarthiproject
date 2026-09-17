package com.vityarthi.campuspulse;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;

public class CampusPulseTest {
    public static void main(String[] args) throws Exception {
        Path temp = Files.createTempDirectory("campuspulse-test");
        EventService service = new EventService(new CsvEventRepository(temp));
        Event event = service.createEvent("Java Workshop", "Academic", LocalDate.now(), "Lab 1", 1);
        service.registerStudent(event.getId(), "S001", "Asha");
        expectFailure(() -> service.registerStudent(event.getId(), "S001", "Asha"), "duplicate registration");
        expectFailure(() -> service.registerStudent(event.getId(), "S002", "Ravi"), "capacity limit");
        assert service.registrationCount(event.getId()) == 1 : "registration count";
        service.cancelRegistration(event.getId(), "S001");
        service.registerStudent(event.getId(), "S002", "Ravi");
        service.cancelEvent(event.getId());
        expectFailure(() -> service.registerStudent(event.getId(), "S003", "Mina"), "cancelled event");
        assert service.getRegistrations().size() == 1 : "persisted registration";
        System.out.println("All CampusPulse tests passed.");
    }
    private static void expectFailure(CheckedAction action, String name) throws Exception {
        try { action.run(); throw new AssertionError("Expected failure: " + name); }
        catch (IllegalArgumentException expected) { }
    }
    @FunctionalInterface private interface CheckedAction { void run() throws Exception; }
}
