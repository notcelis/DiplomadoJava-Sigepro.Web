package dgtic.core.M8P1.controller.rest;

import dgtic.core.M8P1.model.Usuario;
import dgtic.core.M8P1.model.dto.ProyectoDTO;
import dgtic.core.M8P1.exception.ResourceNotFoundException;
import dgtic.core.M8P1.model.Proyecto;
import dgtic.core.M8P1.service.ProyectoService;
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
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/proyectos")
public class ProyectoRestController {

    @Autowired
    ProyectoService proyectoService;

    @Autowired
    UsuarioService usuarioService;

    // Mapper para convertir de Proyecto a ProyectoDTO
    private ProyectoDTO convertToDto(Proyecto proyecto) {
        ProyectoDTO dto = new ProyectoDTO();
        dto.setId(proyecto.getId());
        dto.setNombre(proyecto.getNombre());
        dto.setDescripcion(proyecto.getDescripcion());
        return dto;
    }

    // Mapper para convertir de ProyectoDTO a Proyecto
    private Proyecto convertToEntity(ProyectoDTO proyectoDTO) {
        Proyecto proyecto = new Proyecto();
        proyecto.setId(proyectoDTO.getId());
        proyecto.setNombre(proyectoDTO.getNombre());
        proyecto.setDescripcion(proyectoDTO.getDescripcion());
        proyecto.setFechaCreacion(proyectoDTO.getFechaCreacion());
        Usuario usuario = usuarioService.getUsuarioById(proyectoDTO.getCreadorId()).orElseThrow();
        System.out.println(usuario);
        proyecto.setCreador(usuario);
        return proyecto;
    }

    @GetMapping
    public ResponseEntity<List<ProyectoDTO>> getAllProyectos() {
        List<Proyecto> proyectos = proyectoService.getAllProyectos();
        List<ProyectoDTO> proyectoDTOS = proyectos.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(proyectoDTOS);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProyectoDTO> getProyectoById(@PathVariable Long id) {
        return proyectoService.getProyectoById(id)
                .map(this::convertToDto)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("Proyecto no encontrado con ID: " + id));
    }

    @PostMapping
    public ResponseEntity<?> createProyecto(@Valid @RequestBody ProyectoDTO proyectoDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getAllErrors().stream()
                    .map(ObjectError::getDefaultMessage)
                    .collect(Collectors.toList());
            return ResponseEntity.badRequest().body(errors);
        }
        Proyecto proyecto = convertToEntity(proyectoDTO);
        Proyecto savedProyecto = proyectoService.saveProyecto(proyecto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedProyecto.getId())
                .toUri();
        return ResponseEntity.created(location).body(convertToDto(savedProyecto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProyecto(@PathVariable Long id, @Valid @RequestBody ProyectoDTO proyectoDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getAllErrors().stream()
                    .map(ObjectError::getDefaultMessage)
                    .collect(Collectors.toList());
            return ResponseEntity.badRequest().body(errors);
        }
        return proyectoService.getProyectoById(id)
                .map(existingProyecto -> {
                    Proyecto updatedProyecto = convertToEntity(proyectoDTO);
                    updatedProyecto.setId(id); // Asegurar que el ID sea el correcto
                    Proyecto savedProyecto = proyectoService.saveProyecto(updatedProyecto);
                    return ResponseEntity.ok(convertToDto(savedProyecto));
                })
                .orElseThrow(() -> new ResourceNotFoundException("Proyecto no encontrado con ID: " + id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProyecto(@PathVariable Long id) {
        if (proyectoService.getProyectoById(id).isEmpty()) {
            throw new ResourceNotFoundException("Proyecto no encontrado con ID: " + id);
        }
        proyectoService.deleteProyecto(id);
        return ResponseEntity.noContent().build();
    }
}