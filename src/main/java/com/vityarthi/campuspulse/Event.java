package com.vityarthi.campuspulse;

import java.time.LocalDate;

public class Event {
    private final String id;
    private String title;
    private String category;
    private LocalDate date;
    private String venue;
    private int capacity;
    private boolean active;

    public Event(String id, String title, String category, LocalDate date, String venue, int capacity, boolean active) {
        this.id = id;
        this.title = title;
        this.category = category;
        this.date = date;
        this.venue = venue;
        this.capacity = capacity;
        this.active = active;
    }

    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getCategory() { return category; }
    public LocalDate getDate() { return date; }
    public String getVenue() { return venue; }
    public int getCapacity() { return capacity; }
    public boolean isActive() { return active; }
    public void update(String title, String category, LocalDate date, String venue, int capacity) {
        this.title = title;
        this.category = category;
        this.date = date;
        this.venue = venue;
        this.capacity = capacity;
    }
    public void cancel() { active = false; }

    @Override
    public String toString() {
        return id + " | " + title + " | " + category + " | " + date + " | " + venue
                + " | seats: " + capacity + " | " + (active ? "OPEN" : "CANCELLED");
    }
}
