package dgtic.core.M8P1.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ComentarioArchivoDTO {

    @NotBlank(message = "El comentario es obligatorio")
    private String comentario;

    @NotNull(message = "El ID del usuario es obligatorio")
    private Long usuarioId;

    private MultipartFile archivo;
}