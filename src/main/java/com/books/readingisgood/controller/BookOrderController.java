package com.books.readingisgood.controller;

import com.books.readingisgood.dto.order.PlaceOrderRequestDto;
import com.books.readingisgood.dto.order.OrderDto;
import com.books.readingisgood.service.BookOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/service")
@RequiredArgsConstructor
public class BookOrderController {

    private final BookOrderService bookOrderService;

    @PostMapping(value = "/order")
    public ResponseEntity<OrderDto> orderBook(@Validated @RequestBody PlaceOrderRequestDto requestDto){
        return ResponseEntity.ok(bookOrderService.placeAnOrder(requestDto));
    }

    @GetMapping(value = "/orders")
    public ResponseEntity<List<OrderDto>> getOrders(){
        return ResponseEntity.ok(bookOrderService.getAllOrders());
    }
}
