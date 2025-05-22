package dgtic.core.M8P1.controller;

import dgtic.core.M8P1.model.*;
import dgtic.core.M8P1.repository.UsuarioProyectoRepository;
import dgtic.core.M8P1.repository.UsuarioRepository;
import dgtic.core.M8P1.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/tarea")
public class TareaController {

    @Autowired
    private TareaService tareaService;

    @Autowired
    private ComentarioService comentarioService;

    @Autowired
    private HistorialCambioService historialCambioService;

    @Autowired
    private ProyectoService proyectoService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioProyectoRepository usuarioProyectoRepository;

    @Autowired
    private NotificacionService notificacionService;

    @GetMapping("tareas")
    public String tareas(Model model, @AuthenticationPrincipal UserDetails userDetails,
                         @RequestParam(required = false) Long proyectoId,
                         @RequestParam(required = false) Long usuarioId,
                         @RequestParam(required = false) PrioridadTarea prioridad,
                         @RequestParam(required = false) String search){
        String cadena = "Tareas";

        model.addAttribute("tarea",cadena);

        // Obtener el usuario autenticado (puedes adaptar esto según tu autenticación real)
        String email = userDetails.getUsername();
        // TO DO

        //Usuario usuarioActual = usuarioService.buscarPorNombre(principal.getName()).get(0); // <-- cambia si usas otro sistema
        Usuario usuarioActual = usuarioRepository.findByEmail(email).orElseThrow(); // <-- cambia si usas otro sistema

        // Obtener proyectos a los que tiene acceso
        List<Proyecto> proyectosPermitidos = usuarioProyectoRepository.findByUsuarioId(usuarioActual.getId())
                .stream()
                .map(UsuarioProyecto::getProyecto)
                .toList();

        // Obtener tareas a las que tiene acceso
        List<Tarea> tareasFiltradas = tareaService.obtenerTareasFiltradas(
                usuarioActual.getId(), proyectoId, usuarioId, prioridad, search
        );

        if(proyectoId != null){
            // Verificar que el usuario tenga acceso al proyecto de la tarea
            boolean tieneAcceso = usuarioProyectoRepository.existsByUsuarioIdAndProyectoId(usuarioActual.getId(), proyectoId);
            if (!tieneAcceso && proyectoId != null) {
                proyectoId = proyectosPermitidos.get(0).getId();
                System.out.println(proyectoId);
            }
        }

        model.addAttribute("tareas",tareasFiltradas);

        return "tareas/tarea";
    }

    @GetMapping("crear")
    public String crear(Model model, Principal principal){
        String cadena = "Crear tarea";

        //Usuario usuarioActual = usuarioService.buscarPorNombre(principal.getName()).get(0); // <-- cambia si usas otro sistema
        Usuario usuarioActual = usuarioRepository.findByEmail(principal.getName()).orElseThrow(); // <-- cambia si usas otro sistema

        // Obtener proyectos a los que tiene acceso
        List<Proyecto> proyectosPermitidos = usuarioProyectoRepository.findByUsuarioId(usuarioActual.getId())
                .stream()
                .map(UsuarioProyecto::getProyecto)
                .toList();

        model.addAttribute("cadena",cadena);
        model.addAttribute("tarea", new Tarea());
        model.addAttribute("proyectos", proyectosPermitidos);
        return "tareas/crear";
    }

    @PostMapping("crear")
    public String tareacreada(@Valid Tarea tarea,
                                 BindingResult bindingResult,
                                 Model model){
        if(bindingResult.hasErrors()){
            for(ObjectError error:bindingResult.getAllErrors()){
                System.out.println("Error: "+error.getDefaultMessage());
            }
            return "tareas/crear";
        }

        tareaService.saveTarea(tarea);
        if (tarea.getUsuario() != null) {
            Notificacion notificacion = new Notificacion();
            notificacion.setUsuario(tarea.getUsuario());
            notificacion.setContenido("Se te ha asignado la tarea: " + tarea.getNombre());
            notificacion.setFecha(LocalDateTime.now());
            notificacion.setTarea(tarea);
            notificacion.setLeido(false);
            notificacionService.saveNotificacion(notificacion);
        }

        model.addAttribute("tareas",tareaService.getAllTareas());
        model.addAttribute("tarea","Tarea creada con éxito");

        return "tareas/tarea";
    }

