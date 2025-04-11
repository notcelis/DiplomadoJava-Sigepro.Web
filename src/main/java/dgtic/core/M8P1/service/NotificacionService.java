package dgtic.core.M8P1.service;

import dgtic.core.M8P1.model.Notificacion;
import dgtic.core.M8P1.model.NotificacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NotificacionService {
    @Autowired
    private NotificacionRepository notificacionRepository;

    public List<Notificacion> getAllNotificaciones() {
        return (List<Notificacion>) notificacionRepository.findAll();
    }

    public Optional<Notificacion> getNotificacionById(Long id) {
        return notificacionRepository.findById(id);
    }

    public Notificacion saveNotificacion(Notificacion notificacion) {
        return notificacionRepository.save(notificacion);
    }

    public void deleteNotificacion(Long id) {
        notificacionRepository.deleteById(id);
    }
}

