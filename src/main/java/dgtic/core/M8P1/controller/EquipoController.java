package dgtic.core.M8P1.controller;

import dgtic.core.M8P1.model.Equipo;
import dgtic.core.M8P1.service.EquipoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/usuario/equipos")
public class EquipoController {

    @Autowired
    private EquipoService equipoService;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("equipos", equipoService.listarEquipos());
        return "equipos/listar";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("equipo", new Equipo());
        return "equipos/formulario";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Equipo equipo, RedirectAttributes redirectAttributes) {
        equipoService.crearEquipo(equipo);
        redirectAttributes.addFlashAttribute("mensaje", "Equipo creado con éxito");
        return "redirect:/equipos";
    }
}
