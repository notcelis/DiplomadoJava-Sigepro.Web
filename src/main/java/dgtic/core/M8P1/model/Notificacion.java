package dgtic.core.M8P1.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "notificaciones")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Notificacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String contenido;

    private LocalDateTime fecha;
    private boolean leido;

    @ManyToOne
    @JoinColumn(name = "usuarioId")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "idTarea")
    private Tarea tarea;
}

