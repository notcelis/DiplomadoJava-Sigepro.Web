package dgtic.core.M8P1.model;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TareaRepository extends JpaRepository<Tarea, Long> {

    // Buscar tareas por estado (Pendiente, En progreso, Completada)
    List<Tarea> findByEstado(String estado);

    // Buscar tareas de un proyecto específico
    List<Tarea> findByproyectoId(Integer proyectoId);

    // Buscar tareas con fecha límite antes de cierta fecha
    List<Tarea> findByfechaLimiteBefore(LocalDate fechaLimite);

    // NamedQuery definida en la entidad Tarea
    @Query(name = "Tarea.findByEstado")
    List<Tarea> buscarTareasPorEstado(@Param("estado") String estado);

    // JPQL: Buscar tareas de un proyecto con fecha límite antes de una fecha dada
    @Query("SELECT t FROM Tarea t WHERE t.proyecto = :proyectoId AND t.fechaLimite < :fechaLimite")
    List<Tarea> buscarTareasPorProyectoYFecha(@Param("proyectoId") Integer proyectoId, @Param("fechaLimite") LocalDate fechaLimite);

    List<Tarea> findByEstado(String estado, Pageable pageable);

    @Query("SELECT t FROM Tarea t WHERE t.proyecto.id IN (SELECT p.id FROM Proyecto p WHERE p.creador.id = :usuarioId)")
    List<Tarea> findTareasByUsuarioId(@Param("usuarioId") Long usuarioId);


}
