package dgtic.core.M8P1.controller;

import dgtic.core.M8P1.model.Reporte;
import dgtic.core.M8P1.model.Tarea;
import dgtic.core.M8P1.service.ReporteService;
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
@RequestMapping("reporte")
public class ReporteController {

    @Autowired
    ReporteService reporteService;

    @GetMapping("ver")
    public String ver(Model model){
        String cadena = "Reportes";
        model.addAttribute("reporte",cadena);
        model.addAttribute("reportes",reporteService.getAllReportes());
        return "reportes/reporte";
    }

    @GetMapping("crear")
    public String crear(Model model){
        String cadena = "Crear reporte";
        model.addAttribute("info",cadena);
        model.addAttribute("reporte", new Reporte());
        return "reportes/crear";
    }

    @PostMapping("crear")
    public String reportecreado(@Valid Reporte reporte,
                              BindingResult bindingResult,
                              Model model){
        if(bindingResult.hasErrors()){
            for(ObjectError error:bindingResult.getAllErrors()){
                System.out.println("Error: "+error.getDefaultMessage());
                System.out.println("======================================================" +
                        reporte +
                        "================================================================");
            }
            return "reportes/crear";
        }

        reporteService.saveReporte(reporte);
        model.addAttribute("reportes",reporteService.getAllReportes());
        model.addAttribute("reporte","Reporte creado con éxito");
        return "reportes/reporte";
    }

}
