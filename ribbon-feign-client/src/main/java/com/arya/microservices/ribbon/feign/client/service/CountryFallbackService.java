package com.arya.microservices.ribbon.feign.client.service;

import org.springframework.stereotype.Component;

@Component
public class CountryFallbackService implements CountryClientProxy {

    @Override
    public Object getCountryData(String country) {
        return "Something wrong happened with common country API";
    }

}
