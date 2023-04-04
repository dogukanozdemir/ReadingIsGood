package com.books.readingisgood.repository;

import com.books.readingisgood.entity.BookOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface BookOrderRepository extends CrudRepository<BookOrder,Long> {

    List<BookOrder> findAll();

    List<BookOrder> findByPurchaseDateBetween(LocalDate startDate, LocalDate endDate);

    Page<BookOrder> findByCustomerId(Long customerId, Pageable pageable);

    @Query("SELECT bo FROM BookOrder bo WHERE bo.customerId = :customerId AND YEAR(bo.purchaseDate) = :year ORDER BY bo.purchaseDate ASC")
    List<BookOrder> findByCustomerIdAndYear(@Param("customerId") Long customerId, @Param("year") int year);
}
