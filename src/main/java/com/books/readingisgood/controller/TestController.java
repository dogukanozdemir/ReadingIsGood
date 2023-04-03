package com.books.readingisgood.controller;

import com.books.readingisgood.authentication.util.AuthUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/service")
@RequiredArgsConstructor
@Slf4j
public class TestController {

    private final AuthUtil authUtil;

    @GetMapping(path = "/customers")
    public ResponseEntity<String> authendpoint(){

        return ResponseEntity.ok(authUtil.getCurrentCustomer().getUsername());
    }
}
