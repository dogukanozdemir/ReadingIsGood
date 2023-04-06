package com.books.readingisgood.logging;

import com.books.readingisgood.authentication.util.AuthUtil;
import com.books.readingisgood.entity.Book;
import com.books.readingisgood.entity.BookOrder;
import com.books.readingisgood.entity.Customer;
import jakarta.persistence.PostPersist;
import jakarta.persistence.PostUpdate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

@Slf4j
public class BookOrderLogListener {

    @Autowired
    private AuthUtil authUtil;

    @PostPersist
    public void orderPlaced(BookOrder bookOrder) {
        Customer currentCustomer = authUtil.getCurrentCustomer();
        log.info(String.format("%s placed an order for %d book(s) and paid %.2f$ in total - %s",
                currentCustomer.getUsername(),
                bookOrder.getPurchasedBookCount(),
                bookOrder.getPurchasedAmount(),
                LocalDateTime.now()));
    }
}
