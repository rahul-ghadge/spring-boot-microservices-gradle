package com.arya.microservices.common.country.client;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;


@SpringBootTest(webEnvironment= WebEnvironment.RANDOM_PORT)
public class CommonCountryClientApplicationTest {

    @LocalServerPort
    public int port;

    @DisplayName("CommonCountryClientApplication - Test Application context")
    @Test
    public void applicationContextLoaded() {
    }

//    @DisplayName("Test main method")
//    @Test
//    public void applicationContextTest() {
//        CommonCountryClientApplication.main(new String[] {});
//    }

}