package com.example.mongodb.repositories;
import com.example.mongodb.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface UserRepository extends MongoRepository<User, String> {
    @Query("{ 'username': { $regex: ?0, $options: 'i' } }")
    User findByUsername(String username);
}