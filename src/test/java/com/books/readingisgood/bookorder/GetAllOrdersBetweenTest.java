package com.books.readingisgood.bookorder;

import com.books.readingisgood.dto.order.OrderDto;
import com.books.readingisgood.entity.Book;
import com.books.readingisgood.entity.BookOrder;
import com.books.readingisgood.entity.Customer;
import com.books.readingisgood.repository.BookOrderRepository;
import com.books.readingisgood.service.BookOrderService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@MockitoSettings(strictness = Strictness.LENIENT)
class GetAllOrdersBetweenTest {

    @InjectMocks
    private BookOrderService bookOrderService;

    @Mock
    private BookOrderRepository bookOrderRepository;


    @Test
    void getOrdersBetweenSuccess() {
        Customer customer = new Customer();
        customer.setId(1L);

        Book book = Book.builder()
                .id(1L)
                .title("Test Book")
                .quantityInStock(5)
                .price(10.0)
                .build();

        BookOrder order = BookOrder.builder()
                .id(1L)
                .bookId(book.getId())
                .bookName(book.getTitle())
                .purchasedBookCount(2)
                .purchasedAmount(book.getPrice() * 2)
                .purchaseDate(LocalDate.now())
                .customerId(customer.getId())
                .build();

        when(bookOrderRepository.findByPurchaseDateBetween(any(LocalDate.class), any(LocalDate.class)))
                .thenReturn(Collections.singletonList(order));

        List<OrderDto> orders = bookOrderService.getAllOrdersBetween("2022-01-01", "2022-01-31");
        OrderDto expectedOrderDto = OrderDto.builder()
                .id(order.getId())
                .bookId(order.getBookId())
                .bookName(order.getBookName())
                .purchasedBookCount(order.getPurchasedBookCount())
                .purchasedAmount(order.getPurchasedAmount())
                .purchaseDate(order.getPurchaseDate())
                .customerId(order.getCustomerId())
                .build();
        List<OrderDto> expectedOrders = Collections.singletonList(expectedOrderDto);
        assertEquals(expectedOrders, orders);
    }

    @Test
    void getAllOrdersBetween_WithInvalidDate_ThrowsBadRequestException(){
        assertThrows(ResponseStatusException.class,
                () -> bookOrderService.getAllOrdersBetween("invalid","date"));
    }
}
