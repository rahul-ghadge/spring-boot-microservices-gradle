package com.arya.microservices.country.currency.webclient.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;

@RestController
public class CountryCurrencyController {

    private static final Logger logger = LoggerFactory.getLogger(CountryCurrencyController.class);
    private static final String COUNTRY_CURRENCY_API = "https://restcountries.eu/rest/v2/currency/";


    @Autowired
    private Environment environment;

    @Autowired
    private WebClient.Builder webClientBuilder;


    @SuppressWarnings("unchecked")
	@GetMapping(value = "/code/{code}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Map<String, Object>> getCountryData(@PathVariable String code) {

        logger.info("Fetching data from currency code : {}", code);
        logger.info("Server port :: {}", environment.getProperty("local.server.port"));
        logger.info("Country Currency API :: {}{}", COUNTRY_CURRENCY_API, code);
        
        
        Object countryDetails = webClientBuilder.build()
                .get()
                .uri(COUNTRY_CURRENCY_API + code)
                .retrieve()
                .bodyToMono(Object.class)
                .block();
        
        logger.info("Country Currency API Response :: {}", countryDetails);
        
        return (List<Map<String, Object>>) countryDetails;
    }
}