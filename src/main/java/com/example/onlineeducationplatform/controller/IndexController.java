package com.example.onlineeducationplatform.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {
    @GetMapping("/")
    public String index() {
        return "Welcome to OnlineEducationPlatform API. Use /api/users/register to register, /api/users/login to login.";
    }
}