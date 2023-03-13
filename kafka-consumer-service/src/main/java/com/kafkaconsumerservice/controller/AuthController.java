package com.kafkaconsumerservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @GetMapping
    public String home(Principal principal){
        return "Hello, " + principal.getName();
    }
    @GetMapping("test")
    public String test(){
        return "hi";
    }
}
