package dgtic.core.M8P1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("usuario")
public class UsuarioController {

    @GetMapping("invitar")
    public String invitar(Model model){
        String cadena = "Invitar usuarios";
        model.addAttribute("usuarios",cadena);
        return "usuarios/invitar";
    }

    @GetMapping("equipos")
    public String equipos(Model model){
        String cadena = "Ver equipos";
        model.addAttribute("usuarios",cadena);
        return "usuarios/equipo";
    }

    @GetMapping("buscar")
    public String buscar(Model model){
        String cadena = "Buscar";
        model.addAttribute("usuarios",cadena);
        return "usuarios/buscar";
    }

    @GetMapping("roles")
    public String rol(Model model){
        String cadena = "Rol";
        model.addAttribute("usuarios",cadena);
        return "usuarios/rol";
    }

}
