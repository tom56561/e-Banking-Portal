package com.kafkaconsumerservice;

import com.kafkaconsumerservice.config.SecurityConfig;
import com.kafkaconsumerservice.controller.AccountController;
import com.kafkaconsumerservice.controller.AuthController;
import com.kafkaconsumerservice.service.TokenService;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;

@WebMvcTest({AccountController.class, AuthController.class})
@Import({SecurityConfig.class, TokenService.class})
public class AuthConrollerTest {
    
}
