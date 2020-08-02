package com.arya.microservices.country.details.restful.controller;

import com.arya.microservices.country.details.restful.CountryDetailsRestfulClientApplicationTest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class CountryDetailsControllerTest extends CountryDetailsRestfulClientApplicationTest {

    @InjectMocks
    private CountryDetailsController controller;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private Environment environment;

    @Value("classpath:data/response.json")
    private Resource resourceFile;

    @SuppressWarnings("unchecked")
    @DisplayName("CountryDetailsController - Test country data by RestTemplate")
    @Test
    void getCountryData() throws IOException {

        List<Map<String, Object>> mockList = (List<Map<String, Object>>) new ObjectMapper().readValue(resourceFile.getInputStream(), List.class);

        when(environment.getProperty(anyString())).thenReturn(String.valueOf(port));
        when(restTemplate.getForObject(
                anyString(),
                eq(List.class)
        )).thenReturn(mockList);

        List<Map<String, Object>> res = controller.getCountryData(null);
        assertNotNull(res);

        verify(restTemplate, times(1))
                .getForObject(anyString(), eq(List.class));
    }
}