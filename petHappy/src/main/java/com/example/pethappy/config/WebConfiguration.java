package com.example.pethappy.config;

import com.example.pethappy.filters.BannedOwnerInterceptor;
import com.example.pethappy.filters.MessageInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    private final MessageInterceptor interceptor;
    private final BannedOwnerInterceptor bannedOwnerInterceptor;

    public WebConfiguration(MessageInterceptor interceptor, BannedOwnerInterceptor bannedOwnerInterceptor) {
        this.interceptor = interceptor;
        this.bannedOwnerInterceptor = bannedOwnerInterceptor;
    }

    public void addInterceptors(InterceptorRegistry interceptorRegistry) {
        interceptorRegistry.addInterceptor(interceptor);
        interceptorRegistry.addInterceptor(bannedOwnerInterceptor);
    }
}
