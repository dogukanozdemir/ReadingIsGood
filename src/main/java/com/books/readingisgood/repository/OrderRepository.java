package com.books.readingisgood.repository;

import com.books.readingisgood.entity.BookOrder;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OrderRepository extends CrudRepository<BookOrder,Long> {

    List<BookOrder> findAll();
}
