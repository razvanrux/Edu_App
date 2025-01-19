package professor;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProfessorRepository extends JpaRepository<ProfessorEntity, Integer> {

    // Query by academic rank
    List<ProfessorEntity> findByGradDidactic(ProfessorEntity.GradDidactic gradDidactic);

    // Query by name (partial match)
    List<ProfessorEntity> findByNumeContaining(String name);

    boolean existsByEmail(String email);
}
