package io.medsys.opteamer.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/api/assets").permitAll()
                        .requestMatchers("/api/assets/**").permitAll()
                        .requestMatchers("/api/inventories").permitAll()
                        .requestMatchers("/api/inventories/**").permitAll()
                        .requestMatchers("/api/pre-operative-assessments").permitAll()
                        .requestMatchers("/api/pre-operative-assessments/**").permitAll()
                        .requestMatchers("/api/patients").permitAll()
                        .requestMatchers("/api/patients/**").permitAll()
                        .requestMatchers("/api/operation-providers").permitAll()
                        .requestMatchers("/api/operation-providers/**").permitAll()
                        .requestMatchers("/api/operation-rooms").permitAll()
                        .requestMatchers("/api/operation-rooms/**").permitAll()
                        .requestMatchers("/api/team-members").permitAll()
                        .requestMatchers("/api/team-members/**").permitAll()
                        .requestMatchers("/api/room-inventories").permitAll()
                        .requestMatchers("/api/room-inventories/**").permitAll()
                        .requestMatchers("/api/operation-types").permitAll()
                        .requestMatchers("/api/operation-types/**").permitAll()
                        .anyRequest().authenticated()
                )
                .logout((logout) -> logout.permitAll());
        return http.build();
    }
}