    @GetMapping("ver/{id}")
    public String ver(Model model,@PathVariable Long id){
        String cadena = "Ver tarea";
        Tarea tarea = tareaService.getTareaById(id).get();
        List<Comentario> comentarios = comentarioService.obtenerComentariosPorTarea(id);
        //List<HistorialCambio> historialCambio = historialCambioService.getHistorialCambioById(id).stream().toList();
        List<HistorialCambio> historialCambio = historialCambioService.getHistorialCambioByTareaId(id).stream().toList();

        // TO DO
        //  Cargar id de usuario desde la sesión
        Usuario usuario = usuarioService.getUsuarioById(1L).orElseThrow();
        model.addAttribute("usuarioActual",usuario);


        // Modifica la URL del archivo para apuntar a la nueva URL accesible
        for (Comentario comentario : comentarios) {
            if (comentario.getArchivoUrl() != null) {
                // Aquí asumimos que comentario.getArchivoUrl() contiene el nombre del archivo
                comentario.setArchivoUrl(comentario.getArchivoUrl());
            }
        }

        model.addAttribute("cadena",cadena);
        model.addAttribute("tarea",tarea);
        model.addAttribute("comentarios",comentarios);
        model.addAttribute("historialCambios",historialCambio);


        return "tareas/ver";
    }

    // Obtener la tarea para editar
    @GetMapping("/editar/{id}")
    public String editarTarea(@PathVariable Long id, Model model) {
        Tarea tarea = tareaService.getTareaById(id).orElseThrow();
        List<HistorialCambio> historialCambios = historialCambioService.getHistorialCambioByTareaId(id).stream().toList();
        List<Proyecto> proyectos = proyectoService.getAllProyectos();

        model.addAttribute("tarea", tarea);
        model.addAttribute("historialCambios", historialCambios);
        model.addAttribute("proyectos", proyectos);
        model.addAttribute("usuarios", usuarioService.getAllUsuarios()); // Si deseas mostrar los usuarios

        return "tareas/editar"; // Nombre de la plantilla
    }

    // Modificar tarea
    @PostMapping("/editar/{id}")
    public String modificarTarea(@PathVariable Long id,
                                 @RequestParam String nombre,
                                 @RequestParam String descripcion,
                                 @RequestParam PrioridadTarea prioridadTarea,
                                 @RequestParam EstadoTarea estado,
                                 @RequestParam LocalDate fechaLimite,
                                 @RequestParam Long proyectoId,
                                 @RequestParam Long usuarioId) {

        Tarea tarea = tareaService.getTareaById(id).orElseThrow();
        Usuario usuario = usuarioService.getUsuarioById(usuarioId).orElseThrow();

        Proyecto proyecto = proyectoService.getProyectoById(proyectoId).orElseThrow();

        tarea.setNombre(nombre);
        tarea.setDescripcion(descripcion);
        tarea.setEstado(estado);
        tarea.setFechaLimite(fechaLimite);
        tarea.setProyecto(proyecto);
        tarea.setPrioridad(prioridadTarea);

        // Actualiza la tarea
        tareaService.saveTarea(tarea);

        // Registrar en el historial de cambios
        HistorialCambio historial = new HistorialCambio();
        historial.setAccion("Tarea modificada");
        historial.setFecha(LocalDate.now());
        historial.setTarea(tarea);
        historial.setUsuario(usuario);
        historialCambioService.saveHistorialCambio(historial);

        if (tarea.getUsuario() != null) {
            Notificacion notificacion = new Notificacion();
            notificacion.setUsuario(tarea.getUsuario());
            notificacion.setContenido("Se ha editado la tarea: " + tarea.getNombre());
            notificacion.setFecha(LocalDateTime.now());
            notificacion.setTarea(tarea);
            notificacion.setLeido(false);
            notificacionService.saveNotificacion(notificacion);
        }

        return "redirect:/tarea/ver/" + id; // Redirige al detalle de la tarea
    }

    @DeleteMapping("/eliminar/{id}")
    public String eliminarTarea(@PathVariable Long id, RedirectAttributes redirectAttributes, Model model) {
        tareaService.deleteTarea(id);
        String cadena = "Tarea eliminada con éxito";
        model.addAttribute("tarea",cadena);
        model.addAttribute("tareas",tareaService.getAllTareas());
        return "tareas/tarea";
    }

}
