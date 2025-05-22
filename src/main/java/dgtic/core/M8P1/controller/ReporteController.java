package dgtic.core.M8P1.controller;

import dgtic.core.M8P1.model.Proyecto;
import dgtic.core.M8P1.model.Reporte;
import dgtic.core.M8P1.model.Tarea;
import dgtic.core.M8P1.service.ProyectoService;
import dgtic.core.M8P1.service.ReportePDFService;
import dgtic.core.M8P1.service.ReporteService;
import dgtic.core.M8P1.service.TareaService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("reporte")
public class ReporteController {

    @Autowired
    ReporteService reporteService;

    @Autowired
    private TareaService tareaService;

    @Autowired
    private ProyectoService proyectoService;

    @Autowired
    private ReportePDFService reportePDFService;

    @GetMapping("ver")
    public void ver(Model model, @RequestParam("proyectoId") Long proyectoId, HttpServletResponse response) throws IOException {

        List<Tarea> tareas = tareaService.obtenerTareasPorProyecto(proyectoId);
        String nombreProyecto = proyectoService.getProyectoById(proyectoId).orElseThrow().getNombre();

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=tareas_proyecto_" + proyectoId + ".pdf");

        // Llamada al servicio que ya creaste
        ByteArrayInputStream pdfStream = reportePDFService.generarReporteTareas(tareas, nombreProyecto);
        // Escribir el contenido del PDF al OutputStream del response
        IOUtils.copy(pdfStream, response.getOutputStream());
        response.getOutputStream().flush();

    }

    @GetMapping("crear")
    public String crear(Model model){
        String cadena = "Crear reporte";
        List<Proyecto> proyectos = proyectoService.getAllProyectos(); // O los del usuario autenticado
        model.addAttribute("info",cadena);
        model.addAttribute("proyectos", proyectos);
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
