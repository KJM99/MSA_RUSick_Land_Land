package com.example.land;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class LandApplication {

	public static void main(String[] args) {
		SpringApplication.run(LandApplication.class, args);
	}

}
