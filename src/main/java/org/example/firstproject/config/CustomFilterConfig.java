package org.example.firstproject.config;

import org.example.firstproject.filters.CustomFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CustomFilterConfig {

    @Bean
    public FilterRegistrationBean<CustomFilter> studentFilterRegistration(){
        FilterRegistrationBean<CustomFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new CustomFilter());
        registrationBean.addUrlPatterns("/student/getStudentDetails");
        return registrationBean;
    }
}
