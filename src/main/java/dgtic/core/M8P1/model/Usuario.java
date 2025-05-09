package dgtic.core.M8P1.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "usuarios")
@NamedQuery(name = "Usuario.findByRol",
        query = "SELECT u FROM Usuario u WHERE u.rol = :rol")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String contraseña;


    @ManyToOne
    @JoinColumn(name = "rolId")
    private Rol rol;

    @ManyToOne
    @JoinColumn(name = "equipoId")
    private Equipo equipo;

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", email='" + email + '\'' +
                ", contraseña='" + contraseña + '\'' +
                ", rolId=" + rol +
                '}';
    }

}
