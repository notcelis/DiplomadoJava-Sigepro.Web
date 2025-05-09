package dgtic.core.M8P1.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "invitacion")
public class Invitacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    @Column(name = "fecha_invitacion")
    private LocalDateTime fechaInvitacion;

    private boolean aceptada;

    @Column(nullable = false)
    private String token;
}
