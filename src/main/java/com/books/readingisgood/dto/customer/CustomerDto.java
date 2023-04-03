package com.books.readingisgood.dto.customer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
public class CustomerDto {
    private Long id;
    private String email;
    private String username;
}
