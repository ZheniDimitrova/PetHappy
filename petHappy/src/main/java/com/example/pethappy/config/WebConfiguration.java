package com.example.pethappy.config;

import com.example.pethappy.filters.Interceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    public final Interceptor interceptor;

    public WebConfiguration(Interceptor interceptor) {
        this.interceptor = interceptor;
    }

    public void addInterceptors(InterceptorRegistry interceptorRegistry) {
        interceptorRegistry.addInterceptor(interceptor);
    }
}
