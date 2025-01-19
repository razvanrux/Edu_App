package professor;

import exceptions.ResourceConflictException;
import exceptions.ResourceNotFoundException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ProfessorService {

    private final ProfessorRepository professorRepository;

    public ProfessorService(ProfessorRepository professorRepository) {
        this.professorRepository = professorRepository;
    }

    public List<ProfessorEntity> getAllProfessors() {
        List<ProfessorEntity> professors = professorRepository.findAll();
        System.out.println("Professors: " + professors); // Log for debugging
        return professors;
    }

    public ProfessorEntity getProfessorById(int id) {
        return professorRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Professor with ID " + id + " not found"));
    }

    public ProfessorEntity addProfessor(ProfessorEntity professor) {
        // Validate required fields
        if (professor.getNume() == null || professor.getNume().isEmpty()) {
            throw new IllegalArgumentException("Professor name is required.");
        }
        if (professor.getEmail() == null || professor.getEmail().isEmpty()) {
            throw new IllegalArgumentException("Professor email is required.");
        }

        // Check for conflicts (e.g., duplicate email)
        if (professorRepository.existsByEmail(professor.getEmail())) {
            throw new ResourceConflictException("A professor with the email " + professor.getEmail() + " already exists.");
        }

        // Save and return the professor
        try {
            return professorRepository.save(professor);
        } catch (DataIntegrityViolationException ex) {
            if (ex.getCause() instanceof ConstraintViolationException) {
                throw new ResourceConflictException("A professor with the email " + professor.getEmail() + " already exists.");
            }
            throw ex; // Rethrow other exceptions
        }
    }

    public void deleteProfessor(int id) {
        if (!professorRepository.existsById(id)) {
            throw new ResourceNotFoundException("Professor with ID " + id + " not found.");
        }
        professorRepository.deleteById(id);
    }

    public List<ProfessorEntity> getProfessorsByRank(ProfessorEntity.GradDidactic gradDidactic) {
        return professorRepository.findByGradDidactic(gradDidactic);
    }

    public List<ProfessorEntity> getProfessorsByName(String name) {
        return professorRepository.findByNumeContaining(name);
    }
}
