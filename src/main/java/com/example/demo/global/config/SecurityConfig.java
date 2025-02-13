package com.example.demo.global.config;

import com.example.demo.domain.user.domain.AccountRole;
import com.example.demo.global.error.exception.ExceptionFilter;
import com.example.demo.global.security.JwtTokenFilter;
import com.example.demo.global.security.JwtTokenProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;
    private final ObjectMapper objectMapper;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{

        http
                .csrf(AbstractHttpConfigurer::disable)
                .addFilterBefore(new JwtTokenFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new ExceptionFilter(objectMapper), JwtTokenFilter.class)
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests((registry) ->
                        registry.requestMatchers("/api/v1/credentials/test-login/**","/api/v1/credentials/sign-up-test",
                                        "/api/v1/credentials/oauth/link/kakao","/api/v1/credentials/oauth/kakao","/api/v1/credentials/oauth/link/google"
                                        ,"/api/v1/credentials/oauth/google","/api/v1/credentials/oauth/valid/register","/api/v1/credentials/login"
                                        ,"/api/v1/credentials/refresh","/stomp/chat","/**").permitAll()
                                .requestMatchers("/v3/api-docs/**").permitAll()
                                //.requestMatchers(HttpMethod.POST,"/api/v1/credentials/").permitAll()
                                .anyRequest().permitAll()
                );


        return http.build();
    }

    @Bean
    public RoleHierarchyImpl roleHierarchy() {
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
        roleHierarchy.setHierarchy("ROLE_ADMIN > ROLE_USER");
        return roleHierarchy;
    }

}
