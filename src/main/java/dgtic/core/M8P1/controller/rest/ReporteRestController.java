package dgtic.core.M8P1.controller.rest;

import dgtic.core.M8P1.model.Reporte;
import dgtic.core.M8P1.service.ReporteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/reportes")
public class ReporteRestController {

    @Autowired
    ReporteService reporteService;

    @GetMapping
    public ResponseEntity<List<Reporte>> getAllReportes() {
        return ResponseEntity.ok(reporteService.getAllReportes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reporte> getReporteById(@PathVariable Long id) {
        return reporteService.getReporteById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> createReporte(@Valid @RequestBody Reporte reporte, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getAllErrors().stream()
                    .map(ObjectError::getDefaultMessage)
                    .collect(Collectors.toList());
            return ResponseEntity.badRequest().body(errors);
        }
        Reporte savedReporte = reporteService.saveReporte(reporte);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedReporte.getId())
                .toUri();
        return ResponseEntity.created(location).body(savedReporte);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateReporte(@PathVariable Long id, @Valid @RequestBody Reporte reporteDetails, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getAllErrors().stream()
                    .map(ObjectError::getDefaultMessage)
                    .collect(Collectors.toList());
            return ResponseEntity.badRequest().body(errors);
        }
        return reporteService.getReporteById(id)
                .map(reporte -> {
                    reporte.setProyecto(reporteDetails.getProyecto());
                    reporte.setContenido(reporteDetails.getContenido());
                    // Puedes agregar más campos para actualizar aquí
                    Reporte updatedReporte = reporteService.saveReporte(reporte);
                    return ResponseEntity.ok(updatedReporte);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReporte(@PathVariable Long id) {
        if (reporteService.getReporteById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        reporteService.deleteReporte(id);
        return ResponseEntity.noContent().build();
    }
}