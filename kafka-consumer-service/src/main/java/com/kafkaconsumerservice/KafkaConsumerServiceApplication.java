package com.kafkaconsumerservice;

import com.kafkaconsumerservice.config.RsaKeyProperties;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.security.web.context.support.SecurityWebApplicationContextUtils;

@EnableConfigurationProperties(RsaKeyProperties.class)
@SpringBootApplication
@OpenAPIDefinition
public class KafkaConsumerServiceApplication {

	public static void main(String[] args) {
//		ConfigurableApplicationContext run = SpringApplication.run(SecurityWebApplicationContextUtils.class, args);
		SpringApplication.run(KafkaConsumerServiceApplication.class, args);
		System.out.println(111);

	}

}
