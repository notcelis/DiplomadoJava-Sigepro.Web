package dgtic.core.M8P1.controller;

import dgtic.core.M8P1.model.Tarea;
import dgtic.core.M8P1.service.ProyectoService;
import dgtic.core.M8P1.service.TareaService;
import dgtic.core.M8P1.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    private UsuarioService usuarioService;

    @GetMapping("home")
    public String home(
            @RequestParam(required = false) Integer proyectoId,
            @RequestParam(required = false) Integer usuarioId,
            @RequestParam(required = false) String prioridad,
            @RequestParam(required = false) String search,
            Model model) {

        List<Tarea> tareas = tareaService.getTareasFiltradas(proyectoId, usuarioId, prioridad, search);
        Map<String, List<Tarea>> tareasPorEstado = tareas.stream()
                .collect(Collectors.groupingBy(t -> t.getEstado().name()));

        model.addAttribute("tareas", tareasPorEstado);
        model.addAttribute("proyectos", proyectoService.getAllProyectos());
        model.addAttribute("usuarios", usuarioService.getAllUsuarios());

        model.addAttribute("proyectoSeleccionado", proyectoId);
        model.addAttribute("usuarioSeleccionado", usuarioId);
        model.addAttribute("prioridadSeleccionada", prioridad);
        model.addAttribute("busqueda", search);



//            Model model){
//        model.addAttribute("bienvenida","¿En qué trabajaremos hoy?");
//
//        List<Tarea> tareas = tareaService.getAllTareas();
//        Map<String, List<Tarea>> tareasPorEstado = tareas.stream()
//                .collect(Collectors.groupingBy(t -> t.getEstado().name()));
//        model.addAttribute("tareas", tareasPorEstado);
//        System.out.println(tareasPorEstado);


        return "home/home";
    }
    @GetMapping("/login")
    public String login(){
        return "inicio";
    }

}
