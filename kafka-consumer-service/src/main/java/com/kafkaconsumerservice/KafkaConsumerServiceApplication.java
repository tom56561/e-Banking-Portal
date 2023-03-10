package com.kafkaconsumerservice;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition
public class KafkaConsumerServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(KafkaConsumerServiceApplication.class, args);
	}

}
