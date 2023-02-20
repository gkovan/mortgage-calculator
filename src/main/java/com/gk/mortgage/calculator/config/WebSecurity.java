package com.gk.mortgage.calculator.config;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.SecurityFilterChain;
@Configuration
@EnableWebSecurity
public class WebSecurity{
    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
         http
        .cors().and()
        .csrf().disable().authorizeHttpRequests()
        .requestMatchers("/health").permitAll()
        .requestMatchers("/calculate1").hasRole("api-user")
        .anyRequest().authenticated()
        .and()
        .httpBasic();
         return http.build();
    }
}