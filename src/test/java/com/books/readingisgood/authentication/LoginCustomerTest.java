package com.books.readingisgood.authentication;

import com.books.readingisgood.authentication.jwt.JwtService;
import com.books.readingisgood.dto.authentication.AuthenticationRequestDto;
import com.books.readingisgood.dto.authentication.AuthenticationResponseDto;
import com.books.readingisgood.service.AuthenticationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@MockitoSettings(strictness = Strictness.LENIENT)
public class LoginCustomerTest {

    @InjectMocks
    private AuthenticationService authenticationService;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private Authentication authentication;

    @Mock
    private JwtService jwtService;

    private AuthenticationRequestDto loginRequestDto;

    @BeforeEach
    void setup() {
        loginRequestDto = AuthenticationRequestDto.builder()
                .email("test@example.com")
                .password("testPassword")
                .build();
    }

    @Test
    void testLoginCustomer_Successful() {
        AuthenticationRequestDto requestDto = AuthenticationRequestDto.builder()
                    .email("test@test.com")
                    .password("password")
                    .build();
        when(authentication.isAuthenticated()).thenReturn(true);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
        when(jwtService.generateToken("test@test.com")).thenReturn("token");

        AuthenticationResponseDto responseDto = authenticationService.loginCustomer(requestDto);
        assertNotNull(responseDto.getToken());
        assertEquals("token", responseDto.getToken());
    }

    @Test
    void loginCustomer_WithInvalidCredentials_ThrowsUnauthorizedException() {
        AuthenticationRequestDto requestDto = AuthenticationRequestDto.builder()
                .email("test@test.com")
                .password("wrong_password")
                .build();
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid username or password entered"));

        assertThrows(ResponseStatusException.class, () -> authenticationService.loginCustomer(requestDto));
    }
}
