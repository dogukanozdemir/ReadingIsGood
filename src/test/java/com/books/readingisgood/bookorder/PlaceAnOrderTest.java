package com.books.readingisgood.bookorder;

import com.books.readingisgood.authentication.util.AuthUtil;
import com.books.readingisgood.dto.order.OrderDto;
import com.books.readingisgood.dto.order.PlaceOrderRequestDto;
import com.books.readingisgood.entity.Book;
import com.books.readingisgood.entity.BookOrder;
import com.books.readingisgood.entity.Customer;
import com.books.readingisgood.repository.BookOrderRepository;
import com.books.readingisgood.service.BookOrderService;
import com.books.readingisgood.service.BookService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@MockitoSettings(strictness = Strictness.LENIENT)
class PlaceAnOrderTest {

    @Mock
    private AuthUtil authUtil;

    @Mock
    private EntityManager entityManager;

    @Mock
    private BookService bookService;

    @Mock
    private BookOrderRepository bookOrderRepository;

    @InjectMocks
    private BookOrderService bookOrderService;

    private Customer customer;
    private Book book;

    @BeforeEach
    void setup(){
        customer = Customer.builder()
                .id(1L)
                .username("Test Customer")
                .email("test@example.com")
                .build();
        book = Book.builder()
                .id(1L)
                .title("Test Book")
                .price(10.0)
                .quantityInStock(2)
                .build();
    }

    @Test
    void placeAnOrderSuccess() {
        when(authUtil.getCurrentCustomer()).thenReturn(customer);

        when(entityManager.find(Book.class, 1L, LockModeType.PESSIMISTIC_WRITE)).thenReturn(book);

        doNothing().when(bookService).updateBookStockByEntity(ArgumentMatchers.any(), anyInt());

        when(bookOrderRepository.save(any())).thenReturn(
                BookOrder.builder()
                        .id(1L)
                        .bookId(book.getId())
                        .bookName(book.getTitle())
                        .purchasedAmount(20.0)
                        .purchasedBookCount(2)
                        .purchaseDate(LocalDate.now())
                        .customerId(customer.getId())
                        .build()
        );

        PlaceOrderRequestDto requestDto = PlaceOrderRequestDto.builder()
                .bookId(1L)
                .amount(2)
                .purchaseDate(LocalDate.now().toString())
                .build();
        OrderDto order = bookOrderService.placeAnOrder(requestDto);

        assertEquals(book.getId(), order.getBookId());
        assertEquals(book.getTitle(), order.getBookName());
        assertEquals(20.0, order.getPurchasedAmount());
        assertEquals(2, order.getPurchasedBookCount());
        assertEquals(LocalDate.now(), order.getPurchaseDate());
        assertEquals(customer.getId(), order.getCustomerId());
    }

    @Test
    void placeAnOrder_WithBookNotFound_ThrowsNotFoundException() {
        when(entityManager.find(Book.class, 1L, LockModeType.PESSIMISTIC_WRITE)).thenReturn(null);

        PlaceOrderRequestDto requestDto = PlaceOrderRequestDto.builder()
                .bookId(1L)
                .amount(1)
                .purchaseDate(LocalDate.now().toString())
                .build();
        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> bookOrderService.placeAnOrder(requestDto));

    }

    @Test
    void placeAnOrder_WithInvalidPurchaseDate_ThrowsBadRequestException() {
        when(authUtil.getCurrentCustomer()).thenReturn(customer);
        when(entityManager.find(Book.class,1L,LockModeType.PESSIMISTIC_WRITE)).thenReturn(book);

        PlaceOrderRequestDto requestDto = PlaceOrderRequestDto.builder()
                .bookId(1L)
                .amount(1)
                .purchaseDate("invalid-date")
                .build();

        assertThrows(ResponseStatusException.class, () -> bookOrderService.placeAnOrder(requestDto));
    }

    @Test
    void placeAnOrder_WithInsufficientStock_ThrowsNotFoundException(){
        when(authUtil.getCurrentCustomer()).thenReturn(customer);
        when(entityManager.find(Book.class,1L,LockModeType.PESSIMISTIC_WRITE)).thenReturn(book);
        PlaceOrderRequestDto requestDto = PlaceOrderRequestDto.builder()
                .bookId(1L)
                .amount(3)
                .purchaseDate(LocalDate.now().toString())
                .build();

        assertThrows(ResponseStatusException.class, () -> bookOrderService.placeAnOrder(requestDto));

    }
}
