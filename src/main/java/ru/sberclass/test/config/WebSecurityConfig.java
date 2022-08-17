package ru.sberclass.test.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import ru.sberclass.test.controller.BookApi;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class WebSecurityConfig {

    private static final String[] AUTH_WHITELIST = {
            // -- actuator
            "/actuator/**",
            "/management/**",
            // -- api methods

            "/health",
            "/health/*",
            // swagger api json description
            "/api",
            // swagger ui
            "/api/doc**",
            "/metrics",
            "/api/swagger-ui.html",
            "/api/swagger-ui/**",
            "/api/swagger-config",
            "/api/v3/api-docs/**",
            "/app-health/**"
    };

    @Bean
    protected SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers(BookApi.BASE_PATH + BookApi.BOOKS).permitAll()
                .antMatchers(BookApi.BASE_PATH + BookApi.BOOKS_BY_LETTER).permitAll()
                .antMatchers(BookApi.BASE_PATH + BookApi.STATISTIC).permitAll()
                .antMatchers(BookApi.BASE_PATH + BookApi.SAVE).permitAll()
                .antMatchers(AUTH_WHITELIST).permitAll()
                .and().csrf().disable().cors();

        return http.build();
    }
}