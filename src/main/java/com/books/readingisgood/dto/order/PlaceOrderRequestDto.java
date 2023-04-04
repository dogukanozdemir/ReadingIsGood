package com.books.readingisgood.dto.order;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlaceOrderRequestDto {

    @Id
    @NotNull
    private Long bookId;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String purchaseDate;

}
