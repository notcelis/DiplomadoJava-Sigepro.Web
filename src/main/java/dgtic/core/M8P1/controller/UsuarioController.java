package dgtic.core.M8P1.controller;

import dgtic.core.M8P1.model.Usuario;
import dgtic.core.M8P1.model.Invitacion;
import dgtic.core.M8P1.service.InvitacionService;
import dgtic.core.M8P1.service.NotificacionService;
import dgtic.core.M8P1.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private NotificacionService notificacionService;

    @Autowired
    private InvitacionService invitacionService;

    @GetMapping("invitar")
    public String invitar(Model model){
        String cadena = "Invitar usuarios";
        model.addAttribute("cadena",cadena);
        model.addAttribute("invitacion", new Invitacion());
        return "usuarios/invitar";
    }

    @PostMapping("invitar")
    public String enviarInvitacion(@ModelAttribute Invitacion invitacion,
                                   RedirectAttributes redirectAttributes) {
        invitacion.setEmail(invitacion.getEmail());
        invitacion.setToken(UUID.randomUUID().toString()); // <-- Asignación del token
        invitacion.setFechaInvitacion(LocalDateTime.now());
        invitacion.setAceptada(false);

        invitacionService.guardarInvitacion(invitacion);
        // Aquí enviar un correo con un enlace de activación

        redirectAttributes.addFlashAttribute("mensaje", "Invitación enviada a " + invitacion.getEmail());
        return "redirect:/usuario/invitar";
    }

//    @GetMapping("equipos")
//    public String equipos(Model model){
//        String cadena = "Ver equipos";
//        model.addAttribute("usuarios",cadena);
//        return "usuarios/equipo";
//    }

    @GetMapping("buscar")
    public String buscar(@RequestParam(required = false) String nombre, Model model){
        List<Usuario> resultados = (nombre != null && !nombre.isEmpty()) ?
                usuarioService.buscarPorNombre(nombre) :
                new ArrayList<>();

        System.out.println("====================== Debug ======================");
        String cadena = "Buscar usuario";
        model.addAttribute("usuarios", resultados);
        model.addAttribute("nombre", nombre);
        model.addAttribute("cadena",cadena);
        return "usuarios/buscar";
    }

//    @GetMapping("roles")
//    public String rol(Model model){
//        String cadena = "Rol";
//        model.addAttribute("usuarios",cadena);
//        return "usuarios/rol";
//    }

}
