package com.books.readingisgood.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerLoginRequestDto {

    @NotBlank
    private String email;

    @NotBlank
    private String password;
}
