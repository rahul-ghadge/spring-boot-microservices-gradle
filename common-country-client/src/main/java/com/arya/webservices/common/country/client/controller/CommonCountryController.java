package com.arya.webservices.common.country.client.controller;


import com.arya.microservices.common.model.CountryCurrency;
import com.arya.microservices.common.model.CountryDetails;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

import com.netflix.hystrix.contrib.javanica.conf.HystrixPropertiesManager;
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

import java.util.*;


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


    
    @SuppressWarnings("unchecked")
	@GetMapping(value = {"/", "/{country}"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CountryDetails>> getCountryData(@PathVariable(required = false) String country) {

        if (StringUtils.isEmpty(country))
            country = "bharat";

        logger.info("Getting country details for " + country);
        final String countryName = country;

        // Call country service by hardcoding API endpoint
        List<Map<String, Object>> countryDetails = getCountryDetailsUsingRestTemplate(countryName);
        logger.info("Response from Country Details :: {}", countryDetails);
        List<Map<String, Object>> countryDetailsResponse = new ArrayList<>();
        List<CountryDetails> countryDetailsList = new ArrayList<>();


        if (!CollectionUtils.isEmpty(countryDetails)) {
        	
        	countryDetails.forEach(map -> 
            		map.keySet().stream()
            		.filter(key -> countryName.equalsIgnoreCase((String) map.get("name")))
             		.filter(key -> key.equals("currencies"))
             		.filter(key -> !CollectionUtils.isEmpty((Collection<?>) (map.get("currencies"))))
             		.forEach(key -> 
             		countryDetailsResponse.addAll(
             				getCountryDetailsUsingWebClient(
             						((List<Map<String, Object>>) map.get(key)).get(0).get("code").toString())
             				)
					)
           		 );

			countryDetails.stream()
			.filter(map -> countryName.equalsIgnoreCase((String) map.get("name")))
			.forEach(map -> countryDetailsList.add(new CountryDetails(
							map.get("name").toString(),
							map.get("capital").toString(),
							map.get("region").toString(),
							map.get("subregion").toString(),
							Long.valueOf(map.get("population").toString()),
							""+ map.get("description"),
							(List<CountryCurrency>) map.get("currencies"),
							200,
							"Country details fetched successfully"
							)));
            }
        logger.info("Final response from common country client :: {}", countryDetailsResponse);

		return ResponseEntity.ok(countryDetailsList);
    }

    
    
    @SuppressWarnings("unchecked")
//     Circuit breaker
    @HystrixCommand(fallbackMethod = "getCountryCurrencyFallback", //ignoreExceptions = { RuntimeException.class },
            commandProperties = {
                    @HystrixProperty(name = HystrixPropertiesManager.EXECUTION_ISOLATION_THREAD_TIMEOUT_IN_MILLISECONDS, value = "2000"),
					@HystrixProperty(name = HystrixPropertiesManager.EXECUTION_TIMEOUT_ENABLED, value = "true")
    })
	public List<Map<String, Object>> getCountryDetailsUsingWebClient(String code) {

		return  webClientBuilder.build().get()
				.uri(COUNTRY_CURRENCY_API + code)
				.retrieve()
				.bodyToMono(List.class)
				.block();
    }


    @SuppressWarnings("unchecked")
//  Circuit breaker
	@HystrixCommand(fallbackMethod = "getCountryDetailsFallback", //ignoreExceptions = { RuntimeException.class },
		    commandProperties = {
					@HystrixProperty(name = HystrixPropertiesManager.EXECUTION_ISOLATION_THREAD_TIMEOUT_IN_MILLISECONDS, value = "2000"),
					@HystrixProperty(name = HystrixPropertiesManager.EXECUTION_TIMEOUT_ENABLED, value = "true")
	})
	public List<Map<String, Object>> getCountryDetailsUsingRestTemplate(String country) {
        return restTemplate
                .getForObject(COUNTRY_DETAILS_API + country, List.class);
//                .exchange(url, HttpMethod.GET, null,
//                        new ParameterizedTypeReference<String>() {}, country)
//                .getBody();
    }

    
    public List<Map<String, Object>> getCountryDetailsFallback(String country) {
		Map<String, Object> fallbackMap = new HashMap<>();
		fallbackMap.put("message", "Something wrong happened...!");
    	return Arrays.asList(fallbackMap);
    }


	public List<Map<String, Object>> getCountryCurrencyFallback(String code) {
		Map<String, Object> fallbackMap = new HashMap<>();
		fallbackMap.put("message", "Something wrong happened...!");
		return Arrays.asList(fallbackMap);
	}
    
}