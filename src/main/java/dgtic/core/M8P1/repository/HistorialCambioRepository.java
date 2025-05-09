package dgtic.core.M8P1.repository;

import dgtic.core.M8P1.model.HistorialCambio;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface HistorialCambioRepository extends JpaRepository<HistorialCambio,Long> {
    List<HistorialCambio> findByUsuarioId(Long usuarioId, Pageable pageable);

    @Query("SELECT h FROM HistorialCambio h JOIN h.tarea t WHERE h.usuario.id = :usuarioId")
    List<HistorialCambio> findHistorialByUsuarioId(@Param("usuarioId") int usuarioId);
}
