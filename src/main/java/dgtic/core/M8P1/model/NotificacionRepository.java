package dgtic.core.M8P1.model;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificacionRepository extends JpaRepository<Notificacion,Long> {
    List<Notificacion> findByUsuarioIdAndLeido(Long usuarioId, Boolean leido, Pageable pageable);

}
