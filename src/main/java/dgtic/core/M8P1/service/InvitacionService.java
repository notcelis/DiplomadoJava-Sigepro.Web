package dgtic.core.M8P1.service;

import dgtic.core.M8P1.model.Invitacion;
import dgtic.core.M8P1.repository.InvitacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InvitacionService {

    @Autowired
    private InvitacionRepository invitacionRepository;

    public void guardarInvitacion(Invitacion invitacion){
        invitacionRepository.save(invitacion);
    }
}
