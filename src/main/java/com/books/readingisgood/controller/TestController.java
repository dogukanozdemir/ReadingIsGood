package com.books.readingisgood.controller;

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

    @GetMapping(path = "/customers")
    public ResponseEntity<String> authendpoint(){
        log.info("hereeeeeeee");
        return ResponseEntity.ok("Nice");
    }
}
