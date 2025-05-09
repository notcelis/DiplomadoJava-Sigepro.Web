package dgtic.core.M8P1.service;

import dgtic.core.M8P1.model.Notificacion;
import dgtic.core.M8P1.model.TipoNotificacion;
import dgtic.core.M8P1.model.Usuario;
import dgtic.core.M8P1.repository.NotificacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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

    public void invitarUsuario(Usuario usuario, String contenido) {
        Notificacion notificacion = new Notificacion();
        notificacion.setUsuario(usuario);
        notificacion.setTipo(TipoNotificacion.Otro);
        notificacion.setContenido(contenido);
        notificacion.setFecha(LocalDateTime.now());
        notificacionRepository.save(notificacion);
    }
}

