package com.books.readingisgood.config;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Date;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(value = JwtException.class)
    public ResponseEntity<Object> defaultErrorHandler(HttpServletRequest req, HttpServletResponse res, JwtException e){
        log.error("exception",e);
        return new ResponseEntity<>(
                ExceptionResponse.builder()
                        .message(e.getMessage())
                        .time(LocalDateTime.now())
                        .build(),
                HttpStatus.UNAUTHORIZED
        );
    }

    @ExceptionHandler(value = ResponseStatusException.class)
    public ResponseEntity<Object> responseStatusExceptionHandler(HttpServletRequest req, HttpServletResponse res, ResponseStatusException rse){
        return new ResponseEntity<>(
                ExceptionResponse.builder()
                        .message(rse.getReason())
                        .time(LocalDateTime.now())
                        .build(),
                rse.getStatusCode()
        );
    }
}
