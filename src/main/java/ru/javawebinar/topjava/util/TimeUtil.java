package ru.javawebinar.topjava.util;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class TimeUtil {

    private static final DateTimeFormatter DEFAULT_DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static LocalDateTime toDate(String date) {
        return LocalDateTime.parse(date.replace('T', ' '), DEFAULT_DATE_FORMAT);
    }

    public static String toString(LocalDateTime localDateTime) {
        return localDateTime == null
                ? "" : localDateTime.format(DEFAULT_DATE_FORMAT);
    }
    public static boolean isBetweenHalfOpen(LocalTime lt, LocalTime startTime, LocalTime endTime) {
        return lt.compareTo(startTime) >= 0 && lt.compareTo(endTime) < 0;
    }
}
