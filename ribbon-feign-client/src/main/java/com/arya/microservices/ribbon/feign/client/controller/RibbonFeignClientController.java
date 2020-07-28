package com.arya.microservices.ribbon.feign.client.controller;

import com.arya.microservices.ribbon.feign.client.service.CountryClientProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope
public class RibbonFeignClientController {

    private static final Logger logger = LoggerFactory.getLogger(RibbonFeignClientController.class);

    @Autowired
    private CountryClientProxy countryClientProxy;

    @GetMapping({"/", "/{country}"})
    public Object getCountryData(@PathVariable(required = false) String country) {

        if (StringUtils.isEmpty(country))
            country = "india";

        logger.info("Getting country details for {}", country);
        // Call country service by API endpoint
        Object response = countryClientProxy.getCountryData(country);
        logger.info("Response Received from common country client {}", response);

        return response;
    }
}
