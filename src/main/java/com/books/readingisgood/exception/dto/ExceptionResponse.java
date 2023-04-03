package com.books.readingisgood.exception.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@Builder
public class ExceptionResponse {

    private LocalDateTime time;
    private String error;
    private Map<String,String> errors;
}

