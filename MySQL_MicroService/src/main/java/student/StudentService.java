package student;

import exceptions.ResourceConflictException;
import exceptions.ResourceNotFoundException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class StudentService {

    private final StudentRepository studentRepository;


    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<StudentEntity> getAllStudents() {
        List<StudentEntity> students = studentRepository.findAll();

        if (students.isEmpty()) {
            throw new ResourceNotFoundException("No students found.");
        }

        return students;
    }


    public StudentEntity getStudentById(int id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Student with ID " + id + " not found"));
    }

    public StudentEntity addStudent(StudentEntity student) {
        try {
            // Save the student
            return studentRepository.save(student);
        } catch (DataIntegrityViolationException ex) {
            if (ex.getCause() instanceof ConstraintViolationException) {
                throw new ResourceConflictException("A student with the email " + student.getEmail() + " already exists.");
            }
            throw ex; // Rethrow other exceptions
        }
    }

    public void deleteStudent(int id) {
        if (!studentRepository.existsById(id)) {
            throw new ResourceNotFoundException("Student with ID " + id + " not found.");
        }
        studentRepository.deleteById(id);
    }


    public List<StudentEntity> getStudentsByName(String name) {
        List<StudentEntity> students = studentRepository.findByNumeContaining(name);

        if (students.isEmpty()) {
            throw new ResourceNotFoundException("No students found with name: " + name);
        }

        return students;
    }

}
