package com.arya.microservices.country.details.restful;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CountryDetailsRestfulClientApplicationTest {

        @LocalServerPort
        public int port;

        @DisplayName("CountryDetailsRestfulClientApplication - Test Application context")
        @Test
        public void applicationContextLoaded() {
        }
}