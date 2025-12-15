package evento;

import java.util.Map;

import evento.dto.EventoDashboard;
import evento.dto.EventoForm;
import evento.dto.EventoPerfil;
import exceptions.PermissaoNegadaException;
import io.javalin.http.Context;
import io.javalin.http.UploadedFile;
import persistencias.AuthService;
import persistencias.ControllerCRUD;
import util.Formatadores;

public class EventoController extends ControllerCRUD<EventoDashboard,EventoPerfil,EventoForm,EventoService> {

    public EventoController(EventoService service) { super(service); }

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

    public void uploadAlvara(Context ctx) {
        String role = getRole(ctx);
        int id = Integer.parseInt(ctx.pathParam("id"));
        
        UploadedFile arquivo = ctx.uploadedFile("arquivo");
        
        if (arquivo != null) {
            String jsonDetalhes = Formatadores.arquivoParaJsonString(arquivo);
            service.setDetalhes(role, id, jsonDetalhes);
        }
        
        ctx.status(200).json(Map.of("mensagem", "Arquivo vinculado com sucesso!", "redirectUrl", "/read/evento"));
    }

    public void downloadAlvara(Context ctx) {
        String role = getRole(ctx);
        int id = Integer.parseInt(ctx.pathParam("id"));
        Evento evento = service.getEntidade(role, id);
        Map<String, Object> arquivo = Formatadores.jsonParaArquivo(evento.getDetalhes());

        if (arquivo == null) throw new exceptions.RecursoNaoEncontradoException("Nenhum arquivo anexado a este evento.");

        ctx.contentType((String) arquivo.get("tipo"));
        ctx.header("Content-Disposition", "attachment; filename=\"" + (String) arquivo.get("nome") + "\"");
        ctx.result((byte[]) arquivo.get("bytes"));
    }

    public void deletarAlvara(Context ctx) {
        String role = getRole(ctx);
        int id = Integer.parseInt(ctx.pathParam("id"));
        
        service.setDetalhes(role, id, "{}");
        ctx.status(200).json(Map.of("mensagem", "Arquivo removido com sucesso!"));
    }
}