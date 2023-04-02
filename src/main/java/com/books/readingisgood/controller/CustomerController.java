package com.books.readingisgood.controller;


import com.books.readingisgood.dto.CustomerLoginRequestDto;
import com.books.readingisgood.dto.CustomerLoginResponseDto;
import com.books.readingisgood.dto.CustomerRegisterRequestDto;
import com.books.readingisgood.dto.CustomerRegisterResponseDto;
import com.books.readingisgood.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping(path = "/register")
    public ResponseEntity<CustomerRegisterResponseDto> registerCustomer(@Validated @RequestBody CustomerRegisterRequestDto requestDto){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(customerService.registerCustomer(requestDto));
    }

    @PostMapping(path = "/login")
    public ResponseEntity<CustomerLoginResponseDto> loginCustomer(@Validated @RequestBody CustomerLoginRequestDto requestDto){
        return ResponseEntity.ok(customerService.loginCustomer(requestDto));
    }
}
