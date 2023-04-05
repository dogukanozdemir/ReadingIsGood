package com.books.readingisgood.bookorder;

import com.books.readingisgood.dto.order.OrderByCustomerPageDto;
import com.books.readingisgood.dto.order.OrderDto;
import com.books.readingisgood.entity.BookOrder;
import com.books.readingisgood.repository.BookOrderRepository;
import com.books.readingisgood.service.BookOrderService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@MockitoSettings(strictness = Strictness.LENIENT)
class GetOrdersByCustomerTest {

    @InjectMocks
    private BookOrderService bookOrderService;

    @Mock
    private BookOrderRepository bookOrderRepository;

    @Test
    void getOrdersByCustomerSuccess() {

        Long customerId = 1L;
        int pageNo = 0;
        int pageSize = 10;
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by("purchaseDate").descending());
        LocalDate purchaseDate = LocalDate.of(2022, 1, 1);
        BookOrder bookOrder = BookOrder.builder()
                .id(1L)
                .bookId(1L)
                .bookTitle("Book A")
                .purchasedBookCount(2)
                .purchasedAmount(20.0)
                .purchaseDate(purchaseDate)
                .customerId(customerId)
                .build();
        List<BookOrder> orders = List.of(bookOrder);
        PageImpl<BookOrder> page = new PageImpl<>(orders, pageable, orders.size());

        when(bookOrderRepository.findByCustomerId(eq(customerId), any(Pageable.class))).thenReturn(page);

        OrderByCustomerPageDto result = bookOrderService.getOrdersByCustomer(customerId, pageNo, pageSize);

        assertEquals(pageNo, result.getPageNo());
        assertEquals(pageSize, result.getPageSize());
        assertEquals(1, result.getNumberOfElements());
        assertEquals(1, result.getTotalPages());
        List<OrderDto> ordersResult = result.getOrders();
        assertEquals(1, ordersResult.size());
        OrderDto orderDto = ordersResult.get(0);
        assertEquals(bookOrder.getId(), orderDto.getId());
        assertEquals(bookOrder.getBookId(), orderDto.getBookId());
        assertEquals(bookOrder.getBookTitle(), orderDto.getBookName());
        assertEquals(bookOrder.getPurchasedBookCount(), orderDto.getPurchasedBookCount());
        assertEquals(bookOrder.getPurchasedAmount(), orderDto.getPurchasedAmount());
        assertEquals(bookOrder.getPurchaseDate(), orderDto.getPurchaseDate());
        assertEquals(bookOrder.getCustomerId(), orderDto.getCustomerId());
    }
}
