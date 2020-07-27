package com.arya.microservices.country.details.restful.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@RestController
public class CountryDetailsController {

    private static final Logger logger = LoggerFactory.getLogger(CountryDetailsController.class);
    private static final String COUNTRY_DETAILS_API = "https://restcountries.eu/rest/v2/name/";


    @Autowired
    private Environment environment;

    @Autowired
    private RestTemplate restTemplate;
    
    List<Map<String, Object>> countryDetails; 


    @SuppressWarnings("unchecked")
	@GetMapping(value = "/name/{country}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Map<String, Object>> getCountryData(@PathVariable String country) {

        logger.info("Fetching data from currency code : {}", country);
        logger.info("Server port :: {}", environment.getProperty("local.server.port"));
        logger.info("Country Currency API :: {}{}", COUNTRY_DETAILS_API, country);

        if ("favicon.ico".equals(country)) {
			return countryDetails;
		} else {
			countryDetails = restTemplate.getForObject(COUNTRY_DETAILS_API + country, List.class);
		}
        logger.info("Country Details API Response :: {}", countryDetails);
        
        return countryDetails;

    }
}