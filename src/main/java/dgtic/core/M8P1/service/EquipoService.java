package dgtic.core.M8P1.service;

import dgtic.core.M8P1.model.Equipo;
import dgtic.core.M8P1.repository.EquipoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EquipoService {

    @Autowired
    private EquipoRepository equipoRepository;

    public List<Equipo> listarEquipos() {
        return equipoRepository.findAll();
    }

    public Equipo crearEquipo(Equipo equipo) {
        return equipoRepository.save(equipo);
    }

    public Optional<Equipo> obtenerPorId(Long id) {
        return equipoRepository.findById(id);
    }
}