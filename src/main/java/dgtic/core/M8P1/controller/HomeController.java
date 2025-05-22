package dgtic.core.M8P1.controller;

import dgtic.core.M8P1.model.Notificacion;
import dgtic.core.M8P1.model.Tarea;
import dgtic.core.M8P1.model.Usuario;
import dgtic.core.M8P1.repository.NotificacionRepository;
import dgtic.core.M8P1.repository.UsuarioRepository;
import dgtic.core.M8P1.security.SecurityConfig;
import dgtic.core.M8P1.service.TareaService;
import dgtic.core.M8P1.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/home")
public class HomeController {

    @Autowired
    private TareaService tareaService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private  SecurityConfig securityConfig;

//    @GetMapping("tareas/{tarea}")
//    public String tareas(Model model,
//                         @PathVariable("tarea")String tarea){
//        String cadena = "Tarea "+ tarea;
//        model.addAttribute("tarea",cadena);
//        return "tareas/tarea";
//    }

    @GetMapping("proyectos/{proyecto}")
    public String proyectos(Model model,
                         @PathVariable("proyecto")String proyecto){
        String cadena = "Proyecto "+ proyecto;
        model.addAttribute("proyecto",cadena);
        return "proyectos/proyecto";
    }

    @GetMapping("reportes/{reporte}")
    public String reportes(Model model,
                            @PathVariable("reporte")String reporte){
        String cadena = "Reporte "+ reporte;
        model.addAttribute("reporte",cadena);
        return "reportes/reporte";
    }

    @GetMapping("usuarios/{usuario}")
    public String usuarios(Model model,
                           @PathVariable("usuario")String usuario){
        String cadena = "Usuarios "+ usuario;
        model.addAttribute("usuarios",cadena);
        return "usuarios/usuario";
    }

    @GetMapping("perfil")
    public String perfil(Model model,
                         Principal principal){
        Usuario usuario = usuarioRepository.findByEmail(principal.getName()).orElseThrow();
        model.addAttribute("usuario", usuario);
        return "perfil/perfil";
    }

    @GetMapping("/configuracion")
    public String configuracion(Model model, Principal principal) {
        Usuario usuario = usuarioRepository.findByEmail(principal.getName()).orElseThrow();
        model.addAttribute("usuario", usuario);
        return "perfil/configuracion";
    }

    @PostMapping("/actualizar")
    public String actualizarUsuario(@ModelAttribute("usuario") Usuario usuarioForm,
                                    @RequestParam("actualPassword") String actualPassword,
                                    @RequestParam(value = "nuevaPassword", required = false) String nuevaPassword,
                                    @RequestParam(value = "confirmarPassword", required = false) String confirmarPassword,
                                    Principal principal,
                                    RedirectAttributes redirectAttributes) {

        Usuario usuarioActual = usuarioRepository.findByEmail(principal.getName()).orElseThrow();

        // Verificar la contraseña actual
        PasswordEncoder passwordEncoder = securityConfig.passwordEncoder();
        if (!passwordEncoder.matches(actualPassword, usuarioActual.getContraseña())) {
            redirectAttributes.addFlashAttribute("error", "La contraseña actual es incorrecta.");
            return "redirect:/usuario/configuracion";
        }

        // Cambiar nombre y email
        usuarioActual.setNombre(usuarioForm.getNombre());
        usuarioActual.setEmail(usuarioForm.getEmail());

        // Si se ingresó una nueva contraseña, verificarla
        if (nuevaPassword != null && !nuevaPassword.isEmpty()) {
            if (!nuevaPassword.equals(confirmarPassword)) {
                redirectAttributes.addFlashAttribute("error", "Las contraseñas no coinciden.");
                return "redirect:/usuario/configuracion";
            }
            usuarioActual.setContraseña(passwordEncoder.encode(nuevaPassword));
        }

        usuarioService.saveUsuario(usuarioActual);
        redirectAttributes.addFlashAttribute("success", "Datos actualizados correctamente.");
        return "redirect:/home/configuracion";
    }

}
