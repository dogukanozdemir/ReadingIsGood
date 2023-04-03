package com.books.readingisgood.config;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ExceptionResponse {

    private String message;
    private LocalDateTime time;
}

