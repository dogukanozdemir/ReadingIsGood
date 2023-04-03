package com.books.readingisgood.service;

import com.books.readingisgood.authentication.util.AuthUtil;
import com.books.readingisgood.dto.book.BookDto;
import com.books.readingisgood.dto.book.CreateBookRequestDto;
import com.books.readingisgood.entity.Book;
import com.books.readingisgood.entity.Customer;
import com.books.readingisgood.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    public BookDto addBook(CreateBookRequestDto requestDto){
        Book book = Book.builder()
                .title(requestDto.getTitle())
                .author(requestDto.getAuthor())
                .quantityInStock(requestDto.getQuantityInStock())
                .totalPages(requestDto.getTotalPages())
                .build();
        bookRepository.save(book);
        return BookDto.builder()
                .id(book.getId())
                .title(book.getTitle())
                .author(book.getAuthor())
                .quantityInStock(book.getQuantityInStock())
                .totalPages(book.getTotalPages())
                .build();
    }
}
