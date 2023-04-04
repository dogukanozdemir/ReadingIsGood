package com.books.readingisgood.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class BookOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long bookId;

    private String bookName;

    private Double paidAmount;

    private Integer purchasedAmount;

    @DateTimeFormat(pattern="yyyy-MM-dd")
    private LocalDate purchaseDate;

    private Long customerId;

}
