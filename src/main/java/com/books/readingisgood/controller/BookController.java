package com.books.readingisgood.controller;

import com.books.readingisgood.dto.book.BookDto;
import com.books.readingisgood.dto.book.CreateBookRequestDto;
import com.books.readingisgood.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/service")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @PostMapping(value = "/book")
    public ResponseEntity<BookDto> createBook(@Validated @RequestBody CreateBookRequestDto requestDto){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(bookService.addBook(requestDto));
    }


}
