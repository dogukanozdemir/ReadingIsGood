package com.books.readingisgood.controller;

import com.books.readingisgood.dto.customer.CustomerDto;
import com.books.readingisgood.dto.customer.UpdateCustomerRequestDto;
import com.books.readingisgood.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/service")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @PutMapping("/customer")
    public ResponseEntity<CustomerDto> updateCustomer(@Validated @RequestBody UpdateCustomerRequestDto requestDto){
        return ResponseEntity.ok(customerService.updateCustomer(requestDto));
    }
}
