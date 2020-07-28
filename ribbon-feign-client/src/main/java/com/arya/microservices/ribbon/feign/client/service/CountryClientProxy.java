package com.arya.microservices.ribbon.feign.client.service;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="common-country-client", fallback = CountryFallbackService.class)
@RibbonClient(name="common-country-client")
public interface CountryClientProxy {

    @GetMapping("/{country}")
    Object getCountryData(@PathVariable("country") String country);

}
