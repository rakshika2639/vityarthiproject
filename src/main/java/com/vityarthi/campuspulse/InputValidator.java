package com.vityarthi.campuspulse;

import java.time.LocalDate;

public final class InputValidator {
    private InputValidator() {}
    public static String required(String value, String field) {
        if (value == null || value.isBlank()) throw new IllegalArgumentException(field + " cannot be empty.");
        return value.trim();
    }
    public static int positive(int value, String field) {
        if (value <= 0) throw new IllegalArgumentException(field + " must be greater than zero.");
        return value;
    }
    public static LocalDate futureOrToday(LocalDate value) {
        if (value.isBefore(LocalDate.now())) throw new IllegalArgumentException("Event date cannot be in the past.");
        return value;
    }
}
