package dgtic.core.M8P1.validation;

import dgtic.core.M8P1.model.Usuario;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class NombreValidation implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return Usuario.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Usuario usuario=(Usuario)target;
        if(usuario.getNombre().equals("DGTIC")){
            errors.rejectValue("nombre","Novalido.usuario.nombre");
        }
    }
}
