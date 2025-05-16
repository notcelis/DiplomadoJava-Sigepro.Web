package dgtic.core.M8P1.service;

import dgtic.core.M8P1.model.Proyecto;
import dgtic.core.M8P1.model.Usuario;
import dgtic.core.M8P1.repository.ProyectoRepository;
import dgtic.core.M8P1.repository.UsuarioProyectoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProyectoService {

    @Autowired
    private ProyectoRepository proyectoRepository;

    @Autowired
    private UsuarioProyectoRepository usuarioProyectoRepository;

    public List<Proyecto> getAllProyectos() {
        return (List<Proyecto>) proyectoRepository.findAll();
    }

    public Optional<Proyecto> getProyectoById(Long id) {
        return proyectoRepository.findById(id);
    }

    public Proyecto saveProyecto(Proyecto proyecto) {
        return proyectoRepository.save(proyecto);
    }

    public void deleteProyecto(Long id) {
        proyectoRepository.deleteById(id);
    }

    public boolean usuarioTieneAcceso(Usuario usuario, Proyecto proyecto) {
        return usuarioProyectoRepository.existsByUsuarioIdAndProyectoId(usuario.getId(), proyecto.getId());
    }

}

