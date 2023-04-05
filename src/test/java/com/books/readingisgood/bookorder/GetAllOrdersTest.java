package com.books.readingisgood.bookorder;

import com.books.readingisgood.dto.order.OrderDto;
import com.books.readingisgood.entity.BookOrder;
import com.books.readingisgood.repository.BookOrderRepository;
import com.books.readingisgood.service.BookOrderService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@MockitoSettings(strictness = Strictness.LENIENT)
class GetAllOrdersTest {

    @InjectMocks
    private BookOrderService bookOrderService;

    @Mock
    private BookOrderRepository bookOrderRepository;


    @Test
    void getAllOrdersSuccess(){
        BookOrder bookOrder = BookOrder.builder()
                .id(1L)
                .bookId(2L)
                .bookTitle("The Great Gatsby")
                .purchasedBookCount(1)
                .purchasedAmount(9.99)
                .purchaseDate(LocalDate.of(2022, 1, 1))
                .customerId(3L)
                .build();

        when(bookOrderRepository.findAll()).thenReturn(Collections.singletonList(bookOrder));


        List<OrderDto> result = bookOrderService.getAllOrders();

        assertEquals(1, result.size());
        OrderDto orderDto = result.get(0);
        assertEquals(bookOrder.getId(), orderDto.getId());
        assertEquals(bookOrder.getBookId(), orderDto.getBookId());
        assertEquals(bookOrder.getBookTitle(), orderDto.getBookName());
        assertEquals(bookOrder.getPurchasedBookCount(), orderDto.getPurchasedBookCount());
        assertEquals(bookOrder.getPurchasedAmount(), orderDto.getPurchasedAmount());
        assertEquals(bookOrder.getPurchaseDate(), orderDto.getPurchaseDate());
        assertEquals(bookOrder.getCustomerId(), orderDto.getCustomerId());
    }
}
