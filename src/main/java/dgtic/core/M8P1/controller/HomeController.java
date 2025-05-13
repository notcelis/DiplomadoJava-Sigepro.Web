package dgtic.core.M8P1.controller;

import dgtic.core.M8P1.model.Tarea;
import dgtic.core.M8P1.service.TareaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/home")
public class HomeController {

    @Autowired
    private TareaService tareaService;

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

    @GetMapping("perfil/{usuario}")
    public String perfil(Model model,
                           @PathVariable("usuario")String usuario){
        String cadena = "Perfil "+ usuario;
        model.addAttribute("perfil",cadena);
        return "perfil/perfil";
    }

}
