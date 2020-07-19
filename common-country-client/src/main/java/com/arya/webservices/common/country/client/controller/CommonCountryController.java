package com.arya.webservices.common.country.client.controller;


import com.arya.microservices.common.model.CountryDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class CommonCountryController {

    private static final Logger logger = LoggerFactory.getLogger(CommonCountryController.class);

    private static final String COUNTRY_DETAILS_API = "http://country-details-restful-client/name/";
    private static final String COUNTRY_CURRENCY_API = "http://country-currency-webclient-client/code/";


    @Autowired
    private Environment environment;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private WebClient.Builder webClientBuilder;


    @GetMapping({"/", "/{country}"})
    public ResponseEntity<List<CountryDetails>> getCountryData(@PathVariable(required = false) String country) {

        if (StringUtils.isEmpty(country))
            country = "bharat";

        System.out.println("Getting country details for " + country);

        // Call country service by hardcoding API endpoint
        List<CountryDetails> countryDetails = getCountryDetailsUsingRestTemplate(country);
        List<CountryDetails> countryDetailsResponse = new ArrayList<>();


            if (null != countryDetails && !CollectionUtils.isEmpty(countryDetails)) {
//                countryDetails.stream()
//                        .map(c ->
//                                getCountryDetailsUsingWebClient(c.getCurrencies().get(0).getCode()))
//                        .collect(Collectors.toList());

                countryDetails.forEach(c -> {
                    countryDetailsResponse.addAll(getCountryDetailsUsingWebClient(c.getCurrencies().get(0).getCode()));
                });
            }
//                    !CollectionUtils.isEmpty(countryDetails.getCurrencies())) {
//                countryDetails = getCountryDetailsUsingWebClient(countryDetails.getCurrencies().get(0).getCode());

//                if (countryDetailsResponse.getStatusCode() == HttpStatus.OK)
//                    countryDetails = countryDetailsResponse.getBody();
//                else
//                    countryDetails.setMessage("Something wrong happened with country details API..!");
//            } else {
//                countryDetails.setMessage("Something wrong happened with country currency API..!");
//            }

//        Call country service by by getting API endpoint from EurekaClient
//        InstanceInfo instanceInfo = client.getNextServerFromEureka("country-client", false);
//        String apiUrl = instanceInfo.getHomePageUrl();
//        Object response = getServiceUsingRestTemplate(apiUrl, country);

        System.out.println("Response Received from country client " + countryDetailsResponse);

        return ResponseEntity.ok(countryDetailsResponse);
        }

    private List<CountryDetails> getCountryDetailsUsingWebClient(String code) {
        return (List<CountryDetails>) webClientBuilder.build().get()
                .uri(COUNTRY_CURRENCY_API + code)
                .retrieve()
                .bodyToMono(Object.class)
                .block();
    }


    private List<CountryDetails> getCountryDetailsUsingRestTemplate(String country) {
        return (List<CountryDetails>) restTemplate
                .getForObject(COUNTRY_DETAILS_API + country, Object.class);
//                .exchange(url, HttpMethod.GET, null,
//                        new ParameterizedTypeReference<String>() {}, country)
//                .getBody();
    }
}