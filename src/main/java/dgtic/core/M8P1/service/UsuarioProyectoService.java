package dgtic.core.M8P1.service;

import dgtic.core.M8P1.model.UsuarioProyecto;
import dgtic.core.M8P1.repository.UsuarioProyectoRepository;
import dgtic.core.M8P1.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioProyectoService {

    @Autowired
    private UsuarioProyectoRepository usuarioProyectoRepository;

    public UsuarioProyecto saveUsuarioProyecto(UsuarioProyecto usuarioProyecto){
        return usuarioProyectoRepository.save(usuarioProyecto);
    }
}
