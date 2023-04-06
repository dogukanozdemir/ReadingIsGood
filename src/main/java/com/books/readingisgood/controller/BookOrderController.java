package com.books.readingisgood.controller;

import com.books.readingisgood.dto.order.PlaceOrderRequestDto;
import com.books.readingisgood.dto.order.OrderDto;
import com.books.readingisgood.service.BookOrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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
    @Operation(tags = "Order Service",security = @SecurityRequirement(name = "Bearer Auth"))
    public ResponseEntity<OrderDto> orderBook(@Validated @RequestBody PlaceOrderRequestDto requestDto){
        return ResponseEntity.ok(bookOrderService.placeAnOrder(requestDto));
    }

    @GetMapping(value = "/orders")
    @Operation(tags = "Order Service",security = @SecurityRequirement(name = "Bearer Auth"))
    public ResponseEntity<List<OrderDto>> getOrders(){
        return ResponseEntity.ok(bookOrderService.getAllOrders());
    }

    @GetMapping(value = "/orders/dateInterval")
    @Operation(tags = "Order Service",security = @SecurityRequirement(name = "Bearer Auth"))
    public ResponseEntity<List<OrderDto>> getOrdersByDateInterval(@RequestParam("start") String startDate,
                                                                  @RequestParam("end") String endDate){

        return ResponseEntity.ok(bookOrderService.getAllOrdersBetween(startDate,endDate));
    }
}
