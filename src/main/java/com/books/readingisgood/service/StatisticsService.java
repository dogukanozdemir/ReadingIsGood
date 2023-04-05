package com.books.readingisgood.service;


import com.books.readingisgood.authentication.util.AuthUtil;
import com.books.readingisgood.dto.statistics.StatisticsDto;
import com.books.readingisgood.entity.BookOrder;
import com.books.readingisgood.entity.Customer;
import com.books.readingisgood.repository.BookOrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Month;
import java.util.*;

@RequiredArgsConstructor
@Service
@Slf4j
public class StatisticsService {

    private final AuthUtil authUtil;
    private final BookOrderRepository bookOrderRepository;

    public List<StatisticsDto> customerStatics(int year) {
        Customer currentCustomer = authUtil.getCurrentCustomer();
        List<BookOrder> bookOrders = bookOrderRepository.findByCustomerIdAndYear(currentCustomer.getId(), year);

        Map<Integer, StatisticsDto> statisticsMap = new TreeMap<>();
        for (BookOrder order : bookOrders) {
            int monthIndex = order.getPurchaseDate().getMonthValue();

            StatisticsDto statistics = statisticsMap.computeIfAbsent(monthIndex,
                            k -> StatisticsDto.builder()
                                    .month(Month.of(monthIndex).toString())
                                    .totalOrderCount(0)
                                    .totalBookCount(0)
                                    .totalPurchasedAmount(0)
                                    .build());

            statistics.setTotalOrderCount(statistics.getTotalOrderCount() + 1);
            statistics.setTotalBookCount(statistics.getTotalBookCount() + order.getPurchasedBookCount());
            statistics.setTotalPurchasedAmount(statistics.getTotalPurchasedAmount() + order.getPurchasedAmount());
        }
        return new ArrayList<>(statisticsMap.values());
    }
}
