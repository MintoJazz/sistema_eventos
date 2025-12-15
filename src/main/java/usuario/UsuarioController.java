package usuario;

import exceptions.PermissaoNegadaException;
import io.javalin.http.Context;
import persistencias.AuthService;
import persistencias.ControllerCRUD;
import persistencias.OpcaoDTO;
import usuario.dto.UsuarioForm;
import usuario.dto.UsuarioPerfil;

public class UsuarioController extends ControllerCRUD<OpcaoDTO, UsuarioPerfil, UsuarioForm, UsuarioService> {

    public UsuarioController(UsuarioService service) {
        super(service);
    }

    @Override protected String getRole(Context ctx) {
        String user = ctx.sessionAttribute("user");
        if (user != null && !user.equals("null")) {
            if (AuthService.isAdmin(user)) return "app_admin";
            if (AuthService.isViewer(user)) return "app_viewer";
        }

        String perfil = ctx.pathParamMap().get("id");

        if (perfil == null || perfil.isEmpty()) return "app_admin";
        else if (user.equals(perfil)) return "app_attendee";
        
        throw new PermissaoNegadaException("Usuário não Autorizado (Apenas Admins e Viewers podem ver os usuários)");
    }

    @Override public void dashboard(Context ctx) {
        String user = this.getRole(ctx);
        if (!user.equals("app_admin") && !user.equals("app_viewer")) throw new PermissaoNegadaException("Usuário não Autorizado (Apenas Admins e Viewers podem ver os usuários)");

        super.dashboard(ctx);
    }

    public void meuPerfil(Context ctx) {
        String idSessao = ctx.sessionAttribute("user");

        if (idSessao == null) {
            ctx.redirect("/login");
            return;
        }

        int id = Integer.parseInt(idSessao);
        String role = "app_attendee"; 
        if (AuthService.isAdmin(idSessao)) role = "app_admin";
        UsuarioPerfil perfil = this.service.getPerfil(role, id);

        ctx.render("/templates/perfil-usuario.html", java.util.Map.of("evento", perfil));
    }
}
