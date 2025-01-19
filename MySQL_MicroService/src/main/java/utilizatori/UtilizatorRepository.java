package utilizatori;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UtilizatorRepository extends JpaRepository<Utilizator, Long> {
    List<Utilizator> findByRol(Utilizator.Rol rol);
    Optional<Utilizator> findByEmail(String email);
    boolean existsByEmail(String email);
}
