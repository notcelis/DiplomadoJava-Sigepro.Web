package dgtic.core.M8P1.controller;

import dgtic.core.M8P1.Util.FileStorageProperties;
import dgtic.core.M8P1.Util.FileStorageService;
import dgtic.core.M8P1.model.*;
import dgtic.core.M8P1.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Controller
@RequestMapping("/comentarios")
public class ComentarioController {

    @Autowired
    private TareaService tareaService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private ComentarioService comentarioService;

    @Autowired
    private HistorialCambioService historialCambioService;

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private FileStorageProperties fileStorageProperties;

    @Autowired
    private NotificacionService notificacionService;

    @PostMapping("/{id}")
    public String agregarComentario(@PathVariable Long id,
                                    @RequestParam("usuarioId") Long usuarioId,
                                    @RequestParam("comentario") String textoComentario,
                                    @RequestParam(value = "archivo", required = false) MultipartFile archivo) throws IOException {

        Tarea tarea = tareaService.getTareaById(id).orElseThrow();
        Usuario usuario = usuarioService.getUsuarioById(usuarioId).orElseThrow();


        Comentario nuevoComentario = new Comentario();
        nuevoComentario.setTarea(tarea);
        nuevoComentario.setUsuario(usuario);
        nuevoComentario.setComentario(textoComentario);
        nuevoComentario.setFecha(LocalDate.from(LocalDateTime.now()));

        if (archivo != null && !archivo.isEmpty()) {
            String nombreArchivo = fileStorageService.storeFile(archivo);
            nuevoComentario.setArchivoUrl(nombreArchivo);  // Generar URL accesible
        }

        // Registrar en el historial de cambios
        HistorialCambio historial = new HistorialCambio();
        historial.setAccion("Comentario agregado");
        historial.setFecha(LocalDate.now());
        historial.setTarea(tarea);
        historial.setUsuario(usuario);
        historialCambioService.saveHistorialCambio(historial);

        comentarioService.saveComentario(nuevoComentario);

        if (tarea.getUsuario() != null) {
            Notificacion notificacion = new Notificacion();
            notificacion.setUsuario(tarea.getUsuario());
            notificacion.setContenido("Se ha comentado la tarea: " + tarea.getNombre());
            notificacion.setFecha(LocalDateTime.now());
            notificacion.setTarea(tarea);
            notificacion.setLeido(false);
            notificacionService.saveNotificacion(notificacion);
        }

        return "redirect:/tarea/ver/" + id;  // Redirige al detalle de la tarea
    }


}
