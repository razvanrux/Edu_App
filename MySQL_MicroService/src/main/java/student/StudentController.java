package student;

import exceptions.ResourceConflictException;
import exceptions.ResourceNotFoundException;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getStudents(@RequestParam(required = false) String name) {
        try {
            List<StudentEntity> students;

            // Apply filtering logic based on the optional 'name' query parameter
            if (name != null) {
                students = studentService.getStudentsByName(name);
            } else {
                students = studentService.getAllStudents();
            }

            if (students.isEmpty()) {
                throw new ResourceNotFoundException(name != null
                        ? "No students found with name: " + name
                        : "No students found.");
            }

            return ResponseEntity.ok(Map.of(
                    "data", students.stream()
                            .map(student -> Map.of(
                                    "data", student,
                                    "links", List.of(
                                            Map.of(
                                                    "rel", "self",
                                                    "href", WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(StudentController.class)
                                                            .getStudentById(student.getId())).toUri().toString(),
                                                    "method", "GET"
                                            ),
                                            Map.of(
                                                    "rel", "delete-student",
                                                    "href", WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(StudentController.class)
                                                            .deleteStudent(student.getId())).toUri().toString(),
                                                    "method", "DELETE"
                                            )
                                    )
                            ))
                            .collect(Collectors.toList()),
                    "links", List.of(
                            Map.of(
                                    "rel", "self",
                                    "href", WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(StudentController.class)
                                            .getStudents(name)).toUri().toString(),
                                    "method", "GET"
                            )
                    )
            ));
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                    "code", HttpStatus.NOT_FOUND.value(),
                    "message", ex.getMessage(),
                    "links", List.of(
                            Map.of(
                                    "rel", "self",
                                    "href", WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(StudentController.class)
                                            .getStudents(null)).toUri().toString(),
                                    "method", "GET"
                            )
                    )
            ));
        }
    }

    // Get Student by ID
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getStudentById(@PathVariable int id) {
        try {
            // Fetch the student by ID
            StudentEntity student = studentService.getStudentById(id);

            return ResponseEntity.ok(Map.of(
                    "data", student,
                    "links", List.of(
                            Map.of(
                                    "rel", "self",
                                    "href", WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(StudentController.class)
                                            .getStudentById(id)).toUri().toString(),
                                    "method", "GET"
                            ),
                            Map.of(
                                    "rel", "parent",
                                    "href", WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(StudentController.class)
                                            .getStudents(null)).toUri().toString(),
                                    "method", "GET"
                            ),
                            Map.of(
                                    "rel", "delete-student",
                                    "href", WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(StudentController.class)
                                            .deleteStudent(id)).toUri().toString(),
                                    "method", "DELETE"
                            )
                    )
            ));
        } catch (ResourceNotFoundException ex) {
            // Handle case where student is not found
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                    "code", HttpStatus.NOT_FOUND.value(),
                    "message", ex.getMessage(),
                    "links", List.of(
                            Map.of(
                                    "rel", "parent",
                                    "href", WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(StudentController.class)
                                            .getStudents(null)).toUri().toString(),
                                    "method", "GET"
                            )
                    )
            ));
        }
    }


    @PostMapping
    public ResponseEntity<Map<String, Object>> addStudent(@RequestBody StudentEntity student) {
        try {
            if (student.getEmail() == null) {
                throw new IllegalArgumentException("email is required.");
            }
            if (student.getNume() == null) {
                throw new IllegalArgumentException("Nume is required.");
            }
            if (student.getPrenume() == null) {
                throw new IllegalArgumentException("Prenume is required.");
            }
            if (student.getCicluStudii() == null) {
                throw new IllegalArgumentException("ciclu_studii is required.");
            }
            if (student.getAnStudiu() != 1 && student.getAnStudiu() != 2 && student.getAnStudiu() != 3 && student.getAnStudiu() != 4) {
                throw new IllegalArgumentException("an_studiu is required.");
            }
            if (student.getGrupa() < 100 && student.getGrupa() >= 500) {
                throw new IllegalArgumentException("an_studiu is required.");
            }
            // Save the student
            StudentEntity savedStudent = studentService.addStudent(student);

            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                    "data", savedStudent,
                    "links", List.of(
                            Map.of(
                                    "rel", "self",
                                    "href", WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(StudentController.class)
                                            .getStudentById(savedStudent.getId())).toUri().toString(),
                                    "method", "GET"
                            ),
                            Map.of(
                                    "rel", "parent",
                                    "href", WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(StudentController.class)
                                            .getStudents(null)).toUri().toString(),
                                    "method", "GET"
                            )
                    )
            ));
        } catch (ResourceConflictException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of(
                    "code", HttpStatus.CONFLICT.value(),
                    "message", ex.getMessage()
            ));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(Map.of(
                    "code", HttpStatus.UNPROCESSABLE_ENTITY.value(),
                    "message", ex.getMessage()
            ));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "code", HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "message", "An unexpected error occurred: " + ex.getMessage()
            ));
        }
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteStudent(@PathVariable int id) {
        try {
            // Attempt to delete the student
            studentService.deleteStudent(id);
            return ResponseEntity.ok(Map.of(
                    "message", "Student deleted successfully",
                    "links", List.of(
                            Map.of(
                                    "rel", "parent",
                                    "href", WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(StudentController.class)
                                            .getStudents(null)).toUri().toString(),
                                    "method", "GET"
                            )
                    )
            ));
        } catch (ResourceNotFoundException ex) {
            // Handle student not found
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                    "code", HttpStatus.NOT_FOUND.value(),
                    "message", ex.getMessage(),
                    "links", List.of(
                            Map.of(
                                    "rel", "parent",
                                    "href", WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(StudentController.class)
                                            .getStudents(null)).toUri().toString(),
                                    "method", "GET"
                            )
                    )
            ));
        }
    }


}
