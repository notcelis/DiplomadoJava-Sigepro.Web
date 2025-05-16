package dgtic.core.M8P1.controller;

import dgtic.core.M8P1.model.*;
import dgtic.core.M8P1.repository.ProyectoRepository;
import dgtic.core.M8P1.repository.UsuarioProyectoRepository;
import dgtic.core.M8P1.repository.UsuarioRepository;
import dgtic.core.M8P1.service.ProyectoService;
import dgtic.core.M8P1.service.UsuarioProyectoService;
import dgtic.core.M8P1.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/proyecto")
public class ProyectoController {

    @Autowired
    private ProyectoService proyectoService;

    @Autowired
    private ProyectoRepository proyectoRepository;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioProyectoRepository usuarioProyectoRepository;

    @Autowired
    private UsuarioProyectoService usuarioProyectoService;

    @GetMapping("ver")
    public String ver(Model model,
     @AuthenticationPrincipal UserDetails userDetails){
        String cadena = "Proyectos";
        model.addAttribute("proyecto",cadena);

        // Obtener el usuario autenticado (puedes adaptar esto según tu autenticación real)
        String email = userDetails.getUsername();
        // TO DO
        Usuario usuarioActual = usuarioRepository.findByEmail(email).orElseThrow(); // <-- cambia si usas otro sistema

        // Obtener proyectos a los que tiene acceso
        List<Proyecto> proyectosPermitidos = usuarioProyectoRepository.findByUsuarioId(usuarioActual.getId())
                .stream()
                .map(UsuarioProyecto::getProyecto)
                .toList();


        model.addAttribute("proyectos",proyectosPermitidos);
        return "proyectos/proyecto";
    }

    @GetMapping("crear")
    public String crear(Model model){
        String cadena = "Crear proyecto";
        List<Usuario> usuarios = usuarioService.getAllUsuarios();
        model.addAttribute("proyectos",cadena);
        model.addAttribute("usuarios",usuarios);
        model.addAttribute("proyecto", new Proyecto());

        return "proyectos/crear";
    }

