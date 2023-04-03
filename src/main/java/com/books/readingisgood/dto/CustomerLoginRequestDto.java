package com.books.readingisgood.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerLoginRequestDto {

    @NotNull(message = "First name must not be empty")
    private String email;

    @NotNull(message = "Last name must not be empty")
    private String password;
}
