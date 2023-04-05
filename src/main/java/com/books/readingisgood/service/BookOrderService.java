package com.books.readingisgood.service;


import com.books.readingisgood.authentication.util.AuthUtil;
import com.books.readingisgood.dto.order.OrderByCustomerPageDto;
import com.books.readingisgood.dto.order.PlaceOrderRequestDto;
import com.books.readingisgood.dto.order.OrderDto;
import com.books.readingisgood.entity.Book;
import com.books.readingisgood.entity.BookOrder;
import com.books.readingisgood.entity.Customer;
import com.books.readingisgood.repository.BookRepository;
import com.books.readingisgood.repository.BookOrderRepository;
import com.books.readingisgood.validation.DateValidator;
import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookOrderService {
    private final BookOrderRepository bookOrderRepository;
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
        if(currentStockQuantity < requestDto.getAmount()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    String.format("You ordered to buy %d but there is only %d left from the book you requested %d",
                            requestDto.getAmount(),
                            currentStockQuantity,
                            bookId));
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
                .purchasedAmount(requestDto.getAmount())
                .paidAmount(requestDto.getAmount() * book.getPrice())
                .purchaseDate(purchaseDate)
                .customerId(currentCustomer.getId())
                .build();
        bookOrderRepository.save(bookOrder);

        return OrderDto.builder()
                .id(bookOrder.getId())
                .bookId(book.getId())
                .bookName(book.getTitle())
                .paidAmount(bookOrder.getPaidAmount())
                .purchasedAmount(bookOrder.getPurchasedAmount())
                .purchaseDate(bookOrder.getPurchaseDate())
                .customerId(bookOrder.getCustomerId())
                .build();
    }

    public List<OrderDto> getAllOrders(){
        return bookOrderRepository.findAll().stream()
                .map(
                        bookOrder -> OrderDto.builder()
                                .id(bookOrder.getId())
                                .bookId(bookOrder.getBookId())
                                .bookName(bookOrder.getBookName())
                                .purchasedAmount(bookOrder.getPurchasedAmount())
                                .paidAmount(bookOrder.getPaidAmount())
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
        return bookOrderRepository.findByPurchaseDateBetween(startDate,endDate)
                .stream()
                .map(
                        bookOrder -> OrderDto.builder()
                                .id(bookOrder.getId())
                                .bookId(bookOrder.getBookId())
                                .bookName(bookOrder.getBookName())
                                .purchasedAmount(bookOrder.getPurchasedAmount())
                                .paidAmount(bookOrder.getPaidAmount())
                                .purchaseDate(bookOrder.getPurchaseDate())
                                .customerId(bookOrder.getCustomerId())
                                .build()
                ).toList();
    }

    public OrderByCustomerPageDto getOrdersByCustomer(Long customerId, int pageNo, int pageSize){
        Pageable pageable = PageRequest.of(pageNo, pageSize,Sort.by("purchaseDate").descending());
        Page<BookOrder> orders = bookOrderRepository.findByCustomerId(customerId,pageable);
        List<OrderDto> listOfOrders =
                orders.getContent().stream()
                    .map(
                            bookOrder -> OrderDto.builder()
                                    .id(bookOrder.getId())
                                    .bookId(bookOrder.getBookId())
                                    .bookName(bookOrder.getBookName())
                                    .purchasedAmount(bookOrder.getPurchasedAmount())
                                    .paidAmount(bookOrder.getPaidAmount())
                                    .purchaseDate(bookOrder.getPurchaseDate())
                                    .customerId(bookOrder.getCustomerId())
                                    .build()
                    ).toList();

        return OrderByCustomerPageDto.builder()
                .pageNo(orders.getNumber())
                .pageSize(orders.getSize())
                .numberOfElements(orders.getNumberOfElements())
                .totalPages(orders.getTotalPages())
                .orders(listOfOrders)
                .build();
    }
}
