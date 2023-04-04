package com.books.readingisgood.service;

import com.books.readingisgood.authentication.util.AuthUtil;

import com.books.readingisgood.dto.customer.CustomerDto;
import com.books.readingisgood.dto.customer.UpdateCustomerRequestDto;
import com.books.readingisgood.dto.order.OrderByCustomerPageDto;
import com.books.readingisgood.dto.order.OrderDto;
import com.books.readingisgood.entity.Customer;
import com.books.readingisgood.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final AuthUtil authUtil;
    private final BookOrderService bookOrderService;

    public OrderByCustomerPageDto getCustomerOrders(int pageNo, int pageSize){
        Customer currentCustomer = authUtil.getCurrentCustomer();
        return bookOrderService.getOrdersByCustomer(currentCustomer.getId(),pageNo,pageSize);

    }
}
