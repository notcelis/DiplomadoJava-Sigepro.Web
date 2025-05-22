package dgtic.core.M8P1.validation;

import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Manejador genérico para cualquier excepción
    @ExceptionHandler(Exception.class)
    public String handleException(Exception ex, Model model) {
        System.err.println("Error capturado: " + ex.getMessage());

        model.addAttribute("mensajeError", "Ocurrió un error inesperado. Por favor intenta más tarde.");
        return "error/general";
    }
}

