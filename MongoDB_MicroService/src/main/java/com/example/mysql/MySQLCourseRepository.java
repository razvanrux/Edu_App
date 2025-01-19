package com.example.mysql;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MySQLCourseRepository extends JpaRepository<MySQLCourse, String> {
    boolean existsById(String id);
}
