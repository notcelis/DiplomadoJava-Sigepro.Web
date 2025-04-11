package dgtic.core.M8P1.model.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull; // Add this import
import lombok.Data;

import java.time.LocalDate;

@Data
public class ProyectoDTO {
    private Long id;

    @NotBlank(message = "El nombre del proyecto es obligatorio")
    private String nombre;

    private String descripcion;

    @NotNull(message = "El ID del creador es obligatorio") // Add this
    private Long creadorId; // Add this

    @NotNull(message = "Fecha es obligatoria")
    private LocalDate fechaCreacion;
}