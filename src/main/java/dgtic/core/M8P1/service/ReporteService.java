package dgtic.core.M8P1.service;

import dgtic.core.M8P1.model.Reporte;
import dgtic.core.M8P1.repository.ReporteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReporteService {
    @Autowired
    private ReporteRepository reporteRepository;

    public List<Reporte> getAllReportes() {
        return (List<Reporte>) reporteRepository.findAll();
    }

    public Optional<Reporte> getReporteById(Long id) {
        return reporteRepository.findById(id);
    }

    public Reporte saveReporte(Reporte reporte) {
        return reporteRepository.save(reporte);
    }

    public void deleteReporte(Long id) {
        reporteRepository.deleteById(id);
    }
}

