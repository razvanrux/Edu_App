package discipline;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class DisciplineService {

    private final DisciplineRepository disciplineRepository;

    public DisciplineService(DisciplineRepository disciplineRepository) {
        this.disciplineRepository = disciplineRepository;
    }

    public List<DisciplineEntity> getAllDisciplines() {
        return disciplineRepository.findAll();
    }

    public DisciplineEntity getDisciplineByCod(String cod) {
        return disciplineRepository.findById(cod)
                .orElseThrow(() -> new NoSuchElementException("Discipline with COD " + cod + " not found"));
    }

    public DisciplineEntity addDiscipline(DisciplineEntity discipline) {
        return disciplineRepository.save(discipline);
    }

    public void deleteDiscipline(String cod) {
        disciplineRepository.deleteById(cod);
    }

    public List<DisciplineEntity> getDisciplinesByType(DisciplineEntity.TipDisciplina tipDisciplina) {
        return disciplineRepository.findByTipDisciplina(tipDisciplina);
    }

    public List<DisciplineEntity> getDisciplinesByCategorie(DisciplineEntity.CategorieDisciplina categorieDisciplina) {
        return disciplineRepository.findByCategorieDisciplina(categorieDisciplina);
    }

    public List<DisciplineEntity> getDisciplinesByTypeAndCategory(DisciplineEntity.TipDisciplina tipDisciplina, DisciplineEntity.CategorieDisciplina categorieDisciplina) {
        return disciplineRepository.findByTipDisciplinaAndCategorieDisciplina(tipDisciplina, categorieDisciplina);
    }
}
