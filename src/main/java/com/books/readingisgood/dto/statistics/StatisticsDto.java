package com.books.readingisgood.dto.statistics;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StatisticsDto {

    private String month;
    private int totalOrderCount;
    private int totalBookCount;
    private double totalPurchasedAmount;
}
