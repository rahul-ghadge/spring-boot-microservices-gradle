package com.arya.microservices.country.currency.webclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class CountryCurrencyWebClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(CountryCurrencyWebClientApplication.class, args);
	}

}
