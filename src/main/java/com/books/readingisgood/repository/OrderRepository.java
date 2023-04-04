package com.books.readingisgood.repository;

import com.books.readingisgood.entity.BookOrder;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.List;

public interface OrderRepository extends CrudRepository<BookOrder,Long> {

    List<BookOrder> findAll();

    List<BookOrder> findByPurchaseDateBetween(LocalDate startDate, LocalDate endDate);
}
