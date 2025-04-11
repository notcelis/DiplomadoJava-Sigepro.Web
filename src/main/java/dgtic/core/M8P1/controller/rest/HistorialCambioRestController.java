package dgtic.core.M8P1.controller.rest;

import dgtic.core.M8P1.model.dto.HistorialCambioDTO;
import dgtic.core.M8P1.exception.ResourceNotFoundException;
import dgtic.core.M8P1.model.HistorialCambio;
import dgtic.core.M8P1.model.Tarea;
import dgtic.core.M8P1.model.Usuario;
import dgtic.core.M8P1.service.HistorialCambioService;
import dgtic.core.M8P1.service.TareaService;
import dgtic.core.M8P1.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/tareas/{tareaId}/historial")
public class HistorialCambioRestController {

    @Autowired
    private HistorialCambioService historialCambioService;

    @Autowired
    private TareaService tareaService;

    @Autowired
    private UsuarioService usuarioService;

    // Mapper para convertir de HistorialCambio a HistorialCambioDTO
    private HistorialCambioDTO convertToDto(HistorialCambio historialCambio) {
        HistorialCambioDTO dto = new HistorialCambioDTO();
        dto.setId(historialCambio.getId());
        dto.setAccion(historialCambio.getAccion());
        dto.setFecha(historialCambio.getFecha());
        dto.setTareaId(historialCambio.getTarea().getId());
        dto.setUsuarioId(historialCambio.getUsuario().getId());
        return dto;
    }

    // Mapper para convertir de HistorialCambioDTO a HistorialCambio
    private HistorialCambio convertToEntity(HistorialCambioDTO historialCambioDTO) {
        HistorialCambio historialCambio = new HistorialCambio();
        historialCambio.setId(historialCambioDTO.getId());
        historialCambio.setAccion(historialCambioDTO.getAccion());
        historialCambio.setFecha(historialCambioDTO.getFecha());
        Tarea tarea = tareaService.getTareaById(historialCambioDTO.getTareaId())
                .orElseThrow(() -> new ResourceNotFoundException("Tarea no encontrada con ID: " + historialCambioDTO.getTareaId()));
        historialCambio.setTarea(tarea);
        Usuario usuario = usuarioService.getUsuarioById(historialCambioDTO.getUsuarioId())
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrada con ID: " + historialCambioDTO.getUsuarioId()));
        historialCambio.setUsuario(usuario);
        return historialCambio;
    }

    @GetMapping
    public ResponseEntity<List<HistorialCambioDTO>> getHistorialCambiosByTareaId(@PathVariable Long tareaId) {
        List<HistorialCambio> historialCambios = historialCambioService.getHistorialCambioById(tareaId).stream().toList();
        List<HistorialCambioDTO> historialCambioDTOS = historialCambios.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(historialCambioDTOS);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HistorialCambioDTO> getHistorialCambioById(@PathVariable Long tareaId, @PathVariable Long id) {
        return historialCambioService.getHistorialCambioById(id)
                .filter(historial -> historial.getTarea().getId().equals(tareaId))
                .map(this::convertToDto)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("Historial de cambio no encontrado con ID: " + id + " para la Tarea ID: " + tareaId));
    }

    @PostMapping
    public ResponseEntity<?> registrarCambio(@PathVariable Long tareaId, @Valid @RequestBody HistorialCambioDTO historialCambioDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getAllErrors().stream()
                    .map(ObjectError::getDefaultMessage)
                    .collect(Collectors.toList());
            return ResponseEntity.badRequest().body(errors);
        }

        Tarea tarea = tareaService.getTareaById(tareaId)
                .orElseThrow(() -> new ResourceNotFoundException("Tarea no encontrada con ID: " + tareaId));
        Usuario usuario = usuarioService.getUsuarioById(historialCambioDTO.getUsuarioId())
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrada con ID: " + historialCambioDTO.getUsuarioId()));

        HistorialCambio historialCambio = convertToEntity(historialCambioDTO);
        historialCambio.setTarea(tarea);
        historialCambio.setUsuario(usuario);
        HistorialCambio savedHistorial = historialCambioService.saveHistorialCambio(historialCambio);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedHistorial.getId())
                .toUri();
        return ResponseEntity.created(location).body(convertToDto(savedHistorial));
    }

    // No se suelen tener operaciones de actualización o eliminación del historial de cambios
    // pero podrías agregarlas si tu lógica de negocio lo requiere.
}