    @PostMapping("crear")
    public String proyectocreado(
            @RequestParam("nombre") String nombre,
            @RequestParam("descripcion") String descripcion,
            @AuthenticationPrincipal UserDetails userDetails,
//            @RequestParam("creador") String creador,
            //@RequestParam("fechaCreacion") LocalDate fechaCreacion,
            //@RequestParam("activo") Integer activo, // Asegúrate de manejar la conversión si viene como String
            //BindingResult bindingResult,
            Model model) {

        // Obtener el usuario autenticado (puedes adaptar esto según tu autenticación real)
        String email = userDetails.getUsername();
        // TO DO
        Usuario usuarioActual = usuarioRepository.findByEmail(email).orElseThrow(); // <-- cambia si usas otro sistema

        // Obtener proyectos a los que tiene acceso
        List<Proyecto> proyectosPermitidos = usuarioProyectoRepository.findByUsuarioId(usuarioActual.getId())
                .stream()
                .map(UsuarioProyecto::getProyecto)
                .toList();

//        Usuario usuario = usuarioRepository.findByNombreContaining(creador).stream().toList().get(0);
        Proyecto proyecto = new Proyecto();
        proyecto.setNombre(nombre);
        proyecto.setDescripcion(descripcion);
        proyecto.setFechaCreacion(LocalDate.now());
        proyecto.setActivo(1);
        proyecto.setCreador(usuarioActual);

        // Puedes realizar validaciones manuales aquí si no usas @Valid a nivel de clase
        // o puedes crear un objeto DTO y usar @Valid en él.

        proyectoService.saveProyecto(proyecto);

        UsuarioProyecto usuarioProyecto = new UsuarioProyecto();
        usuarioProyecto.setProyecto(proyecto);
        usuarioProyecto.setUsuario(usuarioActual);

        usuarioProyectoService.saveUsuarioProyecto(usuarioProyecto);

        model.addAttribute("proyectos",proyectosPermitidos);
        model.addAttribute("proyecto", "Proyecto creado con éxito");
        return "redirect:/proyecto/ver";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEdicion(@PathVariable Long id, Model model, @AuthenticationPrincipal UserDetails userDetails) {
        Proyecto proyecto = proyectoService.getProyectoById(id).orElseThrow();
        List<Usuario> todosUsuarios = usuarioRepository.findAll();

        // Obtener el usuario autenticado (puedes adaptar esto según tu autenticación real)
        String email = userDetails.getUsername();
        // TO DO
        Usuario usuarioActual = usuarioRepository.findByEmail(email).orElseThrow(); // <-- cambia si usas otro sistema

        // Obtener proyectos a los que tiene acceso
        List<Proyecto> proyectosPermitidos = usuarioProyectoRepository.findByUsuarioId(usuarioActual.getId())
                .stream()
                .map(UsuarioProyecto::getProyecto)
                .toList();

        if (!proyectoService.usuarioTieneAcceso(usuarioActual, proyecto)) {
            return "error/403"; // O redirigir a una vista adecuada
        }

        List<UsuarioProyecto> relaciones = usuarioProyectoRepository.findByProyectoId(id);
        List<Usuario> usuariosProyecto = relaciones.stream()
                .map(UsuarioProyecto::getUsuario)
                .collect(Collectors.toList());

        List<Usuario> usuariosDisponibles = todosUsuarios.stream()
                .filter(u -> !usuariosProyecto.contains(u))
                .collect(Collectors.toList());

        model.addAttribute("proyecto", proyecto);
        model.addAttribute("usuarios", todosUsuarios);
        model.addAttribute("usuarioSeleccionado", proyecto.getCreador().getId());
        model.addAttribute("usuariosProyecto", usuariosProyecto);
        model.addAttribute("usuariosDisponibles", usuariosDisponibles);

        return "proyectos/editar";
    }

    @PostMapping("/editar/{id}")
    public String actualizarProyecto(
            @PathVariable Long id,
            @ModelAttribute Proyecto proyectoActualizado,
            @RequestParam(required=false) Usuario usuario,
            @RequestParam Integer activo,
            RedirectAttributes redirectAttributes,
            @AuthenticationPrincipal UserDetails userDetails) {

        Proyecto proyectoExistente = proyectoService.getProyectoById(id).get();

        // Obtener el usuario autenticado (puedes adaptar esto según tu autenticación real)
        String email = userDetails.getUsername();
        // TO DO
        Usuario usuarioActual = usuarioRepository.findByEmail(email).orElseThrow(); // <-- cambia si usas otro sistema

        if (!proyectoService.usuarioTieneAcceso(usuarioActual, proyectoExistente)) {
            return "error/403";
        }

        // Solo actualiza nombre y descripción
        proyectoExistente.setNombre(proyectoActualizado.getNombre());
        proyectoExistente.setDescripcion(proyectoActualizado.getDescripcion());
        proyectoExistente.setCreador(usuario);
        proyectoExistente.setActivo(activo);

        proyectoService.saveProyecto(proyectoExistente);

        redirectAttributes.addFlashAttribute("mensaje", "Proyecto actualizado correctamente.");
        return "redirect:/proyecto/ver";
    }

    // Agregar usuario al proyecto
    @PostMapping("/{id}/agregar-usuario")
    public String agregarUsuarioProyecto(@PathVariable Long id,
                                         @RequestParam Long usuarioId) {
        UsuarioProyectoId relacionId = new UsuarioProyectoId();
        relacionId.setUsuarioId(usuarioId);
        relacionId.setProyectoId(id);

        UsuarioProyecto relacion = new UsuarioProyecto();
        relacion.setUsuario(usuarioRepository.findById(usuarioId).orElseThrow());
        relacion.setProyecto(proyectoRepository.findById(id).orElseThrow());

        usuarioProyectoRepository.save(relacion);

        return "redirect:/proyecto/editar/" + id;
    }

    // Remover usuario del proyecto
    @PostMapping("/{id}/remover-usuario/{usuarioId}")
    public String removerUsuarioProyecto(@PathVariable Long id,
                                         @PathVariable Long usuarioId) {
        UsuarioProyecto usuarioProyecto = usuarioProyectoRepository.findByProyectoIdAndUsuarioId(id,usuarioId);
        usuarioProyectoRepository.deleteById(usuarioProyecto.getId());

        return "redirect:/proyecto/editar/" + id;
    }

}
