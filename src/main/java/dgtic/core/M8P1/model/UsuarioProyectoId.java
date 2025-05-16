package dgtic.core.M8P1.model;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioProyectoId implements Serializable {
    private Long usuarioId;
    private Long proyectoId;



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UsuarioProyectoId)) return false;
        UsuarioProyectoId that = (UsuarioProyectoId) o;
        return Objects.equals(usuarioId, that.usuarioId) &&
                Objects.equals(proyectoId, that.proyectoId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(usuarioId, proyectoId);
    }
}