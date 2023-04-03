package com.books.readingisgood.dto.book;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class BookDto {

    private Long id;
    private String title;
    private String author;
    private Integer quantityInStock;
    private Integer totalPages;


}
