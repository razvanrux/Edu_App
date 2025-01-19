package com.example.mongodb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories(basePackages = "com.example.mongodb.repositories")
@EnableJpaRepositories(basePackages = "com.example.mysql")
@EntityScan(basePackages = "com.example.mysql")
public class MongoDbMicroServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MongoDbMicroServiceApplication.class, args);
    }

}
