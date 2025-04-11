package dgtic.core.M8P1.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "historial_cambios")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HistorialCambio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String accion;
    private LocalDate fecha;

    @ManyToOne
    @JoinColumn(name = "tareaId")
    private Tarea tarea;

    @ManyToOne
    @JoinColumn(name = "usuarioId")
    private Usuario usuario;
}
