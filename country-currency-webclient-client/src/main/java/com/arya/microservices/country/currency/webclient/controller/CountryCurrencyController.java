package com.arya.microservices.country.currency.webclient.controller;


import com.arya.microservices.common.model.CountryDetails;
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

@RestController
public class CountryCurrencyController {

    private static final Logger logger = LoggerFactory.getLogger(CountryCurrencyController.class);
    private static final String COUNTRY_CURRENCY_API = "https://restcountries.eu/rest/v2/currency/";


    @Autowired
    private Environment environment;

    @Autowired
    private WebClient.Builder webClientBuilder;


    @GetMapping(value = "/code/{code}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CountryDetails> getCountryData(@PathVariable String code) {

        logger.info("Fetching data from currency code : "+ code);
        logger.info("\n\n*********************************************");
        logger.info("Server port :: " + environment.getProperty("local.server.port"));
        logger.info("*********************************************\n");

        logger.info("Country Currency API :: " + COUNTRY_CURRENCY_API + code);

        return (List<CountryDetails>) webClientBuilder.build()
                .get()
                .uri(COUNTRY_CURRENCY_API + code)
                .retrieve()
                .bodyToMono(Object.class)
                .block();


//        CountryDetails countryDetails = new CountryDetails();
//        ResponseEntity<CountryDetails> countryDetailsResponse = getCountryDetails(country);
//
//        if (countryDetailsResponse.getStatusCode() == HttpStatus.OK) {
//            if (null != countryDetailsResponse.getBody()
//                    && !CollectionUtils.isEmpty(countryDetailsResponse.getBody().getCurrencies())) {
//                countryDetailsResponse = getCountryCurrencyDetails(countryDetailsResponse.getBody().getCurrencies().get(0).getCode());
//
//                if (countryDetailsResponse.getStatusCode() == HttpStatus.OK)
//                    countryDetails = countryDetailsResponse.getBody();
//                else
//                    countryDetails.setMessage("Something wrong happened with country details API..!");
//            } else {
//                countryDetails.setMessage("Something wrong happened with country currency API..!");
//            }
//        }
//        return ResponseEntity.ok(countryDetails);
    }
//
//    private ResponseEntity<CountryDetails> getCountryDetails(String country) {
//        logger.info("Country Details API :: ", COUNTRY_DETAILS_API + country);
//        return restTemplate.getForEntity(COUNTRY_DETAILS_API + country, CountryDetails.class);
//    }
//
//    private ResponseEntity<CountryDetails> getCountryCurrencyDetails(String code) {
//        logger.info("Country Currency API :: ", COUNTRY_CURRENCY_API + code);
//        return restTemplate.getForEntity(COUNTRY_CURRENCY_API + code, CountryDetails.class);
//    }
}