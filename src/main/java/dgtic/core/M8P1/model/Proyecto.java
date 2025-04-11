package dgtic.core.M8P1.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "proyectos")
@NamedQuery(name = "Proyecto.findByCreador",
        query = "SELECT p FROM Proyecto p WHERE p.creador = :creador")
public class Proyecto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotBlank
    private String nombre;

    @NotBlank
    @Column(nullable = false)
    private String descripcion;

    @NotNull
    @Column(nullable = false,name = "fechaCreacion")
    private LocalDate fechaCreacion;

    @ManyToOne
    @JoinColumn(name = "creadorId")
    @NotNull
    private Usuario creador;

}
