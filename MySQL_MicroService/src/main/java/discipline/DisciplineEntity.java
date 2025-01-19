package discipline;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import professor.ProfessorEntity;

@Entity
@Table(name = "disciplina_de_studii")
@Getter
@Setter
public class DisciplineEntity {

    @Id
    @Column(name = "COD", nullable = false, unique = true)
    @JsonProperty("cod")
    private String cod;

    @ManyToOne
    @JoinColumn(name = "ID_titular", referencedColumnName = "id")
    @JsonProperty("titular")
    private professor.ProfessorEntity titular;

    @Column(name = "nume_disciplina", nullable = false)
    @JsonProperty("nume_disciplina")
    private String numeDisciplina;

    @Column(name = "an_studiu", nullable = false)
    @JsonProperty("an_studiu")
    private int anStudiu;

    @Enumerated(EnumType.STRING)
    @Column(name = "tip_disciplina", nullable = false)
    @JsonProperty("tip_disciplina")
    private TipDisciplina tipDisciplina;

    @Enumerated(EnumType.STRING)
    @Column(name = "categorie_disciplina", nullable = false)
    @JsonProperty("categorie_disciplina")
    private CategorieDisciplina categorieDisciplina;

    @Enumerated(EnumType.STRING)
    @Column(name = "tip_examinare", nullable = false)
    @JsonProperty("tip_examinare")
    private TipExaminare tipExaminare;

    public enum TipDisciplina {
        impusa,
        optionala,
        liber_aleasa
    }

    public enum CategorieDisciplina {
        domeniu,
        specialitate,
        adiacenta
    }

    public enum TipExaminare {
        examen,
        colocviu
    }

    // Getters and Setters
    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public ProfessorEntity getTitular() {
        return titular;
    }

    public void setTitular(ProfessorEntity titular) {
        this.titular = titular;
    }

    public String getNumeDisciplina() {
        return numeDisciplina;
    }

    public void setNumeDisciplina(String numeDisciplina) {
        this.numeDisciplina = numeDisciplina;
    }

    public int getAnStudiu() {
        return anStudiu;
    }

    public void setAnStudiu(int anStudiu) {
        this.anStudiu = anStudiu;
    }

    public TipDisciplina getTipDisciplina() {
        return tipDisciplina;
    }

    public void setTipDisciplina(TipDisciplina tipDisciplina) {
        this.tipDisciplina = tipDisciplina;
    }

    public CategorieDisciplina getCategorieDisciplina() {
        return categorieDisciplina;
    }

    public void setCategorieDisciplina(CategorieDisciplina categorieDisciplina) {
        this.categorieDisciplina = categorieDisciplina;
    }

    public TipExaminare getTipExaminare() {
        return tipExaminare;
    }

    public void setTipExaminare(TipExaminare tipExaminare) {
        this.tipExaminare = tipExaminare;
    }

    @Override
    public String toString() {
        return "DisciplineEntity{" +
                "cod='" + cod + '\'' +
                ", titular=" + (titular != null ? titular.getNume() : "None") +
                ", numeDisciplina='" + numeDisciplina + '\'' +
                ", anStudiu=" + anStudiu +
                ", tipDisciplina=" + tipDisciplina +
                ", categorieDisciplina=" + categorieDisciplina +
                ", tipExaminare=" + tipExaminare +
                '}';
    }

}