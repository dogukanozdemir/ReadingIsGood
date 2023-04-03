package com.books.readingisgood.dto.book;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
public class CreateBookRequestDto {

    @NotNull
    private String title;

    private String author;

    @NotNull
    @PositiveOrZero
    private Integer quantityInStock;

    @Positive
    private Integer totalPages;
}
