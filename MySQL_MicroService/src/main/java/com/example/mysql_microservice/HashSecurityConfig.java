package com.example.mysql_microservice;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class HashSecurityConfig {
    @Bean
    public BCryptPasswordEncoder hashedpasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
