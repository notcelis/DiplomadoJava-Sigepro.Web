package dgtic.core.M8P1.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Entity
@Table(name = "tareas")
@Data
@NoArgsConstructor
@AllArgsConstructor
@NamedQuery(name = "Tarea.findByEstado",
        query = "SELECT t FROM Tarea t WHERE t.estado = :estado")
public class Tarea {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "proyectoId")
    @NotNull(message = "El proyecto es obligatorio")
    private Proyecto proyecto;

    @ManyToOne
    @JoinColumn(name = "usuarioId")
    @NotNull(message = "El usuario asignado es obligatorio")
    private Usuario usuario;

    @NotBlank(message = "El nombre de la tarea es obligatorio")
    @Column(nullable = false)
    private String nombre;

    @NotBlank(message = "La descripción de la tarea es obligatoria")
    @Column(nullable = false)
    private String descripcion;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado")
    private EstadoTarea estado;

    @DateTimeFormat
    @Column(nullable = false, name = "fechaLimite")
    @NotNull(message = "La fecha límite es obligatoria")
    private LocalDate fechaLimite;

    @Override
    public String toString() {
        return "Tarea{" +
                "id=" + id +
                ", proyectoId=" + proyecto.getId() + // Changed to get ID
                ", usuarioId=" + usuario.getId() +   // Changed to get ID
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", estado='" + estado + '\'' +
                ", fechaLimite=" + fechaLimite +
                '}';
    }
}