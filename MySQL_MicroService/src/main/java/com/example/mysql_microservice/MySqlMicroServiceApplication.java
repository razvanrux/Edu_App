package com.example.mysql_microservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages =
        {"com.example.mysql_microservice", "student", "professor", "discipline", "utilizatori", "exceptions", "com.example.grpc", "com.example.config", "com.example.controller","com.example.filters", "com.example.utils"})
@EnableJpaRepositories(basePackages =
        {"student", "professor", "discipline", "utilizatori", "exceptions", "com.example.grpc", "com.example.config", "com.example.controller","com.example.filters", "com.example.utils"})
@EntityScan({"student", "professor", "discipline", "utilizatori"})
public class MySqlMicroServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MySqlMicroServiceApplication.class, args);
    }

}
