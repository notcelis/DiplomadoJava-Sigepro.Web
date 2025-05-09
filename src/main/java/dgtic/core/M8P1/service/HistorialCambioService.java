package dgtic.core.M8P1.service;

import dgtic.core.M8P1.model.HistorialCambio;
import dgtic.core.M8P1.repository.HistorialCambioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HistorialCambioService {
    @Autowired
    private HistorialCambioRepository historialCambioRepository;

    public List<HistorialCambio> getAllHistorialCambios() {
        return (List<HistorialCambio>) historialCambioRepository.findAll();
    }

    public Optional<HistorialCambio> getHistorialCambioById(Long id) {
        return historialCambioRepository.findById(id);
    }


    public HistorialCambio saveHistorialCambio(HistorialCambio historialCambio) {
        return historialCambioRepository.save(historialCambio);
    }

    public void deleteHistorialCambio(Long id) {
        historialCambioRepository.deleteById(id);
    }
}

