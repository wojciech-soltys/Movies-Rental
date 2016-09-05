package com.epam.katowice.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

/**
 * Created by Wojciech_Soltys on 23.08.2016.
 */
    @Configuration
    @EnableSpringDataWebSupport
    @EnableTransactionManagement
    public class WebConfig extends WebMvcConfigurerAdapter {

        @Override
        public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
            PageableHandlerMethodArgumentResolver resolver = new PageableHandlerMethodArgumentResolver();
            resolver.setFallbackPageable(new PageRequest(0, 15));
            resolver.setOneIndexedParameters(true);
            argumentResolvers.add(resolver);
        }
    }
