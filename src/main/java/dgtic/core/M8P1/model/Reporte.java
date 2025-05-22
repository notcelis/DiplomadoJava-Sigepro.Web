package dgtic.core.M8P1.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "reportes")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Reporte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "proyectoId")
    @NotNull
    private Proyecto proyecto;

    @Column(nullable = false)
    @NotBlank
    private String contenido;

    @Column(nullable = false)
    @NotNull
    private LocalDate fecha;

    @Override
    public String toString() {
        return "Reporte{" +
                "id=" + id +
                ", proyectoId=" + proyecto +
                ", contenido='" + contenido + '\'' +
                ", fecha=" + fecha +
                '}';
    }
}
