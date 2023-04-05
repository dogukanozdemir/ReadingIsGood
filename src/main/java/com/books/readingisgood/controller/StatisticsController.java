package com.books.readingisgood.controller;


import com.books.readingisgood.dto.statistics.StatisticsDto;
import com.books.readingisgood.entity.Book;
import com.books.readingisgood.entity.BookOrder;
import com.books.readingisgood.repository.BookOrderRepository;
import com.books.readingisgood.repository.BookRepository;
import com.books.readingisgood.service.StatisticsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/service")
@SecurityRequirement(name = "Bearer Authentication")
public class StatisticsController {

    private final StatisticsService statisticsService;

    @GetMapping("/statistics")
    @Operation(security = @SecurityRequirement(name = "Bearer Auth"))
    public List<StatisticsDto> statisticsByMonth(@RequestParam("year") int year){
        return statisticsService.customerStatics(year);
    }
}
