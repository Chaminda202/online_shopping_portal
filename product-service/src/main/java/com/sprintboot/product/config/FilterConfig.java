package com.sprintboot.product.config;

import com.sprintboot.product.filter.RequestResponseLogger;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {
    @Bean
    FilterRegistrationBean<RequestResponseLogger> filterRegistrationBean(RequestResponseLogger requestResponseLogger) {
        FilterRegistrationBean<RequestResponseLogger> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(requestResponseLogger);
        registrationBean.addUrlPatterns("/v1/product", "/v1/product/*");
        return registrationBean;
    }
}
