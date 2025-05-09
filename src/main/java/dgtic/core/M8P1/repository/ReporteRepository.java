package dgtic.core.M8P1.repository;

import dgtic.core.M8P1.model.Reporte;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReporteRepository extends JpaRepository<Reporte,Long> {

    // Buscar reportes por tipo
    List<Reporte> findByTipo(String tipo);

    // Buscar reportes de un proyecto específico
    List<Reporte> findByproyectoId(Integer proyectoId);

    // Buscar reportes creados después de cierta fecha
    List<Reporte> findByFechaAfter(LocalDate fecha);

    // NamedQuery definida en la entidad Reporte
    @Query(name = "Reporte.findByTipo")
    List<Reporte> buscarReportesPorTipo(@Param("tipo") String tipo);

    // JPQL: Buscar reportes de un proyecto con contenido específico
    @Query("SELECT r FROM Reporte r WHERE r.proyecto = :proyectoId AND r.contenido LIKE %:contenido%")
    List<Reporte> buscarReportesPorProyectoYContenido(@Param("proyectoId") Integer proyectoId, @Param("contenido") String contenido);

    List<Reporte> findByTipo(String tipo, Pageable pageable);

}
