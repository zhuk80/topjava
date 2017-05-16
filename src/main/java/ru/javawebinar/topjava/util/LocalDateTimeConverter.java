package ru.javawebinar.topjava.util;

import org.springframework.core.convert.converter.Converter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by evgeniy on 16.05.2017.
 */
public class LocalDateTimeConverter implements Converter<String, LocalDateTime> {

    private final static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

    @Override
    public LocalDateTime convert(String s) {
        if (s == null || s.isEmpty()) return null;

        return LocalDateTime.parse(s, formatter);
    }
}
