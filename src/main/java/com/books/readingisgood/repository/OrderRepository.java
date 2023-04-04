package com.books.readingisgood.repository;

import com.books.readingisgood.entity.BookOrder;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<BookOrder,Long> {
}
