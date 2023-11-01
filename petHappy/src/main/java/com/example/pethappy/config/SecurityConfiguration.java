package com.example.pethappy.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
public class SecurityConfiguration {

    @SuppressWarnings("removal")
    @Bean
    public SecurityFilterChain getSecurityFilterChain(HttpSecurity httpSecurity) throws Exception {


        httpSecurity.authorizeHttpRequests()
                .requestMatchers( "/", "/static/**", "/images/**", "/css/**","/owners/login", "/owners/register").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/owners/login");

        return httpSecurity.build();
    }
}
