package dgtic.core.M8P1.model.dto;

import lombok.Data;

@Data
public class LoginResponse {
    private String token;
    public LoginResponse(String token) { this.token = token; }
    // getter
}