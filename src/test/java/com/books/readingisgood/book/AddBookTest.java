package com.books.readingisgood.book;

import com.books.readingisgood.dto.book.BookDto;
import com.books.readingisgood.dto.book.CreateBookRequestDto;
import com.books.readingisgood.entity.Book;
import com.books.readingisgood.repository.BookRepository;
import com.books.readingisgood.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@MockitoSettings(strictness = Strictness.LENIENT)
class AddBookTest {

    @InjectMocks
    private BookService bookService;

    @Mock
    private BookRepository bookRepository;

    private CreateBookRequestDto createBookRequestDto;

    @BeforeEach
    void setup() {
        createBookRequestDto = CreateBookRequestDto.builder()
                .title("Test Book")
                .author("Test Author")
                .price(19.99)
                .quantityInStock(10)
                .totalPages(200)
                .build();
    }

    @Test
    void addBookSuccess() {
        Book book = Book.builder()
                .id(1L)
                .title(createBookRequestDto.getTitle())
                .author(createBookRequestDto.getAuthor())
                .price(createBookRequestDto.getPrice())
                .quantityInStock(createBookRequestDto.getQuantityInStock())
                .totalPages(createBookRequestDto.getTotalPages())
                .build();
        when(bookRepository.save(any())).thenReturn(book);

        BookDto bookDto = bookService.addBook(createBookRequestDto);

        assertNotNull(bookDto);
        assertEquals(book.getTitle(), bookDto.getTitle());
        assertEquals(book.getAuthor(), bookDto.getAuthor());
        assertEquals(book.getPrice(), bookDto.getPrice());
        assertEquals(book.getQuantityInStock(), bookDto.getQuantityInStock());
        assertEquals(book.getTotalPages(), bookDto.getTotalPages());
    }

}
