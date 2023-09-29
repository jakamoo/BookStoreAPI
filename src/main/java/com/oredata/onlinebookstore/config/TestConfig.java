package com.oredata.onlinebookstore.config;

import com.oredata.onlinebookstore.service.JwtUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
@Profile("test")
public class TestConfig {


    @Bean
    public SecurityFilterChain testFilterChain(HttpSecurity http, JwtUtils jwtUtils) throws Exception {

        http.authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/**").permitAll()
                        )
                .csrf().disable();


        return http.build();
    }
}
