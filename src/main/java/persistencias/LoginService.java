package persistencias;

import java.sql.Connection;
import java.sql.SQLException;

import io.javalin.http.Context;
import usuario.Usuario;
import usuario.UsuarioDAO;

public class LoginService {
    
    private UsuarioDAO usuarioDAO;

    public LoginService() { this.usuarioDAO = new UsuarioDAO("usuario"); }

    public void isLogado(Context ctx) {
        if (ctx.path().equals("/login") || ctx.path().startsWith("/static") || ctx.path().equals("/create/usuario") || ctx.path().startsWith("/js") || ctx.path().startsWith("/css") || ctx.path().startsWith("/img")) return;
        if (ctx.sessionAttribute("user") != null) return;

        ctx.redirect("/login");
        ctx.skipRemainingHandlers();
    }

    public void formularioLogin(Context ctx) {
        if (ctx.sessionAttribute("user") != null) {
            ctx.redirect("/");
            return;
        }
        ctx.render("/templates/login.html");
    }

    public void login(Context ctx) {
        String email = ctx.formParam("email");
        String senha = ctx.formParam("senha");

        if (email == null || senha == null) {
            ctx.redirect("/login?erro=Campos obrigatorios");
            return;
        }

        try (Connection conexao = new Conexao().getConexao("app_admin")) {
            
            Usuario usuario = this.usuarioDAO.autenticar(conexao, email, senha);

            if (usuario != null) {
                ctx.sessionAttribute("user", String.valueOf(usuario.getId()));
                ctx.redirect("/");
            } else ctx.redirect("/login?erro=Credenciais invalidas");

        } catch (SQLException e) {
            e.printStackTrace();
            ctx.status(500).result("Erro no banco de dados ao tentar logar.");
        }
    }

    public void logout(Context ctx) {
        ctx.req().getSession().invalidate();
        ctx.redirect("/login");
    }
}