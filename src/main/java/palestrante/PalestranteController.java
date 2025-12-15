package palestrante;

import exceptions.PermissaoNegadaException;
import exceptions.RecursoNaoEncontradoException;
import io.javalin.http.Context;
import palestrante.dto.PalestranteDashboard;
import palestrante.dto.PalestranteForm;
import palestrante.dto.PalestrantePerfil;
import persistencias.AuthService;
import persistencias.ControllerCRUD;

public class PalestranteController extends ControllerCRUD<PalestranteDashboard, PalestrantePerfil, PalestranteForm, PalestranteService>{

    public PalestranteController(PalestranteService service) {
        super(service);
    }

    @Override protected String getRole(Context ctx) {
        String user = ctx.sessionAttribute("user");

        if (AuthService.isAdmin(user)) return "app_admin";
        else if (AuthService.isViewer(user)) return "app_viewer";

        String palestrante = ctx.pathParamMap().get("id");

        if (user.equals(palestrante)) return "app_attendee"; 
        
        throw new PermissaoNegadaException("Acesso negado: Apenas o proprio participante ou algum organizador de um evento que ele faz parte podem consultá-lo.");
    }
    
    @Override public void formularioCriar(Context ctx) { throw new RecursoNaoEncontradoException("Esse recurso não existe para a entidade Palestrante"); }
    @Override public void criar(Context ctx) { throw new RecursoNaoEncontradoException("Esse recurso não existe para a entidade Palestrante"); }
    @Override public void perfil(Context ctx) { throw new RecursoNaoEncontradoException("Esse recurso não existe para a entidade Palestrante"); }
    @Override public void excluir(Context ctx) { throw new RecursoNaoEncontradoException("Esse recurso não existe para a entidade Palestrante"); }
}
