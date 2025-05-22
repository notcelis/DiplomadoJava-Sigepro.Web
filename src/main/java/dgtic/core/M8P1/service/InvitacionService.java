package dgtic.core.M8P1.service;

import dgtic.core.M8P1.model.Invitacion;
import dgtic.core.M8P1.repository.InvitacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class InvitacionService {

    @Autowired
    private InvitacionRepository invitacionRepository;

    @Autowired
    private JavaMailSender mailSender;

    public void guardarInvitacion(Invitacion invitacion){
        invitacionRepository.save(invitacion);
    }

    public void enviarInvitacion(String destinatario, String token) {
        String asunto = "Invitación a la plataforma";
        String cuerpo = "Hola, fuiste invitado a unirte al sistema. "
                + "Usa el siguiente enlace para crear tu cuenta:\n\n"
                + "http://localhost:8081/usuario/registro?token=" + token + "\n\n"
                + "Este enlace es válido por 24 horas.";

        SimpleMailMessage mensaje = new SimpleMailMessage();
        mensaje.setFrom("isaac.celis.3c@gmail.com");
        mensaje.setTo(destinatario);
        mensaje.setSubject(asunto);
        mensaje.setText(cuerpo);

        mailSender.send(mensaje);
    }

}
