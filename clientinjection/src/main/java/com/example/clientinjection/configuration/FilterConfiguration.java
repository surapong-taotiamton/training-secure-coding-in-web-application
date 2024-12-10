package com.example.clientinjection.configuration;

import com.example.clientinjection.filter.AccessTokenFilter;
import com.example.clientinjection.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class FilterConfiguration {

    @Autowired
    private AuthenticationService authenticationService;

    @Bean
    FilterRegistrationBean<AccessTokenFilter> accessTokenFilterFilterRegistrationBean() {

        AccessTokenFilter accessTokenFilter = new AccessTokenFilter(authenticationService);

        FilterRegistrationBean<AccessTokenFilter> bean = new FilterRegistrationBean<>();
        bean.setFilter(accessTokenFilter);
        bean.setUrlPatterns(Arrays.asList("/search-json/parcel", "/company-info/*"));
        return bean;
    }

}
