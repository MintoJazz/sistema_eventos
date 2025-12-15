package inscricao;

import java.util.HashMap;
import java.util.Map;

import exceptions.PermissaoNegadaException;
import exceptions.RecursoNaoEncontradoException;
import inscricao.dto.InscricaoForm;
import io.javalin.http.Context;
import persistencias.AuthService;
import persistencias.ControllerCRUD;

public class InscricaoController extends ControllerCRUD<Inscricao, Inscricao, InscricaoForm, InscricaoService> {

    public InscricaoController(InscricaoService service) {
        super(service);
    }

    @Override protected String getRole(Context ctx) {
        String user = ctx.sessionAttribute("user");
        if (user == null) throw new PermissaoNegadaException("Faça login para se inscrever.");
        if (AuthService.isAdmin(user)) return "app_admin";
        if (AuthService.isViewer(user)) return "app_viewer";
        
        return "app_attendee";
    }

    @Override public void dashboard(Context ctx) {
        String user = this.getRole(ctx);
        if (!user.equals("app_admin") || !user.equals("app_viewer")) throw new PermissaoNegadaException("Usuário não Autorizado (Apenas Admins e Viewers podem ver os usuários)");

        super.dashboard(ctx);
    }

    @Override public void criar(Context ctx) {
        String role = getRole(ctx);
        InscricaoForm form = ctx.bodyAsClass(InscricaoForm.class);
        int idUsuarioLogado = Integer.parseInt(ctx.sessionAttribute("user"));

        form.setUsuarioId(idUsuarioLogado);

        Map<String, String> erros = this.service.validador(form);

        if (!erros.isEmpty()) {
            ctx.status(400).json(erros);
            return;
        } try {
            int novoId = this.service.create(role, form);
            Map<String, Object> resposta = new HashMap<>();
            
            resposta.put("mensagem", "Inscrição realizada com sucesso!");
            resposta.put("id", novoId);
            resposta.put("redirectUrl", "/read/evento/id/" + form.getEventoId()); 
            
            ctx.status(201).json(resposta);
            
        } catch (Exception e) {
            if (e.getMessage() != null && (e.getMessage().contains("unique") || e.getMessage().contains("duplicate"))) ctx.status(409).json(Map.of("erro", "Você já está inscrito neste evento!"));
            else throw e;
        }
    }

    @Override public void perfil(Context ctx) { throw new RecursoNaoEncontradoException("Esse recurso não existe."); }
    @Override public void atualizar(Context ctx) { throw new RecursoNaoEncontradoException("Esse recurso não existe."); }
    @Override public void formularioAtualizar(Context ctx) { throw new RecursoNaoEncontradoException("Esse recurso não existe."); }
    @Override public void formularioCriar(Context ctx) { throw new RecursoNaoEncontradoException("Esse recurso não existe."); }
}