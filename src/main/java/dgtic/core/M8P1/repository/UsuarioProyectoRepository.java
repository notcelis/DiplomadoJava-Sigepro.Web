package dgtic.core.M8P1.repository;

import dgtic.core.M8P1.model.Tarea;
import dgtic.core.M8P1.model.UsuarioProyecto;
import dgtic.core.M8P1.model.UsuarioProyectoId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsuarioProyectoRepository extends JpaRepository<UsuarioProyecto, Long> {
    List<UsuarioProyecto> findByUsuarioId(Long usuarioId);

    List<UsuarioProyecto> findByProyectoIdIn(List<Long> proyectoIds);
    List<UsuarioProyecto> findByProyectoId(Long proyectoId);

    UsuarioProyecto findByProyectoIdAndUsuarioId(Long proyectoId, Long usuarioId);

    boolean existsById(UsuarioProyectoId id);
    void deleteById(UsuarioProyectoId id);

    boolean existsByUsuarioIdAndProyectoId(Long usuarioId, Long proyectoId);


}
