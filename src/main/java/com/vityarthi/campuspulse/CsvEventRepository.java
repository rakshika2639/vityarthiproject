package com.vityarthi.campuspulse;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CsvEventRepository implements EventRepository {
    private final Path eventsFile;
    private final Path registrationsFile;

    public CsvEventRepository(Path dataDirectory) throws IOException {
        Files.createDirectories(dataDirectory);
        eventsFile = dataDirectory.resolve("events.csv");
        registrationsFile = dataDirectory.resolve("registrations.csv");
        if (Files.notExists(eventsFile)) Files.writeString(eventsFile, "id,title,category,date,venue,capacity,active\n");
        if (Files.notExists(registrationsFile)) Files.writeString(registrationsFile, "eventId,studentId,studentName,registeredAt\n");
    }

    @Override
    public List<Event> loadEvents() throws IOException {
        List<Event> result = new ArrayList<>();
        for (String line : Files.readAllLines(eventsFile).subList(1, Files.readAllLines(eventsFile).size())) {
            if (!line.isBlank()) {
                String[] fields = parse(line, 7);
                result.add(new Event(fields[0], fields[1], fields[2], LocalDate.parse(fields[3]), fields[4], Integer.parseInt(fields[5]), Boolean.parseBoolean(fields[6])));
            }
        }
        return result;
    }

    @Override
    public List<Registration> loadRegistrations() throws IOException {
        List<Registration> result = new ArrayList<>();
        List<String> lines = Files.readAllLines(registrationsFile);
        for (String line : lines.subList(1, lines.size())) {
            if (!line.isBlank()) {
                String[] fields = parse(line, 4);
                result.add(new Registration(fields[0], fields[1], fields[2], LocalDateTime.parse(fields[3])));
            }
        }
        return result;
    }

    @Override
    public void saveEvents(List<Event> events) throws IOException {
        List<String> lines = new ArrayList<>(List.of("id,title,category,date,venue,capacity,active"));
        for (Event event : events) lines.add(String.join(",", escape(event.getId()), escape(event.getTitle()), escape(event.getCategory()), event.getDate().toString(), escape(event.getVenue()), String.valueOf(event.getCapacity()), String.valueOf(event.isActive())));
        Files.write(eventsFile, lines);
    }

    @Override
    public void saveRegistrations(List<Registration> registrations) throws IOException {
        List<String> lines = new ArrayList<>(List.of("eventId,studentId,studentName,registeredAt"));
        for (Registration registration : registrations) lines.add(String.join(",", escape(registration.eventId()), escape(registration.studentId()), escape(registration.studentName()), registration.registeredAt().toString()));
        Files.write(registrationsFile, lines);
    }

    private static String escape(String value) { return value.replace("\\", "\\\\").replace(",", "\\,"); }
    private static String[] parse(String line, int expected) {
        List<String> fields = new ArrayList<>();
        StringBuilder current = new StringBuilder();
        boolean escaped = false;
        for (char character : line.toCharArray()) {
            if (escaped) { current.append(character); escaped = false; }
            else if (character == '\\') escaped = true;
            else if (character == ',') { fields.add(current.toString()); current.setLength(0); }
            else current.append(character);
        }
        fields.add(current.toString());
        if (fields.size() != expected) throw new IllegalArgumentException("Malformed CSV row: " + line);
        return fields.toArray(String[]::new);
    }
}
