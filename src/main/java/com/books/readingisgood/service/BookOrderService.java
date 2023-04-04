package com.books.readingisgood.service;


import com.books.readingisgood.authentication.util.AuthUtil;
import com.books.readingisgood.dto.order.PlaceOrderRequestDto;
import com.books.readingisgood.dto.order.OrderDto;
import com.books.readingisgood.entity.Book;
import com.books.readingisgood.entity.BookOrder;
import com.books.readingisgood.entity.Customer;
import com.books.readingisgood.repository.BookRepository;
import com.books.readingisgood.repository.OrderRepository;
import com.books.readingisgood.validation.DateValidator;
import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookOrderService {
    private final BookRepository bookRepository;
    private final OrderRepository orderRepository;
    private final BookService bookService;
    private final EntityManager entityManager;
    private final AuthUtil authUtil;

    @Transactional
    public OrderDto placeAnOrder(PlaceOrderRequestDto requestDto){
        Customer currentCustomer = authUtil.getCurrentCustomer();
        Long bookId = requestDto.getBookId();
        Book book = entityManager.find(Book.class, bookId, LockModeType.PESSIMISTIC_WRITE);
        if(book == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    String.format("Book with id %d does not exist",bookId));
        }

        int currentStockQuantity = book.getQuantityInStock();
        if(currentStockQuantity < 1){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    String.format("Book with %d isn't in stock, Please try again in a few days while we restock",bookId));
        }

        LocalDate purchaseDate = DateValidator.validate(requestDto.getPurchaseDate());
        if(purchaseDate == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Invalid date, please make sure the date provided matches the pattern 'yyyy-MM-dd'");
        }


        bookService.updateBookStockByEntity(book,currentStockQuantity - 1);

        BookOrder bookOrder = BookOrder.builder()
                .bookId(bookId)
                .bookName(book.getTitle())
                .bookPrice(book.getPrice())
                .purchaseDate(purchaseDate)
                .customerId(currentCustomer.getId())
                .build();
        orderRepository.save(bookOrder);

        return OrderDto.builder()
                .id(bookOrder.getId())
                .bookId(book.getId())
                .bookName(book.getTitle())
                .bookPrice(bookOrder.getBookPrice())
                .purchaseDate(bookOrder.getPurchaseDate())
                .customerId(bookOrder.getCustomerId())
                .build();
    }

    public List<OrderDto> getAllOrders(){
        return orderRepository.findAll().stream()
                .map(
                        bookOrder -> OrderDto.builder()
                                .id(bookOrder.getId())
                                .bookId(bookOrder.getBookId())
                                .bookName(bookOrder.getBookName())
                                .bookPrice(bookOrder.getBookPrice())
                                .purchaseDate(bookOrder.getPurchaseDate())
                                .customerId(bookOrder.getCustomerId())
                                .build()
                ).toList();
    }

    public List<OrderDto> getAllOrdersBetween(String start, String end){
        LocalDate startDate = DateValidator.validate(start);
        LocalDate endDate = DateValidator.validate(end);
        if(startDate == null || endDate == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Invalid date, please make sure the date provided matches the pattern 'yyyy-MM-dd'");
        }
        return orderRepository.findByPurchaseDateBetween(startDate,endDate)
                .stream()
                .map(
                        bookOrder -> OrderDto.builder()
                                .id(bookOrder.getId())
                                .bookId(bookOrder.getBookId())
                                .bookName(bookOrder.getBookName())
                                .bookPrice(bookOrder.getBookPrice())
                                .purchaseDate(bookOrder.getPurchaseDate())
                                .customerId(bookOrder.getCustomerId())
                                .build()
                ).toList();
    }
}
