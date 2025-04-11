package dgtic.core.M8P1.controller.rest;

import dgtic.core.M8P1.Util.FileStorageProperties;
import dgtic.core.M8P1.Util.FileStorageService;
import dgtic.core.M8P1.model.dto.ComentarioArchivoDTO;
import dgtic.core.M8P1.model.dto.ComentarioDTO;
import dgtic.core.M8P1.exception.ResourceNotFoundException;
import dgtic.core.M8P1.model.Comentario;
import dgtic.core.M8P1.model.Tarea;
import dgtic.core.M8P1.model.Usuario;
import dgtic.core.M8P1.service.ComentarioService;
import dgtic.core.M8P1.service.TareaService;
import dgtic.core.M8P1.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/tareas/{tareaId}/comentarios")
public class ComentarioRestController {

    @Autowired
    private ComentarioService comentarioService;

    @Autowired
    private TareaService tareaService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private FileStorageService fileStorageService;

    // Mapper para convertir de Comentario a ComentarioDTO
    private ComentarioDTO convertToDto(Comentario comentario) {
        ComentarioDTO dto = new ComentarioDTO();
        dto.setId(comentario.getId());
        dto.setComentario(comentario.getComentario());
        dto.setFecha(comentario.getFecha());
        dto.setTareaId(comentario.getTarea().getId());
        dto.setUsuarioId(comentario.getUsuario().getId());
        dto.setArchivoUrl(comentario.getArchivoUrl());
        return dto;
    }

    // Mapper para convertir de ComentarioDTO a Comentario
    private Comentario convertToEntity(ComentarioDTO comentarioDTO) {
        Comentario comentario = new Comentario();
        comentario.setId(comentarioDTO.getId());
        comentario.setComentario(comentarioDTO.getComentario());
        comentario.setFecha(comentarioDTO.getFecha());
        Tarea tarea = tareaService.getTareaById(comentarioDTO.getTareaId())
                .orElseThrow(() -> new ResourceNotFoundException("Tarea no encontrada con ID: " + comentarioDTO.getTareaId()));
        comentario.setTarea(tarea);
        Usuario usuario = usuarioService.getUsuarioById(comentarioDTO.getUsuarioId())
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con ID: " + comentarioDTO.getUsuarioId()));
        comentario.setUsuario(usuario);
        comentario.setArchivoUrl(comentarioDTO.getArchivoUrl());
        return comentario;
    }

    @GetMapping
    public ResponseEntity<List<ComentarioDTO>> getComentariosByTareaId(@PathVariable Long tareaId) {
        List<Comentario> comentarios = comentarioService.obtenerComentariosPorTarea(tareaId);
        List<ComentarioDTO> comentarioDTOS = comentarios.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(comentarioDTOS);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ComentarioDTO> getComentarioById(@PathVariable Long tareaId, @PathVariable Long id) {
        return comentarioService.getComentarioById(id)
                .filter(comentario -> comentario.getTarea().getId().equals(tareaId))
                .map(this::convertToDto)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("Comentario no encontrado con ID: " + id + " para la Tarea ID: " + tareaId));
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> agregarComentario(@PathVariable Long tareaId,
                                               @Valid @ModelAttribute ComentarioArchivoDTO comentarioArchivoDTO,
                                               BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getAllErrors().stream()
                    .map(ObjectError::getDefaultMessage)
                    .collect(Collectors.toList());
            return ResponseEntity.badRequest().body(errors);
        }

        Tarea tarea = tareaService.getTareaById(tareaId)
                .orElseThrow(() -> new ResourceNotFoundException("Tarea no encontrada con ID: " + tareaId));
        Usuario usuario = usuarioService.getUsuarioById(comentarioArchivoDTO.getUsuarioId())
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con ID: " + comentarioArchivoDTO.getUsuarioId()));

        Comentario nuevoComentario = new Comentario();
        nuevoComentario.setTarea(tarea);
        nuevoComentario.setUsuario(usuario);
        nuevoComentario.setComentario(comentarioArchivoDTO.getComentario());
        nuevoComentario.setFecha(LocalDate.now());

        if (comentarioArchivoDTO.getArchivo() != null && !comentarioArchivoDTO.getArchivo().isEmpty()) {
            String nombreArchivo = fileStorageService.storeFile(comentarioArchivoDTO.getArchivo());
            nuevoComentario.setArchivoUrl("/imagenes/" + nombreArchivo); // Generar URL accesible
        }

        Comentario savedComentario = comentarioService.saveComentario(nuevoComentario);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedComentario.getId())
                .toUri();
        return ResponseEntity.created(location).body(convertToDto(savedComentario));
    }

    // Considera agregar endpoints para actualizar y eliminar comentarios si es necesario.
}