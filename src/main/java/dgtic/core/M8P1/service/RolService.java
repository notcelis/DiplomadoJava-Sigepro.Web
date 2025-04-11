package dgtic.core.M8P1.service;

import dgtic.core.M8P1.model.Rol;
import dgtic.core.M8P1.model.RolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RolService {
    @Autowired
    private RolRepository rolRepository;

    public List<Rol> getAllRoles() {
        return (List<Rol>) rolRepository.findAll();
    }

    public Optional<Rol> getRolById(Long id) {
        return rolRepository.findById(id);
    }

    public Rol saveRol(Rol rol) {
        return rolRepository.save(rol);
    }

    public void deleteRol(Long id) {
        rolRepository.deleteById(id);
    }
}
