package dgtic.core.M8P1.controller;

import dgtic.core.M8P1.service.RolService;
import dgtic.core.M8P1.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/usuario/roles")
public class RolController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private RolService rolService;


    @GetMapping
    public String mostrarFormularioAsignacion(Model model) {
        String cadena = "Roles";
        model.addAttribute("cadena", cadena);
        model.addAttribute("usuarios", usuarioService.getAllUsuarios());
        model.addAttribute("roles", rolService.getAllRoles());
        return "usuarios/rol";
    }

    @PostMapping
    public String asignarRol(@RequestParam Long usuarioId, @RequestParam Long rolId) {
        usuarioService.asignarRol(usuarioId, rolId);
        return "redirect:/usuario/roles?success";
    }
}
