package utilizatori;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UtilizatorService {
    @Autowired
    private UtilizatorRepository utilizatorRepository;

    @Autowired
    private BCryptPasswordEncoder hashedpasswordEncoder;

    public List<Utilizator> getAllUtilizatori() {
        return utilizatorRepository.findAll();
    }

    public Optional<Utilizator> getUtilizatorById(Long id) {
        return utilizatorRepository.findById(id);
    }

    public Utilizator addUtilizator(Utilizator utilizator) {
        // Ensure password is encoded before saving
        if (utilizator.getParola() != null && !utilizator.getParola().isEmpty()) {
            utilizator.setParola(hashedpasswordEncoder.encode(utilizator.getParola()));
        } else {
            throw new IllegalArgumentException("Password cannot be null or empty.");
        }
        return utilizatorRepository.save(utilizator);
    }


    public Utilizator updateUtilizator(Long id, Utilizator utilizatorDetails) {
        return utilizatorRepository.findById(id).map(utilizator -> {
            utilizator.setEmail(utilizatorDetails.getEmail());
            utilizator.setParola(hashedpasswordEncoder.encode(utilizatorDetails.getParola()));
            utilizator.setRol(utilizatorDetails.getRol());
            return utilizatorRepository.save(utilizator);
        }).orElseThrow(() -> new RuntimeException("Utilizator not found with id " + id));
    }

    public boolean existsByEmail(String email) {
        return utilizatorRepository.existsByEmail(email);
    }

    public void deleteUtilizator(Long id) {
        utilizatorRepository.deleteById(id);
    }

    public List<Utilizator> getUtilizatoriByRol(Utilizator.Rol rol) {
        return utilizatorRepository.findByRol(rol);
    }

    public boolean existsById(Long id) { return utilizatorRepository.existsById(id); }
}

