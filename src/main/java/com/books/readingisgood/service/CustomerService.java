package com.books.readingisgood.service;

import com.books.readingisgood.authentication.jwt.JwtService;
import com.books.readingisgood.dto.CustomerLoginRequestDto;
import com.books.readingisgood.dto.CustomerLoginResponseDto;
import com.books.readingisgood.dto.CustomerRegisterRequestDto;
import com.books.readingisgood.dto.CustomerRegisterResponseDto;
import com.books.readingisgood.entity.Customer;
import com.books.readingisgood.enums.Role;
import com.books.readingisgood.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public CustomerRegisterResponseDto registerCustomer(CustomerRegisterRequestDto registerRequestDto){
        if(customerRepository.findByEmail(registerRequestDto.getEmail()).isPresent()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    String.format("Customer with '%s' email already exists", registerRequestDto.getEmail()));
        }

        Customer customer = Customer.builder()
                .username(registerRequestDto.getUsername())
                .email(registerRequestDto.getEmail())
                .password(passwordEncoder.encode(registerRequestDto.getPassword()))
                .role(Role.USER)
                .build();
        customerRepository.save(customer);

        return CustomerRegisterResponseDto.builder()
                .message(String.format(
                        "%s registered successfully, please login using your credentials to generate a token.",
                        registerRequestDto.getEmail()
                )).build();
    }

    public CustomerLoginResponseDto loginCustomer(CustomerLoginRequestDto loginRequestDto){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequestDto.getEmail(), loginRequestDto.getPassword()));

        if (authentication.isAuthenticated()) {
            String token = jwtService.generateToken(loginRequestDto.getEmail());
            return CustomerLoginResponseDto.builder()
                    .token(token)
                    .build();
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid username or password entered");
        }
    }

}
