package com.books.readingisgood.dto.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderByCustomerPageDto {


    private List<OrderDto> orders;
    private int pageNo;
    private int pageSize;
    private int totalPages;
    private int numberOfElements;

}
