package com.books.readingisgood.authentication;

import com.books.readingisgood.authentication.dto.AuthenticatedCustomer;
import com.books.readingisgood.entity.Customer;
import com.books.readingisgood.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CustomerDetailsService implements UserDetailsService {

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Customer customer = customerRepository.findByEmail(email)
                .orElseThrow(
                        () -> new UsernameNotFoundException("Invalid email: " + email)
                );

        return new AuthenticatedCustomer(
                customer.getEmail(),
                customer.getPassword(),
                List.of((GrantedAuthority) () -> customer.getRole().name()),
                customer
        );
    }
}
