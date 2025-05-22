package dgtic.core.M8P1.controller;

import dgtic.core.M8P1.model.*;
import dgtic.core.M8P1.repository.NotificacionRepository;
import dgtic.core.M8P1.repository.TareaRepository;
import dgtic.core.M8P1.repository.UsuarioProyectoRepository;
import dgtic.core.M8P1.repository.UsuarioRepository;
import dgtic.core.M8P1.service.ProyectoService;
import dgtic.core.M8P1.service.TareaService;
import dgtic.core.M8P1.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class InicioController {

    @Value("${message.application}")
    private String message;

    @Autowired
    private TareaService tareaService;

    @Autowired
    private ProyectoService proyectoService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioProyectoRepository usuarioProyectoRepository;

    @Autowired
    private TareaRepository tareaRepository;

    @Autowired
    private NotificacionRepository notificacionRepository;

    @ModelAttribute("notificaciones")
    public List<Notificacion> cargarNotificacionesDelUsuario(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return Collections.emptyList();
        }

        String email = authentication.getName();
        Usuario usuario = usuarioRepository.findByEmail(email).orElseThrow();
        System.out.println(notificacionRepository.findByUsuarioAndLeidoFalseOrderByFechaDesc(usuario));
        return notificacionRepository.findByUsuarioAndLeidoFalseOrderByFechaDesc(usuario);
    }


    @GetMapping("home")
    public String home(
            @RequestParam(required = false) Long proyectoId,
            @RequestParam(required = false) Long usuarioId,
            @RequestParam(required = false) PrioridadTarea prioridad,
            @RequestParam(required = false) String search,
            @AuthenticationPrincipal UserDetails userDetails,
            Model model) {

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

        Map<String, List<Tarea>> tareasPorEstado = tareasFiltradas.stream()
                .collect(Collectors.groupingBy(t -> t.getEstado().name()));



        model.addAttribute("tareas", tareasPorEstado);
        model.addAttribute("proyectos", proyectosPermitidos);
        model.addAttribute("usuarios", usuarioService.getAllUsuarios());

        model.addAttribute("proyectoSeleccionado", proyectoId);
        model.addAttribute("usuarioSeleccionado", usuarioId);
        model.addAttribute("prioridadSeleccionada", prioridad);
        model.addAttribute("busqueda", search);

        return "home/home";
    }
    @GetMapping("/login")
    public String login(){
        return "inicio";
    }

}
