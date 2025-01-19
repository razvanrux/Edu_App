package student;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

// Vai de steaua mea
//@Data
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name = "date_studenti")
public class StudentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    @NotNull(message = "nume is required.")
    private String nume;

    @Column(nullable = false)
    @NotNull(message = "prenume is required.")
    private String prenume;

    @Column(unique = true, nullable = false)
    @NotNull(message = "email is required.")
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "ciclu_studii", nullable = false)
    @NotNull(message = "ciclu_studii is required.")
    private CicluStudii cicluStudii;

    @Column(name = "an_studiu", nullable = false)
    @NotNull(message = "an_studiu is required.")
    private int anStudiu;

    @Column(nullable = false)
    @NotNull(message = "grupa is required.")
    private int grupa;

    public enum CicluStudii {
        LICENTA, MASTER
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNume() {
        return nume;
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

    public CicluStudii getCicluStudii() {
        return cicluStudii;
    }

    public void setCicluStudii(CicluStudii cicluStudii) {
        this.cicluStudii = cicluStudii;
    }

    public int getAnStudiu() {
        return anStudiu;
    }

    public void setAnStudiu(int anStudiu) {
        this.anStudiu = anStudiu;
    }

    public int getGrupa() {
        return grupa;
    }

    public void setGrupa(int grupa) {
        this.grupa = grupa;
    }

    @Override
    public String toString() {
        return "StudentEntity{" +
                "id=" + id +
                ", nume='" + nume + '\'' +
                ", prenume='" + prenume + '\'' +
                ", email='" + email + '\'' +
                ", cicluStudii=" + cicluStudii +
                ", anStudiu=" + anStudiu +
                ", grupa=" + grupa +
                '}';
    }
}
