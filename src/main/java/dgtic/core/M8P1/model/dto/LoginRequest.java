package dgtic.core.M8P1.model.dto;

import lombok.Data;

@Data
public class LoginRequest {
    private String email;
    private String contraseña;
}