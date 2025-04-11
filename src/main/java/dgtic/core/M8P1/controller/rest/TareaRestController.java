package dgtic.core.M8P1.controller.rest;

import dgtic.core.M8P1.model.dto.TareaDTO;
import dgtic.core.M8P1.exception.ResourceNotFoundException;
import dgtic.core.M8P1.model.EstadoTarea;
import dgtic.core.M8P1.model.Proyecto;
import dgtic.core.M8P1.model.Tarea;
import dgtic.core.M8P1.model.Usuario;
import dgtic.core.M8P1.service.ProyectoService;
import dgtic.core.M8P1.service.TareaService;
import dgtic.core.M8P1.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/tareas")
public class TareaRestController {

    @Autowired
    private TareaService tareaService;

    @Autowired
    private ProyectoService proyectoService;

    @Autowired
    private UsuarioService usuarioService;

    private TareaDTO convertToDto(Tarea tarea) {
        TareaDTO dto = new TareaDTO();
        dto.setId(tarea.getId());
        dto.setProyectoId(tarea.getProyecto().getId());
        dto.setUsuarioId(tarea.getUsuario().getId());
        dto.setNombre(tarea.getNombre());
        dto.setDescripcion(tarea.getDescripcion());
        dto.setEstado(tarea.getEstado());
        dto.setFechaLimite(tarea.getFechaLimite());
        return dto;
    }

    private Tarea convertToEntity(TareaDTO tareaDTO) {
        Tarea tarea = new Tarea();
        tarea.setId(tareaDTO.getId());
        Proyecto proyecto = proyectoService.getProyectoById(tareaDTO.getProyectoId())
                .orElseThrow(() -> new ResourceNotFoundException("Proyecto no encontrado con ID: " + tareaDTO.getProyectoId()));
        tarea.setProyecto(proyecto);
        Usuario usuario = usuarioService.getUsuarioById(tareaDTO.getUsuarioId())
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con ID: " + tareaDTO.getUsuarioId()));
        tarea.setUsuario(usuario);
        tarea.setNombre(tareaDTO.getNombre());
        tarea.setDescripcion(tareaDTO.getDescripcion());
        tarea.setEstado(tareaDTO.getEstado());
        tarea.setFechaLimite(tareaDTO.getFechaLimite());
        return tarea;
    }

    @GetMapping
    public ResponseEntity<List<TareaDTO>> getAllTareas() {
        List<Tarea> tareas = tareaService.getAllTareas();
        List<TareaDTO> tareaDTOS = tareas.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(tareaDTOS);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TareaDTO> getTareaById(@PathVariable Long id) {
        return tareaService.getTareaById(id)
                .map(this::convertToDto)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("Tarea no encontrada con ID: " + id));
    }

    @PostMapping
    public ResponseEntity<?> createTarea(@Valid @RequestBody TareaDTO tareaDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getAllErrors().stream()
                    .map(ObjectError::getDefaultMessage)
                    .collect(Collectors.toList());
            return ResponseEntity.badRequest().body(errors);
        }
        Tarea tarea = convertToEntity(tareaDTO);
        Tarea savedTarea = tareaService.saveTarea(tarea);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedTarea.getId())
                .toUri();
        return ResponseEntity.created(location).body(convertToDto(savedTarea));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateTarea(@PathVariable Long id, @Valid @RequestBody TareaDTO tareaDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getAllErrors().stream()
                    .map(ObjectError::getDefaultMessage)
                    .collect(Collectors.toList());
            return ResponseEntity.badRequest().body(errors);
        }
        return tareaService.getTareaById(id)
                .map(existingTarea -> {
                    Tarea updatedTarea = convertToEntity(tareaDTO);
                    updatedTarea.setId(id);
                    Tarea savedTarea = tareaService.saveTarea(updatedTarea);
                    return ResponseEntity.ok(convertToDto(savedTarea));
                })
                .orElseThrow(() -> new ResourceNotFoundException("Tarea no encontrada con ID: " + id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTarea(@PathVariable Long id) {
        if (tareaService.getTareaById(id).isEmpty()) {
            throw new ResourceNotFoundException("Tarea no encontrada con ID: " + id);
        }
        tareaService.deleteTarea(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<TareaDTO>> getTareasByEstado(@PathVariable EstadoTarea estado) {
        List<Tarea> tareas = tareaService.findByEstado(estado);
        List<TareaDTO> tareaDTOS = tareas.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(tareaDTOS);
    }
}