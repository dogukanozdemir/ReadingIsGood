package com.books.readingisgood.dto.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDto {

    private Long id;

    private Long bookId;

    private String bookName;

    private Double bookPrice;

    private LocalDate purchaseDate;

    private Long customerId;
}
