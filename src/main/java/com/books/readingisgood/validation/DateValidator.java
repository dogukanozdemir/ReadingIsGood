package com.books.readingisgood.validation;

import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;


public class DateValidator{

    public static LocalDate validate(String dateString){
        try {
            return LocalDate.parse(dateString,DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        } catch (DateTimeParseException ex){
            return null;
        }
    }

}
