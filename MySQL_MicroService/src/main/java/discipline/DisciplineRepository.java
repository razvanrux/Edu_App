package discipline;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DisciplineRepository extends JpaRepository<DisciplineEntity, String> {
    // Query by type
    List<DisciplineEntity> findByTipDisciplina(DisciplineEntity.TipDisciplina tipDisciplina);
    // Query by category
    List<DisciplineEntity> findByCategorieDisciplina(DisciplineEntity.CategorieDisciplina categorieDisciplina);
    // Query by type and category
    List<DisciplineEntity> findByTipDisciplinaAndCategorieDisciplina(DisciplineEntity.TipDisciplina tipDisciplina, DisciplineEntity.CategorieDisciplina categorieDisciplina);
}
