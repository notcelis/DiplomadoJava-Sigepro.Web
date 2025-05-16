package dgtic.core.M8P1.service;

import dgtic.core.M8P1.model.Usuario;
import dgtic.core.M8P1.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con email: " + email));

        return new org.springframework.security.core.userdetails.User(
                usuario.getEmail(),
                usuario.getContraseña(),
                //List.of(new SimpleGrantedAuthority("ROLE_USER")) // Ajusta según roles
                List.of(new SimpleGrantedAuthority("ROLE_"+usuario.getRol().getNombre().toUpperCase())) // Ajusta según roles
        );
    }
}
