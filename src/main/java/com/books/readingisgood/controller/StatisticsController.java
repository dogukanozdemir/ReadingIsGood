package com.books.readingisgood.controller;


import com.books.readingisgood.dto.statistics.StatisticsDto;
import com.books.readingisgood.entity.Book;
import com.books.readingisgood.entity.BookOrder;
import com.books.readingisgood.repository.BookOrderRepository;
import com.books.readingisgood.repository.BookRepository;
import com.books.readingisgood.service.StatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/service")
public class StatisticsController {

    private final StatisticsService statisticsService;
    private final BookOrderRepository bookOrderRepository;
    private final BookRepository bookRepository;

    @GetMapping("/statistics")
    public List<StatisticsDto> statisticsByMonth(@RequestParam("year") int year){
        return statisticsService.customerStatics(year);
    }
}
