package com.example.mongodb.controllers;

import com.example.mongodb.models.Course;
import com.example.mongodb.repositories.CourseRepository;
import com.example.mysql.MySQLCourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/courses") // Base URL for this controller
public class CourseController {

    private final CourseRepository courseRepository;
    private final MySQLCourseRepository mySQLCourseRepository;

    public CourseController(CourseRepository courseRepository, MySQLCourseRepository mySQLCourseRepository) {
        this.courseRepository = courseRepository;
        this.mySQLCourseRepository = mySQLCourseRepository;
    }

    // Create a new course
    @PostMapping
    public Course createCourse(@RequestBody Course course) {
        if (!mySQLCourseRepository.existsById(course.getId())) {
            throw new RuntimeException("Course does not exist in MySQL. Cannot create in MongoDB.");
        }
        return courseRepository.save(course);
    }

    // Get all courses
    @GetMapping
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    // Get a course by ID
    @GetMapping("/{id}")
    public Course getCourseById(@PathVariable String id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found with id: " + id));
    }

    // Update a course
    @PutMapping("/{id}")
    public Course updateCourse(@PathVariable String id, @RequestBody Course updatedCourse) {
        updatedCourse.setId(id);
        return courseRepository.save(updatedCourse);
    }

    // Delete a course
    @DeleteMapping("/{id}")
    public void deleteCourse(@PathVariable String id) {
        courseRepository.deleteById(id);
    }

    // Add an evaluation probe to a course
    @PostMapping("/{courseId}/evaluation-probes")
    public Course addEvaluationProbe(@PathVariable String courseId, @RequestBody Course.EvaluationProbe probe) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found with id: " + courseId));

        double totalWeight = course.getTotalWeight() + probe.getWeight();
        if (totalWeight > 100) {
            throw new RuntimeException("Total weight of evaluation probes cannot exceed 100%");
        }

        course.getEvaluationProbes().add(probe);
        return courseRepository.save(course);
    }

    // Get all evaluation probes for a course
    @GetMapping("/{courseId}/evaluation-probes")
    public List<Course.EvaluationProbe> getEvaluationProbes(@PathVariable String courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found with id: " + courseId));
        return course.getEvaluationProbes();
    }

    @PostMapping("/{courseId}/materials")
    public Course addMaterial(@PathVariable String courseId, @RequestBody Course.Material material) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found with id: " + courseId));

        course.getMaterials().add(material);
        return courseRepository.save(course);
    }

    @GetMapping("/{courseId}/materials")
    public List<Course.Material> getMaterials(@PathVariable String courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found with id: " + courseId));
        return course.getMaterials();
    }


}
