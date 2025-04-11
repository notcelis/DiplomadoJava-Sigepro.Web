package dgtic.core.M8P1.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = NoEspacioVacioValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD,ElementType.METHOD})
public @interface NoEspacioNoVacio {
    String message() default "No debe ser vacio";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
