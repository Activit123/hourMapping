package com.mihai.Java_2024.config;

import com.mihai.Java_2024.utils.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfiguration {
    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    private static final String[] WHITE_LIST_URL = {
            "/api/v1/auth/**",
            "/v2/api-docs",
            "/v3/api-docs",
            "/v3/api-docs/**",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui/**",
            "/webjars/**",
            "/swagger-ui.html"
    };

    @Order(2)
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(AbstractHttpConfigurer::disable)
                .securityMatcher(WHITE_LIST_URL)
                .authorizeHttpRequests(req -> req.anyRequest()
                        .permitAll());

        return http.build();
    }

    @Order(1)
    @Bean
    public SecurityFilterChain endpointsFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .securityMatcher("/api/**")
                .authorizeHttpRequests(req -> req
                        .requestMatchers("POST", "/api/login").permitAll()
                        .requestMatchers("POST", "/api/users").permitAll()
                        .requestMatchers("GET", "/api/revenues/**").hasAnyAuthority(Constants.ROLE_NORMAL_USER)
                        .requestMatchers("POST", "/api/revenues/**").hasAnyAuthority(Constants.ROLE_NORMAL_USER)
                        .requestMatchers("GET", "/api/category/**").hasAnyAuthority(Constants.ROLE_NORMAL_USER)
                        .requestMatchers("POST", "/api/category/**").hasAnyAuthority(Constants.ROLE_NORMAL_USER)
                        .requestMatchers("PUT", "/api/category/**").hasAnyAuthority(Constants.ROLE_NORMAL_USER)
                        .requestMatchers("DELETE", "/api/category/**").hasAnyAuthority(Constants.ROLE_NORMAL_USER)
                        .requestMatchers("GET", "/api/admin/**").hasAnyAuthority(Constants.ROLE_ADMIN)
                        .requestMatchers("POST", "/api/admin/**").hasAnyAuthority(Constants.ROLE_ADMIN)
                        .requestMatchers("PUT", "/api/admin/**").hasAnyAuthority(Constants.ROLE_ADMIN)
                        .requestMatchers("DELETE", "/api/admin/**").hasAnyAuthority(Constants.ROLE_ADMIN)
                        .requestMatchers("POST", "/api/chatbot/**").permitAll()
                        .anyRequest()
                        .authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, BasicAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOriginPatterns("*")
                        .allowedMethods("GET", "POST", "PUT", "DELETE")
                        .allowedHeaders("*")
                        .allowCredentials(true);
            }
        };
    }

}