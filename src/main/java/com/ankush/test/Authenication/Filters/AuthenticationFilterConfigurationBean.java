package com.ankush.test.Authenication.Filters;

import com.ankush.test.Authenication.Filters.AuthenticationFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuthenticationFilterConfigurationBean {
    @Bean
    public FilterRegistrationBean<AuthenticationFilter> filterFilterRegistrationBean() {
        System.out.println("AYY");
        FilterRegistrationBean<AuthenticationFilter> registrationBean = new FilterRegistrationBean<>();

        registrationBean.setFilter(new AuthenticationFilter());
        registrationBean.addUrlPatterns("/AISSMS/common/*");
        registrationBean.setOrder(1);

        return registrationBean;

    }

}
