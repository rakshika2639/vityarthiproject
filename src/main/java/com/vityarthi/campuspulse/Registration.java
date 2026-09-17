package com.vityarthi.campuspulse;

import java.time.LocalDateTime;

public record Registration(String eventId, String studentId, String studentName, LocalDateTime registeredAt) {}
