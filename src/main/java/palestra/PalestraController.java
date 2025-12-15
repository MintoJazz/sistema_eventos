package palestra;

import exceptions.PermissaoNegadaException;
import io.javalin.http.Context;
import palestra.dto.PalestraDashboard;
import palestra.dto.PalestraForm;
import palestra.dto.PalestraPerfil;
import persistencias.AuthService;
import persistencias.ControllerCRUD;

public class PalestraController extends ControllerCRUD<PalestraDashboard, PalestraPerfil, PalestraForm, PalestraService>{

    public PalestraController(PalestraService service) {
        super(service);
    }

    @Override protected String getRole(Context ctx) {
        String user = ctx.sessionAttribute("user");
        if (AuthService.isAdmin(user)) return "app_admin";
        if (AuthService.isViewer(user)) return "app_viewer";

        String palestra = ctx.pathParamMap().get("id");
        if (palestra == null || palestra.isEmpty()) return "app_organizer";
        if (AuthService.isOrganizerPalestra(palestra, user)) return "app_organizer";
        if (AuthService.isPalestrante(user, palestra) || AuthService.isParticipantePalestra(user, palestra)) return "app_attendee";


        throw new PermissaoNegadaException("Acesso negado: Você não tem permissão para acessar esta palestra.");
    }

    @Override public void dashboard(Context ctx) {
        String role = getRole(ctx);
        if ("app_attendee".equals(role)) throw new PermissaoNegadaException("Acesso restrito: Participantes devem acessar palestras através do perfil do Evento ou do seu Perfil de Usuário.");

        super.dashboard(ctx);
    }

    @Override public void criar(Context ctx) {
        String user = ctx.sessionAttribute("user");
        PalestraForm form = ctx.bodyAsClass(PalestraForm.class);
        
        if (!AuthService.isOrganizer(user, String.valueOf(form.getEventoId()))) throw new PermissaoNegadaException("Você não pode criar palestras em um evento que não organiza.");

        super.criar(ctx);
    }
}