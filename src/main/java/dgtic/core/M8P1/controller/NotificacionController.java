package dgtic.core.M8P1.controller;

import dgtic.core.M8P1.model.Notificacion;
import dgtic.core.M8P1.model.Usuario;
import dgtic.core.M8P1.repository.UsuarioRepository;
import dgtic.core.M8P1.service.NotificacionService;
import dgtic.core.M8P1.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/notificaciones")
public class NotificacionController {

    @Autowired
    private NotificacionService notificacionService;

    @Autowired
    private UsuarioRepository usuarioRepository; // para obtener el usuario logueado

    @GetMapping
    public String verNotificaciones(Model model, Principal principal) {
        Usuario usuario = usuarioRepository.findByEmail(principal.getName()).orElseThrow();
        List<Notificacion> notificaciones = notificacionService.obtenerNoLeidasPorUsuario(usuario.getId());
        model.addAttribute("notificaciones", notificaciones);
        return "notificaciones/lista"; // tu vista
    }

    @PostMapping("/marcar-leida/{id}")
    public String marcarComoLeida(@PathVariable Long id) {
        Optional<Notificacion> notificacion = notificacionService.getNotificacionById(id);
        notificacion.ifPresent(n -> {
            n.setLeido(true);
            notificacionService.saveNotificacion(n);
        });
        return "redirect:/tarea/ver/" + notificacion.orElseThrow().getTarea().getId();
    }

    @ModelAttribute("notificaciones")
    public List<Notificacion> notificacionesDelUsuarioAutenticado(Authentication authentication) {
        if (authentication == null) return List.of();
        Usuario usuario = usuarioRepository.findByEmail(authentication.getName()).orElseThrow();
        return notificacionService.obtenerNoLeidasPorUsuario(usuario.getId());
    }

    @GetMapping("/marcar-leido/{id}")
    public String marcarComoLeido(@PathVariable Long id) {
        Optional<Notificacion> notificacion = notificacionService.getNotificacionById(id);
        notificacion.ifPresent(n -> {
            n.setLeido(true);
            notificacionService.saveNotificacion(n);
        });
        return "redirect:/tarea/ver/" + notificacion.orElseThrow().getTarea().getId();
    }
}
