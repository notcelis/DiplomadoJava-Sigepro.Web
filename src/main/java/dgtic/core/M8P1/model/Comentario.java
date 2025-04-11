package dgtic.core.M8P1.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "comentarios")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comentario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String comentario;

    private LocalDate fecha;

    @ManyToOne
    @JoinColumn(name = "tareaId")
    private Tarea tarea;

    @ManyToOne
    @JoinColumn(name = "usuarioId")
    private Usuario usuario;

    private String archivoUrl;
}
