package com.books.readingisgood.dto.book;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateBookRequestDto {

    @NotNull
    private String title;

    private String author;

    @NotNull
    @Positive
    private Double price;

    @NotNull
    @PositiveOrZero
    private Integer quantityInStock;

    @Positive
    private Integer totalPages;
}
