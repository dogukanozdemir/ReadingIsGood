package com.books.readingisgood.controller;

import com.books.readingisgood.dto.book.BookDto;
import com.books.readingisgood.dto.book.CreateBookRequestDto;
import com.books.readingisgood.dto.book.UpdateBookStockRequestDto;
import com.books.readingisgood.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

    @PatchMapping(value = "/book/{id}")
    public ResponseEntity<BookDto> updateBookStock(@Validated @RequestBody UpdateBookStockRequestDto requestDto,
                                                   @PathVariable(value = "id") Long id){
        return ResponseEntity.ok(bookService.updateBookStockById(requestDto,id));

    }


}
