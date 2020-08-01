package com.arya.microservices.common.country.client.controller;

import com.arya.microservices.common.model.CountryDetails;
import com.arya.microservices.common.country.client.CommonCountryClientApplicationTest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;


class CommonCountryControllerTest extends CommonCountryClientApplicationTest {

    @InjectMocks
    CommonCountryController controller;

    @Mock
    RestTemplate restTemplate;

    @Mock
    WebClient.Builder webClientBuilder;

    @Value("classpath:data/response.json")
    Resource resourceFile;

    @SuppressWarnings({"unchecked", "rawtypes"})
    @DisplayName("CommonCountryController - Test country data by RestTemplate and WebClient")
    @Test
    void getCountryData() throws Exception {

        List<Map<String, Object>> mockList = new ObjectMapper().readValue(resourceFile.getInputStream(), List.class);

        when(restTemplate.getForObject(
                anyString(),
                eq(List.class)
        )).thenReturn(mockList);

        WebClient webClient = mock(WebClient.class);
        WebClient.RequestHeadersUriSpec requestHeadersUriSpec = mock(WebClient.RequestHeadersUriSpec.class);
        WebClient.RequestHeadersSpec<?> requestHeadersSpec = mock(WebClient.RequestHeadersSpec.class);
        WebClient.ResponseSpec responseSpec = mock(WebClient.ResponseSpec.class);
        Mono<List> mono = mock(Mono.class);

        when(webClientBuilder.build()).thenReturn(webClient);
        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(anyString())).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(eq(List.class))).thenReturn(mono);
        when(mono.block()).thenReturn(mockList);

        ResponseEntity<List<CountryDetails>> res = controller.getCountryData(null);
        assertNotNull(res);

        verify(restTemplate, times(1))
                .getForObject(anyString(), eq(List.class));
    }


    @DisplayName("Test country details fallback method")
    @Test
    void getCountryDetailsFallback() {
        List<Map<String, Object>> countryDetailsFallback = controller.getCountryDetailsFallback(null);
        assertEquals(1, countryDetailsFallback.size());
    }

    @DisplayName("Test country currency details fallback method")
    @Test
    void getCountryCurrencyFallback() {
        List<Map<String, Object>> countryCurrencyFallback = controller.getCountryCurrencyFallback(null);
        assertEquals(1, countryCurrencyFallback.size());
    }
}