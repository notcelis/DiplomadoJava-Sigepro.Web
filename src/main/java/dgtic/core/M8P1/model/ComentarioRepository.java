package dgtic.core.M8P1.model;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ComentarioRepository extends JpaRepository<Comentario,Long> {

    List<Comentario> findByTareaId(Long tareaId, Pageable pageable);

    @Query("SELECT c FROM Comentario c JOIN c.usuario u WHERE c.tarea.id = :tareaId")
    List<Comentario> findComentariosByTareaId(@Param("tareaId") int tareaId);

    List<Comentario> findComentariosByTareaId(Long tareaId);
}
