package dgtic.core.M8P1.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class HistorialCambioDTO {
    private Long id;

    @NotBlank(message = "La acción es obligatoria")
    private String accion;

    private LocalDate fecha;

    @NotNull(message = "El ID de la tarea es obligatorio")
    private Long tareaId;

    @NotNull(message = "El ID del usuario es obligatorio")
    private Long usuarioId;
}