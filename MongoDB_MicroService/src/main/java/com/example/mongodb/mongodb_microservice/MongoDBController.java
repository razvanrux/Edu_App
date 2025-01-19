package com.example.mongodb.mongodb_microservice;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MongoDBController {
    @GetMapping("/")
    public String home() {
        return "Welcome to MongoDB Microservice!";
    }
}
