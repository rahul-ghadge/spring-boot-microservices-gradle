package com.arya.webservices.common.country.client.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.PostConstruct;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

@Configuration
public class RequestFilter extends OncePerRequestFilter {

    private static Collection<String> excludeUrlPatterns = new ArrayList<>();
    private static final AntPathMatcher pathMatcher = new AntPathMatcher();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        if (request.getRequestURI().contains("favicon")
                || request.getRequestURI().contains("bharat"))
            if (!excludeUrlPatterns.contains(request.getRequestURI()))
                excludeUrlPatterns.add(request.getRequestURI());

            logger.info(excludeUrlPatterns);
            logger.info(request.getRequestURI());

        doFilter(request,response, filterChain);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        boolean shouldNotFilter = excludeUrlPatterns.stream()
                .anyMatch(p -> pathMatcher.match(p, request.getServletPath()));

        return shouldNotFilter;
    }

    @PostConstruct
    public void setDefaultExcludePaths() {
        excludeUrlPatterns.add("/favicon");
        excludeUrlPatterns.add("/favicon.ico");
    }

}
