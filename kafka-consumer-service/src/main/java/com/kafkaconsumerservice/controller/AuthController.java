package com.kafkaconsumerservice.controller;

import com.kafkaconsumerservice.service.TokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
