package dgtic.core.M8P1.controller;

import dgtic.core.M8P1.model.*;
import dgtic.core.M8P1.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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

    @GetMapping("tareas")
    public String tareas(Model model){
        String cadena = "Tareas";
        model.addAttribute("tarea",cadena);
        model.addAttribute("tareas",tareaService.getAllTareas());
        return "tareas/tarea";
    }

    @GetMapping("crear")
    public String crear(Model model){
        String cadena = "Crear tarea";
        model.addAttribute("cadena",cadena);
        model.addAttribute("tarea", new Tarea());
        return "tareas/crear";
    }

    @PostMapping("crear")
    public String tareacreada(@Valid Tarea tarea,
                                 BindingResult bindingResult,
                                 Model model){
        if(bindingResult.hasErrors()){
            for(ObjectError error:bindingResult.getAllErrors()){
                System.out.println("Error: "+error.getDefaultMessage());
                System.out.println("======================================================" +
                        tarea +
                        "================================================================");
            }
            return "tareas/crear";
        }

        tareaService.saveTarea(tarea);
        model.addAttribute("tareas",tareaService.getAllTareas());
        model.addAttribute("tarea","Tarea creada con éxito");

        return "tareas/tarea";
    }

    @GetMapping("ver/{id}")
    public String ver(Model model,@PathVariable Long id){
        String cadena = "Ver tarea";
        Tarea tarea = tareaService.getTareaById(id).get();
        List<Comentario> comentarios = comentarioService.obtenerComentariosPorTarea(id);
        List<HistorialCambio> historialCambio = historialCambioService.getHistorialCambioById(id).stream().toList();

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
        List<HistorialCambio> historialCambios = historialCambioService.getHistorialCambioById(id).stream().toList();

        model.addAttribute("tarea", tarea);
        model.addAttribute("historialCambios", historialCambios);
        model.addAttribute("usuarios", usuarioService.getAllUsuarios()); // Si deseas mostrar los usuarios

        return "tareas/editar"; // Nombre de la plantilla
    }

    // Modificar tarea
    @PostMapping("/editar/{id}")
    public String modificarTarea(@PathVariable Long id,
                                 @RequestParam String nombre,
                                 @RequestParam String descripcion,
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

        // Actualiza la tarea
        tareaService.saveTarea(tarea);

        // Registrar en el historial de cambios
        HistorialCambio historial = new HistorialCambio();
        historial.setAccion("Tarea modificada");
        historial.setFecha(LocalDate.now());
        historial.setTarea(tarea);
        historial.setUsuario(usuario);
        historialCambioService.saveHistorialCambio(historial);

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
