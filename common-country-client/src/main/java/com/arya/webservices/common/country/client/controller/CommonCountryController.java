package com.arya.webservices.common.country.client.controller;


import com.arya.microservices.common.model.CountryDetails;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RefreshScope
public class CommonCountryController {

    private static final Logger logger = LoggerFactory.getLogger(CommonCountryController.class);

    private static final String COUNTRY_DETAILS_API = "http://country-details-restful-client/name/";
    private static final String COUNTRY_CURRENCY_API = "http://country-currency-webclient-client/code/";


    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private WebClient.Builder webClientBuilder;


    
    @GetMapping(value = {"/", "/{country}"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CountryDetails>> getCountryData(@PathVariable(required = false) String country) {

        if (StringUtils.isEmpty(country))
            country = "bharat";

        logger.info("Getting country details for " + country);

        // Call country service by hardcoding API endpoint
        List<CountryDetails> countryDetails = getCountryDetailsUsingRestTemplate(country);
        List<CountryDetails> countryDetailsResponse = new ArrayList<>();


            if (null != countryDetails && !CollectionUtils.isEmpty(countryDetails)) {
                countryDetails.stream()
                        .map(c ->
                                getCountryDetailsUsingWebClient(
                                		c.getCurrencies().get(0).getCode())
                                				.stream()
                                				.map(cd -> countryDetailsResponse.add(cd)));
//                        .collect(Collectors.toList());

//                countryDetails.forEach(c -> {
//                    countryDetailsResponse.addAll(getCountryDetailsUsingWebClient(c.getCurrencies().get(0).getCode()));
//                });
            }
        logger.info("Response Received from country client " + countryDetailsResponse);

        return ResponseEntity.ok(countryDetailsResponse);
    }

    
    
    @SuppressWarnings("unchecked")
//     Circuit breaker
    @HystrixCommand(fallbackMethod = "getCountryDetailsFallback",
          ignoreExceptions = { RuntimeException.class },
          commandProperties = {
                  @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000")
    })
	private List<CountryDetails> getCountryDetailsUsingWebClient(String code) {
        return webClientBuilder.build().get()
                .uri(COUNTRY_CURRENCY_API + code)
                .retrieve()
                .bodyToMono(List.class)
                .block();
    }


    
    @SuppressWarnings("unchecked")
//  Circuit breaker
	@HystrixCommand(fallbackMethod = "getCountryDetailsFallback", 
	ignoreExceptions = { RuntimeException.class }, 
		commandProperties = {
					@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000") 
	})
	private List<CountryDetails> getCountryDetailsUsingRestTemplate(String country) {
        return restTemplate
                .getForObject(COUNTRY_DETAILS_API + country, List.class);
//                .exchange(url, HttpMethod.GET, null,
//                        new ParameterizedTypeReference<String>() {}, country)
//                .getBody();
    }
    
    
    public List<CountryDetails> getCountryDetailsFallback() {
    	CountryDetails countryDetails = new CountryDetails();
    	countryDetails.setMessage("Something happend wrong...!");
    	return Arrays.asList(countryDetails);
    }    
    
}