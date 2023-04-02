package com.books.readingisgood.authentication.dto;

import com.books.readingisgood.entity.Customer;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Getter
public class AuthenticatedCustomer extends User {

    private final Customer customer;

    public AuthenticatedCustomer(String username, String password, Collection<? extends GrantedAuthority> authorities, Customer customer) {
        super(username, password, authorities);
        this.customer = customer;
    }
}
