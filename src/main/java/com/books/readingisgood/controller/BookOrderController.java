package com.books.readingisgood.controller;

import com.books.readingisgood.dto.order.PlaceOrderRequestDto;
import com.books.readingisgood.dto.order.PlaceOrderResponseDto;
import com.books.readingisgood.service.BookOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/service")
@RequiredArgsConstructor
public class BookOrderController {

    private final BookOrderService bookOrderService;

    @PostMapping(value = "/order")
    public ResponseEntity<PlaceOrderResponseDto> orderBook(@Validated @RequestBody PlaceOrderRequestDto requestDto){
        return ResponseEntity.ok(bookOrderService.placeAnOrder(requestDto));
    }
}
