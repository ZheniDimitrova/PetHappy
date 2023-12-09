package com.example.pethappy.config;

import com.example.pethappy.model.entity.enums.UserRoleEnum;
import com.example.pethappy.repository.OwnerRepository;
import com.example.pethappy.service.impl.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.DelegatingSecurityContextRepository;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.RequestAttributeSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;


@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain getSecurityFilterChain(HttpSecurity httpSecurity, SecurityContextRepository securityContextRepository) throws Exception {


        httpSecurity.authorizeHttpRequests((requests) ->
                requests.requestMatchers( "/", "/static/**", "/images/**", "/css/**", "/css/responsive/**","/owners/login", "/owners/register", "/aboutUs", "/contacts", "/shop",
                        "/products/{type}", "/downloadPicture/{productId}", "/owners/login-error").permitAll()
                .requestMatchers("/owners/admin").hasAuthority(UserRoleEnum.ADMINISTRATOR.name())
                .requestMatchers("/owners/moderator").hasAuthority(UserRoleEnum.MODERATOR.name())
                .anyRequest().authenticated())
                .formLogin((form) ->
                form.loginPage("/owners/login")
                .usernameParameter(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY)
                .passwordParameter(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_PASSWORD_KEY)
                .defaultSuccessUrl("/", true)
                .failureForwardUrl("/owners/login-error"))
                .logout((logout) ->
                        logout.logoutUrl("/owners/logout").invalidateHttpSession(true)
                .logoutSuccessUrl("/"))
                .securityContext((securityContext) ->
                securityContext.securityContextRepository(securityContextRepository));

        return httpSecurity.build();
    }

    @Bean
    public SecurityContextRepository getSecurityContextRepository() {
        return new DelegatingSecurityContextRepository(new RequestAttributeSecurityContextRepository(), new HttpSessionSecurityContextRepository());
    }

    @Bean
    public UserDetailsService getUserDetailsService(OwnerRepository ownerRepository) {
        return new UserDetailsServiceImpl(ownerRepository);
    }
}
