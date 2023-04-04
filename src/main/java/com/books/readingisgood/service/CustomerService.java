package com.books.readingisgood.service;

import com.books.readingisgood.authentication.util.AuthUtil;

import com.books.readingisgood.dto.order.OrderByCustomerPageDto;
import com.books.readingisgood.entity.Customer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
