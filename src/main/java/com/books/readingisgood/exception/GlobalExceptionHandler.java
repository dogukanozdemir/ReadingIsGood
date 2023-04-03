package com.books.readingisgood.exception;

import com.books.readingisgood.exception.dto.ExceptionResponse;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<Object> methodArgumentNotValidHandler(HttpServletRequest req, HttpServletResponse res, MethodArgumentNotValidException e){
        String errors = e.getBindingResult().getAllErrors().stream()
                        .map(DefaultMessageSourceResolvable::getDefaultMessage)
                        .collect(Collectors.joining(" - "));
        return new ResponseEntity<>(
                ExceptionResponse.builder()
                        .time(LocalDateTime.now())
                        .message("Constraint Validation Failed")
                        .details(errors)
                        .build(),
                HttpStatus.BAD_REQUEST
        );
    }
    @ExceptionHandler(value = JwtException.class)
    public ResponseEntity<Object> jwtExceptionHandler(HttpServletRequest req, HttpServletResponse res, JwtException e){
        log.error("exception",e);
        return new ResponseEntity<>(
                ExceptionResponse.builder()
                        .message("Authentication Failed!")
                        .details(e.getMessage())
                        .time(LocalDateTime.now())
                        .build(),
                HttpStatus.UNAUTHORIZED
        );
    }

    @ExceptionHandler(value = ReadingIsGoodException.class)
    public ResponseEntity<Object> responseStatusExceptionHandler(HttpServletRequest req, HttpServletResponse res, ReadingIsGoodException ex){
        return new ResponseEntity<>(
                ExceptionResponse.builder()
                        .message(ex.getTitle())
                        .details(ex.getReason())
                        .time(LocalDateTime.now())
                        .build(),
                ex.getStatusCode()
        );
    }
}
