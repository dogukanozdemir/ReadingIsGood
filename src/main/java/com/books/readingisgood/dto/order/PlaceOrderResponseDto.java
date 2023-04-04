package com.books.readingisgood.dto.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlaceOrderResponseDto {

    private Long id;

    private LocalDate purchaseDate;

    private Long bookId;

    private Long customerId;
}
