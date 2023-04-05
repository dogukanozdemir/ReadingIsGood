package com.books.readingisgood.book;

import com.books.readingisgood.dto.book.BookDto;
import com.books.readingisgood.dto.book.UpdateBookStockRequestDto;
import com.books.readingisgood.entity.Book;
import com.books.readingisgood.repository.BookRepository;
import com.books.readingisgood.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@MockitoSettings(strictness = Strictness.LENIENT)
class UpdateBookStockTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    private Book book;

    @BeforeEach
    void setup(){
        book = Book.builder()
                .id(1L)
                .title("Test Book")
                .author("Test Author")
                .quantityInStock(10)
                .totalPages(100)
                .build();
    }

    @Test
    void updateBookStockByIdSuccess() {

        UpdateBookStockRequestDto requestDto = new UpdateBookStockRequestDto(15);

        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        BookDto updatedBook = bookService.updateBookStockById(requestDto, 1L);


        assertThat(updatedBook).isNotNull();
        assertEquals(15, updatedBook.getQuantityInStock());
    }

    @Test
    void updateBookStockById_WithBookNotFound_ThrowsNotFoundException() {
        UpdateBookStockRequestDto requestDto = new UpdateBookStockRequestDto(10);

        when(bookRepository.findById(any())).thenReturn(Optional.empty());
        assertThrows(ResponseStatusException.class, () -> bookService.updateBookStockById(requestDto, 1L));
    }

    @Test
    void updateBookStockById_WithInvalidStockValue_ThrowsBadRequestException() {
        int updatedStock = -5;
        UpdateBookStockRequestDto requestDto = new UpdateBookStockRequestDto(updatedStock);

        when(bookRepository.findById(any())).thenReturn(Optional.of(book));
        assertThrows(ResponseStatusException.class, () -> bookService.updateBookStockById(requestDto, 1L));
    }

    @Test
    void updateBookStockByEntitySuccess() {

        int updatedStock = 15;
        bookService.updateBookStockByEntity(book, updatedStock);

        assertEquals(book.getQuantityInStock(),updatedStock);
    }
}
