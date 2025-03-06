package com.alibou.gateway.security;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.http.HttpMethod.*;

@EnableWebSecurity
public class SecurityConfig {

    private final KeycloakRoleConverter keycloakRoleConverter;

    public SecurityConfig(KeycloakRoleConverter keycloakRoleConverter) {
        this.keycloakRoleConverter = keycloakRoleConverter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(requests ->
                        requests.requestMatchers("/api/v1/auth").permitAll()
                                .requestMatchers(GET,"/api/v1/**").hasAuthority("GESTIONNAIRE")
                                .requestMatchers(POST,"/api/v1/**").hasAuthority("GESTIONNAIRE")
                                .requestMatchers(PUT,"/api/v1/**").hasAuthority("GESTIONNAIRE")
                                .requestMatchers(DELETE,"/api/v1/**").hasAuthority("GESTIONNAIRE")
                                .anyRequest().authenticated())
                .oauth2ResourceServer(rs -> rs.jwt(jwt ->
                        jwt.jwtAuthenticationConverter(keycloakRoleConverter)));
        return http.build();
    }
    
}