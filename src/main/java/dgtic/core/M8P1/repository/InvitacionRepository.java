package dgtic.core.M8P1.repository;

import dgtic.core.M8P1.model.Invitacion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InvitacionRepository extends JpaRepository<Invitacion, Long> {
    Optional<Invitacion> findByToken(String token);
    Optional<Invitacion> findByEmail(String correo);
}
