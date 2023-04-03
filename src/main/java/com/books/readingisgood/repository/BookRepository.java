package com.books.readingisgood.repository;

import com.books.readingisgood.entity.Book;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface BookRepository extends CrudRepository<Book,Long> {

}
