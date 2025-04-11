package dgtic.core.M8P1.model.dto;

import dgtic.core.M8P1.model.EstadoTarea;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class TareaDTO {
    private Long id;

    @NotNull(message = "El ID del proyecto es obligatorio")
    private Long proyectoId;

    @NotNull(message = "El ID del usuario asignado es obligatorio")
    private Long usuarioId;

    @NotBlank(message = "El nombre de la tarea es obligatorio")
    private String nombre;

    @NotBlank(message = "La descripción de la tarea es obligatoria")
    private String descripcion;

    private EstadoTarea estado;

    @NotNull(message = "La fecha límite es obligatoria")
    @DateTimeFormat
    private LocalDate fechaLimite;
}