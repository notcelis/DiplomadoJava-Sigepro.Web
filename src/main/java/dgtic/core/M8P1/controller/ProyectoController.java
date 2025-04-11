package dgtic.core.M8P1.controller;

import dgtic.core.M8P1.model.Proyecto;
import dgtic.core.M8P1.model.Tarea;
import dgtic.core.M8P1.service.ProyectoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/proyecto")
public class ProyectoController {

    @Autowired
    ProyectoService proyectoService;

    @GetMapping("ver")
    public String ver(Model model){
        String cadena = "Proyectos";
        model.addAttribute("proyecto",cadena);
        model.addAttribute("proyectos",proyectoService.getAllProyectos());
        return "proyectos/proyecto";
    }

    @GetMapping("crear")
    public String crear(Model model){
        String cadena = "Crear proyecto";
        model.addAttribute("proyectos",cadena);
        model.addAttribute("proyecto", new Proyecto());
        return "proyectos/crear";
    }

    @PostMapping("crear")
    public String proyectocreado(@Valid Proyecto proyecto,
                              BindingResult bindingResult,
                              Model model){
        if(bindingResult.hasErrors()){
            for(ObjectError error:bindingResult.getAllErrors()){
                System.out.println("Error: "+error.getDefaultMessage());
                System.out.println("======================================================" +
                        proyecto +
                        "================================================================");
            }
            return "proyectos/crear";
        }

        proyectoService.saveProyecto(proyecto);
        model.addAttribute("proyectos",proyectoService.getAllProyectos());
        model.addAttribute("proyecto","Proyecto creado con éxito");
        return "proyectos/proyecto";
    }
}
