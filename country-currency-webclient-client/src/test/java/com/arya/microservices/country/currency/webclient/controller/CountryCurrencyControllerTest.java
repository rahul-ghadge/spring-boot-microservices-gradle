package com.arya.microservices.country.currency.webclient.controller;

import com.arya.microservices.country.currency.webclient.CountryCurrencyWebClientApplicationTest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class CountryCurrencyControllerTest extends CountryCurrencyWebClientApplicationTest {

    @InjectMocks
    private CountryCurrencyController countryCurrencyController;

    @Mock
    private Environment environment;

    @Mock
    private WebClient.Builder webClientBuilder;

    @Value("classpath:data/response.json")
    Resource resourceFile;

    @SuppressWarnings("unchecked")
    @DisplayName("CountryCurrencyController - Test country data by RestTemplate and WebClient")
    @Test
    void getCountryData() throws IOException {

        List<Map<String, Object>> mockList = new ObjectMapper().readValue(resourceFile.getInputStream(), List.class);

        when(environment.getProperty(anyString())).thenReturn(String.valueOf(port));

        WebClient webClient = mock(WebClient.class);
        WebClient.RequestHeadersUriSpec requestHeadersUriSpec = mock(WebClient.RequestHeadersUriSpec.class);
        WebClient.RequestHeadersSpec<?> requestHeadersSpec = mock(WebClient.RequestHeadersSpec.class);
        WebClient.ResponseSpec responseSpec = mock(WebClient.ResponseSpec.class);
        Mono<Object> mono = mock(Mono.class);

        when(webClientBuilder.build()).thenReturn(webClient);
        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(anyString())).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(eq(Object.class))).thenReturn(mono);
        when(mono.block()).thenReturn(mockList);

        List<Map<String, Object>> res = countryCurrencyController.getCountryData("india");
        assertNotNull(res);
    }
}