package com.example.pethappy.config;

import com.example.pethappy.filters.BannedOwnerInterceptor;
import com.example.pethappy.filters.MessageInterceptor;
import com.example.pethappy.service.BannedOwnerService;
import com.example.pethappy.service.MessageService;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class ApplicationBeanConfiguration {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public MessageInterceptor interceptor(MessageService messageService) {
        return new MessageInterceptor(messageService);
    }
    @Bean
    public BannedOwnerInterceptor bannedOwnerInterceptor(BannedOwnerService bannedOwnerService) {
        return new BannedOwnerInterceptor(bannedOwnerService);
    }
}
