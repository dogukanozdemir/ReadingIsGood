package com.books.readingisgood.authentication;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.Optional;

import com.books.readingisgood.dto.authentication.RegisterRequestDto;
import com.books.readingisgood.dto.authentication.RegisterResponseDto;
import com.books.readingisgood.entity.Customer;
import com.books.readingisgood.repository.CustomerRepository;
import com.books.readingisgood.service.AuthenticationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.server.ResponseStatusException;

@MockitoSettings(strictness = Strictness.LENIENT)
class RegisterCustomerTest {

    @InjectMocks
    private AuthenticationService authenticationService;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    private RegisterRequestDto registerRequestDto;

    @BeforeEach
    void setup(){
         registerRequestDto = RegisterRequestDto.builder()
                         .email("test@example.com")
                         .username("testUser")
                         .password("testPassword")
                         .build();

    }

    @Test
    void registerCustomerSuccess() {

        when(customerRepository.findByEmail("test@example.com")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("testPassword")).thenReturn("encodedPassword");

        RegisterResponseDto response = authenticationService.registerCustomer(registerRequestDto);

        assertNotNull(response);
        assertEquals(String.format("%s registered successfully, please login using your credentials to generate a token.",
                registerRequestDto.getEmail()), response.getMessage());
    }

    @Test
    void registerCustomer_WithDuplicateEmail_ThrowsResponseStatusException() {

        Customer customer = new Customer();
        when(customerRepository.findByEmail("test@example.com")).thenReturn(Optional.of(customer));

        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> authenticationService.registerCustomer(registerRequestDto));
        assertEquals(String.format("Customer with '%s' email already exists", registerRequestDto.getEmail()),
                exception.getReason());
    }
}
