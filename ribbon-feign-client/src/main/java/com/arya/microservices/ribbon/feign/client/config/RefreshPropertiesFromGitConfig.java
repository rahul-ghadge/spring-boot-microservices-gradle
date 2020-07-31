package com.arya.microservices.ribbon.feign.client.config;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.client.RestTemplate;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;

@Configuration
public class RefreshPropertiesFromGitConfig {

    public final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private Environment environment;


    @Scheduled(fixedDelay = 30000L)
    public void refreshProperties() throws UnknownHostException {
        StringBuffer stringBuffer = getActuatorRefreshURL(environment);
        
        logger.info("Property refreshing at : {}", new Date());
        logger.info("Property refreshing from : {}", stringBuffer.toString());

        final String requestJson = "{}";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(requestJson, headers);
        Object response = new RestTemplate().postForObject(stringBuffer.toString(), entity, Object.class);
        logger.info("Updated properties from GIT :: {}", response);
    }

    private static StringBuffer getActuatorRefreshURL(Environment environment) throws UnknownHostException {
        final String port = environment.getProperty("local.server.port");
        final String hostname = InetAddress.getLocalHost().getHostAddress();

        StringBuffer stringBuffer = new StringBuffer();

        stringBuffer.append("http://");
        stringBuffer.append(InetAddress.getLocalHost().getHostAddress() == null ? "localhost" : hostname);
        stringBuffer.append(":");
        stringBuffer.append(environment.getProperty("local.server.port") == null ? 8088 : port);
        stringBuffer.append("/actuator/refresh");

        return stringBuffer;
    }

}
