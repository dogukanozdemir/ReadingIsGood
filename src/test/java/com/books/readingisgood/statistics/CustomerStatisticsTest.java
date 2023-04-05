package com.books.readingisgood.statistics;

import com.books.readingisgood.authentication.util.AuthUtil;
import com.books.readingisgood.dto.statistics.StatisticsDto;
import com.books.readingisgood.entity.BookOrder;
import com.books.readingisgood.entity.Customer;
import com.books.readingisgood.repository.BookOrderRepository;
import com.books.readingisgood.service.StatisticsService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@MockitoSettings(strictness = Strictness.LENIENT)
class CustomerStatisticsTest {

    @InjectMocks
    private StatisticsService statisticsService;
    @Mock
    private BookOrderRepository bookOrderRepository;
    @Mock
    private AuthUtil authUtil;

    @Test
    void customerStatisticsSuccess() {

        Customer currentCustomer = Customer.builder()
                .id(1L)
                .build();
        when(authUtil.getCurrentCustomer()).thenReturn(currentCustomer);


        List<BookOrder> bookOrders = Arrays.asList(
                BookOrder.builder().id(1L).customerId(1L).purchaseDate(LocalDate.of(2022, 1, 1))
                        .purchasedBookCount(2).purchasedAmount(20.0).build(),
                BookOrder.builder().id(2L).customerId(1L).purchaseDate(LocalDate.of(2022, 1, 2))
                        .purchasedBookCount(3).purchasedAmount(30.0).build(),
                BookOrder.builder().id(3L).customerId(1L).purchaseDate(LocalDate.of(2022, 2, 1))
                        .purchasedBookCount(1).purchasedAmount(10.0).build(),
                BookOrder.builder().id(4L).customerId(1L).purchaseDate(LocalDate.of(2022, 2, 2))
                        .purchasedBookCount(4).purchasedAmount(40.0).build()
        );
        when(bookOrderRepository.findByCustomerIdAndYear(1L, 2022)).thenReturn(bookOrders);

        List<StatisticsDto> result = statisticsService.customerStatics(2022);

        assertEquals(2, result.size());

        StatisticsDto statisticsJan = result.get(0);
        assertEquals("JANUARY", statisticsJan.getMonth());
        assertEquals(2, statisticsJan.getTotalOrderCount());
        assertEquals(5, statisticsJan.getTotalBookCount());
        assertEquals(50.0, statisticsJan.getTotalPurchasedAmount());

        StatisticsDto statisticsFeb = result.get(1);
        assertEquals("FEBRUARY", statisticsFeb.getMonth());
        assertEquals(2, statisticsFeb.getTotalOrderCount());
        assertEquals(5, statisticsFeb.getTotalBookCount());
        assertEquals(50.0, statisticsFeb.getTotalPurchasedAmount());
    }
}
