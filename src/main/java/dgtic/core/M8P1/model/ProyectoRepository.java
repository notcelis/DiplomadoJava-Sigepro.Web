package dgtic.core.M8P1.model;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ProyectoRepository extends JpaRepository<Proyecto,Long> {

    // Buscar proyectos por nombre
    List<Proyecto> findByNombreContaining(String nombre);

    // Buscar proyectos por creadorId
    List<Proyecto> findByCreadorId(Integer creadorId);

    // Buscar proyectos creados después de cierta fecha
    List<Proyecto> findByFechaCreacionAfter(LocalDate fechaCreacion);

    // NamedQuery definida en la entidad Proyecto
    @Query(name = "Proyecto.findByCreador")
    List<Proyecto> buscarProyectosPorCreador(@Param("creadorId") Integer creadorId);

    // JPQL: Buscar proyectos que contengan un nombre específico
    @Query("SELECT p FROM Proyecto p WHERE p.nombre LIKE %:nombre%")
    List<Proyecto> buscarProyectosPorNombre(@Param("nombre") String nombre);

    // Método para encontrar proyectos por nombre (ejemplo de uso de CrudRepository)
    List<Proyecto> findByNombreContainingIgnoreCase(String nombre);

    // Método para listar proyectos con paginación
    Page<Proyecto> findAll(Pageable pageable);

    List<Proyecto> findByNombreContaining(String nombre, Pageable pageable);

}
