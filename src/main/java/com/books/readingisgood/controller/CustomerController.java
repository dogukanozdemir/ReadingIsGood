package com.books.readingisgood.controller;

import com.books.readingisgood.dto.customer.CustomerDto;
import com.books.readingisgood.dto.customer.UpdateCustomerRequestDto;
import com.books.readingisgood.dto.order.OrderByCustomerPageDto;
import com.books.readingisgood.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/service")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping("/customer/orders")
    public ResponseEntity<OrderByCustomerPageDto> updateCustomer(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize){

        return ResponseEntity.ok(customerService.getCustomerOrders(pageNo,pageSize));
    }
}
