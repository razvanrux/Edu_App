package com.example.mongodb.repositories;

import com.example.mongodb.models.Course;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface CourseRepository extends MongoRepository<Course, String> {
    List<Course> findByProfessorId(String professorId);
}
