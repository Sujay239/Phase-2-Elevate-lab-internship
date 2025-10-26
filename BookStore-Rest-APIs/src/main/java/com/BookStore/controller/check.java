package com.BookStore.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class check {

    @GetMapping("/health")
    public String HealthCheck(){
        return "hello";
    }
}
