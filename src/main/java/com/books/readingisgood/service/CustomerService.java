package com.books.readingisgood.service;

import com.books.readingisgood.authentication.util.AuthUtil;

import com.books.readingisgood.dto.customer.CustomerDto;
import com.books.readingisgood.dto.customer.UpdateCustomerRequestDto;
import com.books.readingisgood.entity.Customer;
import com.books.readingisgood.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    private final AuthUtil authUtil;

    private final PasswordEncoder passwordEncoder;

    public CustomerDto updateCustomer(UpdateCustomerRequestDto requestDto){
        Customer loggedInCustomer = authUtil.getCurrentCustomer();
        if(!loggedInCustomer.getEmail().equals(requestDto.getEmail())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,
                    "You are not authorized to update this customer. Please ensure you are logged in as the correct customer before attempting to update.");
        }

        loggedInCustomer.setEmail(requestDto.getEmail());
        loggedInCustomer.setPassword(passwordEncoder.encode(requestDto.getPassword()));
        loggedInCustomer.setUsername(requestDto.getUsername());
        customerRepository.save(loggedInCustomer);

        return CustomerDto.builder()
                    .id(loggedInCustomer.getId())
                    .username(loggedInCustomer.getUsername())
                    .email(loggedInCustomer.getEmail())
                    .build();
    }
}
