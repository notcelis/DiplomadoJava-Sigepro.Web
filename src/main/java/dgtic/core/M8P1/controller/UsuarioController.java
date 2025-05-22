package dgtic.core.M8P1.controller;

import dgtic.core.M8P1.model.Rol;
import dgtic.core.M8P1.model.Usuario;
import dgtic.core.M8P1.model.Invitacion;
import dgtic.core.M8P1.repository.InvitacionRepository;
import dgtic.core.M8P1.repository.UsuarioRepository;
import dgtic.core.M8P1.security.SecurityConfig;
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
import java.util.Optional;
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

    @Autowired
    private InvitacionRepository invitacionRepository;

    @Autowired
    private SecurityConfig securityConfig;


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
        invitacionService.enviarInvitacion(invitacion.getEmail(), invitacion.getToken());

        redirectAttributes.addFlashAttribute("mensaje", "Invitación enviada a " + invitacion.getEmail());
        return "redirect:/usuario/invitar";
    }


    @GetMapping("buscar")
    public String buscar(@RequestParam(required = false) String nombre, Model model){
        List<Usuario> resultados = (nombre != null && !nombre.isEmpty()) ?
                usuarioService.buscarPorNombre(nombre) :
                new ArrayList<>();

        String cadena = "Buscar usuario";
        model.addAttribute("usuarios", resultados);
        model.addAttribute("nombre", nombre);
        model.addAttribute("cadena",cadena);
        return "usuarios/buscar";
    }

    @GetMapping("/registro")
    public String mostrarFormularioRegistro(@RequestParam("token") String token, Model model) {
        Optional<Invitacion> invitacionOpt = invitacionRepository.findByToken(token);

        if (invitacionOpt.isEmpty() || invitacionOpt.get().isAceptada()) {
            model.addAttribute("mensaje", "El enlace de registro no es válido o ha expirado.");
            return "registro_invalido"; // crea una vista simple para este caso
        }

        model.addAttribute("token", token);
        model.addAttribute("usuario", new Usuario());
        model.addAttribute("email", invitacionOpt.orElseThrow().getEmail());
        return "usuarios/registro"; // nombre del archivo .html de registro
    }

    @PostMapping("/registro")
    public String procesarRegistro(
            @RequestParam("token") String token,
            @ModelAttribute("usuario") Usuario usuario,
            RedirectAttributes redirectAttributes) {

        Optional<Invitacion> invitacionOpt = invitacionRepository.findByToken(token);

        if (invitacionOpt.isEmpty() || invitacionOpt.get().isAceptada()) {

            redirectAttributes.addFlashAttribute("mensajeError", "El enlace de registro no es válido o ha expirado.");
            return "redirect:/usuario/registro?token=" + token;
        }

        Invitacion invitacion = invitacionOpt.get();

        // Verificamos que el correo del formulario sea igual al de la invitación
        if (!invitacion.getEmail().equalsIgnoreCase(usuario.getEmail())) {
            redirectAttributes.addFlashAttribute("mensajeError", "El correo no coincide con el de la invitación.");
            return "redirect:/usuario/registro?token=" + token;
        }

        // Crear y guardar el usuario
        Usuario usuarioNuevo = new Usuario();
        usuarioNuevo.setNombre(usuario.getNombre());
        usuarioNuevo.setEmail(usuario.getEmail());
        usuarioNuevo.setContraseña(securityConfig.passwordEncoder().encode(usuario.getContraseña()));
        usuarioNuevo.setRol(new Rol(4L,"Invitado")); // si tu invitación define el rol
        usuarioService.saveUsuario(usuarioNuevo);

        // Marcar invitación como usada
        invitacion.setAceptada(true);
        invitacionRepository.save(invitacion);

        redirectAttributes.addFlashAttribute("mensajeExito", "Registro completado. Ya puedes iniciar sesión.");
        return "redirect:/login";
    }


}
