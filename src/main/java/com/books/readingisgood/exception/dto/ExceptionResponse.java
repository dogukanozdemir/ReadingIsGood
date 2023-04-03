package com.books.readingisgood.exception.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ExceptionResponse {

    private LocalDateTime time;
    private String message;
    private String details;
}

