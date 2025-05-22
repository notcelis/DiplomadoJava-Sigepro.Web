package dgtic.core.M8P1.service;

import dgtic.core.M8P1.model.PrioridadTarea;
import dgtic.core.M8P1.model.Tarea;
import dgtic.core.M8P1.model.EstadoTarea;
import dgtic.core.M8P1.model.UsuarioProyecto;
import dgtic.core.M8P1.repository.TareaRepository;
import dgtic.core.M8P1.repository.UsuarioProyectoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TareaService {
    @Autowired
    private TareaRepository tareaRepository;

    @Autowired
    private UsuarioProyectoRepository usuarioProyectoRepository;

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


    public List<Tarea> getTareasFiltradas(Long proyectoId, Long usuarioId, PrioridadTarea prioridad, String search) {
        List<Tarea> tareas = tareaRepository.findAll(); // O usa un método paginado

        return tareas.stream()
                .filter(t -> proyectoId == null || t.getProyecto().getId().equals(proyectoId))
                .filter(t -> usuarioId == null || t.getUsuario().getId().equals(usuarioId))
                .filter(t -> prioridad == null || t.getPrioridad().equals(prioridad))
                .filter(t -> search == null || search.isBlank()
                        || t.getNombre().toLowerCase().contains(search.toLowerCase())
                        || (t.getDescripcion() != null && t.getDescripcion().toLowerCase().contains(search.toLowerCase())))
                .collect(Collectors.toList());
    }

//    public List<Tarea> getTareasPermitidasParaUsuario(Long usuarioId) {
//        List<UsuarioProyecto> permisos = usuarioProyectoRepository.findByUsuarioId(usuarioId);
//        List<Long> proyectosPermitidos = permisos.stream()
//                .map(up -> up.getProyecto().getId())
//                .collect(Collectors.toList());
//
//        return tareaRepository.findByProyectoIdIn(proyectosPermitidos);
//    }


    public List<Tarea> obtenerTareasFiltradas(Long usuarioIdAcceso, Long proyectoId, Long usuarioId, PrioridadTarea prioridad, String search) {
        return tareaRepository.buscarTareasFiltradas(usuarioIdAcceso, proyectoId, usuarioId, prioridad, search);
    }

    public  List<Tarea> obtenerTareasPorProyecto(Long proyectoId){
        return tareaRepository.findByproyectoId(proyectoId);
    }

}

