package dgtic.core.M8P1.model;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario,Long> {

    // Buscar usuario por email (único)
    Optional<Usuario> findByEmail(String email);

    // Buscar usuarios por rol
    List<Usuario> findByRolId(Integer rolId);

    // Buscar usuarios cuyo nombre contenga un texto
    List<Usuario> findByNombreContaining(String nombre);

    // NamedQuery definida en la entidad Usuario
    @Query(name = "Usuario.findByRol")
    List<Usuario> buscarUsuariosPorRol(@Param("rolId") Integer rolId);

    // JPQL: Buscar usuarios cuyo email contenga cierto dominio
    @Query("SELECT u FROM Usuario u WHERE u.email LIKE %:dominio%")
    List<Usuario> buscarUsuariosPorDominioEmail(@Param("dominio") String dominio);

    // Obtener usuarios por rol con paginación
    Page<Usuario> findByRolId(Long rolId, Pageable pageable);

}
