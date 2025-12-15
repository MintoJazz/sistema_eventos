package evento.privado;

import evento.dto.EventoDashboard;
import evento.dto.EventoPrivadoForm;
import evento.dto.EventoPrivadoPerfil;
import exceptions.PermissaoNegadaException;
import io.javalin.http.Context;
import persistencias.AuthService;
import persistencias.ControllerCRUD;

public class EventoPrivadoController extends ControllerCRUD<EventoDashboard, EventoPrivadoPerfil, EventoPrivadoForm, EventoPrivadoService> {

    public EventoPrivadoController(EventoPrivadoService service) {
        super(service);
    }

    @Override protected String getRole(Context ctx) {
        String userId = ctx.sessionAttribute("user");
        
        if (AuthService.isAdmin(userId)) return "app_admin";
        if (AuthService.isViewer(userId)) return "app_viewer";

        String eventoId = ctx.pathParamMap().get("id");

        if (eventoId != null && !eventoId.isEmpty() && AuthService.isOrganizer(userId, eventoId)) return "app_organizer";

        return "app_attendee"; 
    }

    @Override public void formularioAtualizar(Context ctx) {
        String user = ctx.sessionAttribute("user");
        if (!AuthService.isOrganizer(user, ctx.pathParam("id")) && !AuthService.isAdmin(user)) throw new PermissaoNegadaException("Acesso restrito: Apenas o organizador do evento pode editar ele.");
        super.formularioAtualizar(ctx);
    }
}