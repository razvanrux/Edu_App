package professor;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "`date_cadre_didactice`")
public class ProfessorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("id")
    private int id;

    @Getter
    @Column(nullable = false)
    @JsonProperty("nume")
    @NotNull(message = "nume is required.")
    private String nume;

    @Column(nullable = false)
    @JsonProperty("prenume")
    @NotNull(message = "prenume is required.")
    private String prenume;

    @Column(unique = true)
    @JsonProperty("email")
    @NotNull(message = "email is required.")
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "grad_didactic")
    @JsonProperty("grad_didactic")
    private GradDidactic gradDidactic;

    @Enumerated(EnumType.STRING)
    @Column(name = "tip_asociere", nullable = false)
    @JsonProperty("tip_asociere")
    @NotNull(message = "tipAsociere is required.")
    private TipAsociere tipAsociere;

    @Column
    @JsonProperty("afiliere")
    private String afiliere;

    public enum GradDidactic {
        ASIST, SEF_LUCR, CONF, PROF
    }

    public enum TipAsociere {
        TITULAR, ASOCIAT, EXTERN
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getPrenume() {
        return prenume;
    }

    public void setPrenume(String prenume) {
        this.prenume = prenume;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public GradDidactic getGradDidactic() {
        return gradDidactic;
    }

    public void setGradDidactic(GradDidactic gradDidactic) {
        this.gradDidactic = gradDidactic;
    }

    public TipAsociere getTipAsociere() {
        return tipAsociere;
    }

    public void setTipAsociere(TipAsociere tipAsociere) {
        this.tipAsociere = tipAsociere;
    }

    public String getAfiliere() {
        return afiliere;
    }

    public void setAfiliere(String afiliere) {
        this.afiliere = afiliere;
    }

    @Override
    public String toString() {
        return "ProfessorEntity{" +
                "id=" + id +
                ", nume='" + nume + '\'' +
                ", prenume='" + prenume + '\'' +
                ", email='" + email + '\'' +
                ", gradDidactic=" + gradDidactic +
                ", tipAsociere=" + tipAsociere +
                ", afiliere='" + afiliere + '\'' +
                '}';
    }
    public String getNume() {
        return nume;
    }
}
