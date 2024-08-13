package ru.yandex.kardo.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class DateMapper {
    private static final DateTimeFormatter EXCEPTION_FORMATTER = DateTimeFormatter
            .ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final DateTimeFormatter PROFILE_BIRTHDAY_FORMATTER = DateTimeFormatter
            .ofPattern("dd.MM.yyyy");

    public static String getExceptionTimestampString(LocalDateTime localDateTime) {
        return EXCEPTION_FORMATTER.format(localDateTime);
    }

    public static String getUserProfileBirthdayString(LocalDate localDate) {
        if (localDate == null) {
            return null;
        }
        return PROFILE_BIRTHDAY_FORMATTER.format(localDate);
    }
}