package com.books.readingisgood.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerLoginRequestDto {

    @NotNull
    private String email;

    @NotNull
    private String password;
}
