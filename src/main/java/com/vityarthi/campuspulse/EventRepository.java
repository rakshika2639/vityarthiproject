package com.vityarthi.campuspulse;

import java.io.IOException;
import java.util.List;

public interface EventRepository {
    List<Event> loadEvents() throws IOException;
    List<Registration> loadRegistrations() throws IOException;
    void saveEvents(List<Event> events) throws IOException;
    void saveRegistrations(List<Registration> registrations) throws IOException;
}
