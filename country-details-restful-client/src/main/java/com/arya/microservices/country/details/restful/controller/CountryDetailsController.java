package com.arya.microservices.country.details.restful.controller;


import com.arya.microservices.common.model.CountryDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@RestController
public class CountryDetailsController {

    private static final Logger logger = LoggerFactory.getLogger(CountryDetailsController.class);
    private static final String COUNTRY_DETAILS_API = "https://restcountries.eu/rest/v2/name/";


    @Autowired
    private Environment environment;

    @Autowired
    private RestTemplate restTemplate;


    @SuppressWarnings("unchecked")
	@GetMapping(value = "/name/{country}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CountryDetails> getCountryData(@PathVariable String country) {

        logger.info("Fetching data from country : " + country);
        logger.info("\n\n*********************************************");
        logger.info("Server port :: " + environment.getProperty("local.server.port"));
        logger.info("*********************************************\n");

        logger.info("Country Details API :: " + COUNTRY_DETAILS_API + country);
        
        List<CountryDetails> countryDetails = restTemplate.getForObject(COUNTRY_DETAILS_API + country, List.class);
        logger.info("Country Details API Response :: " + countryDetails);
        
        return countryDetails;

    }
}