package dgtic.core.M8P1.service;

import dgtic.core.M8P1.model.Tarea;
import dgtic.core.M8P1.model.EstadoTarea;
import dgtic.core.M8P1.repository.TareaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TareaService {
    @Autowired
    private TareaRepository tareaRepository;

    public List<Tarea> getAllTareas() {
        return (List<Tarea>) tareaRepository.findAll();
    }

    public Optional<Tarea> getTareaById(Long id) {
        return tareaRepository.findById(id);
    }

    public Tarea saveTarea(Tarea tarea) {
        return tareaRepository.save(tarea);
    }

    public void deleteTarea(Long id) {
        tareaRepository.deleteById(id);
    }

    public List<Tarea> obtenerTareasPorUsuario(Long usuarioId) {
        return tareaRepository.findTareasByUsuarioId(usuarioId);
    }
    public List<Tarea> findByEstado(EstadoTarea estado) {
        return tareaRepository.findByEstado(String.valueOf(estado));
    }


}

