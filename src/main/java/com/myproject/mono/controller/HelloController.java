package com.myproject.mono.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/mono")
public class HelloController {
    @GetMapping("/hello")
    public ResponseEntity<String> great()
    {
        return ResponseEntity.ok( "Hello..Welcome to Spring Boot learning !");
    }
    @PostMapping("/hello")
    public ResponseEntity<String> post(@RequestParam String message)
    {
        return ResponseEntity.ok( "Hello.."+message+" Welcome to Spring Boot learning !");
    }
}
