package com.camel.camel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = "com.camel.camel.beans")
public class CamelProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(CamelProjectApplication.class, args);
	}

}
