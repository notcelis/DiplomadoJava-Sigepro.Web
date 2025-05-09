package dgtic.core.M8P1.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class InicioController {

    @Value("${message.application}")
    private String message;

    @GetMapping("home")
    public String home(Model model){
        model.addAttribute("bienvenida","¿En qué trabajaremos hoy?");
        return "home/home";
    }
    @GetMapping("/login")
    public String login(){
        return "inicio";
    }

}
