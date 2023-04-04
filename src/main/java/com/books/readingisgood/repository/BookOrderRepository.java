package com.books.readingisgood.repository;

import com.books.readingisgood.entity.BookOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.List;

public interface BookOrderRepository extends CrudRepository<BookOrder,Long> {

    List<BookOrder> findAll();

    List<BookOrder> findByPurchaseDateBetween(LocalDate startDate, LocalDate endDate);

    Page<BookOrder> findByCustomerId(Long customerId, Pageable pageable);
}
