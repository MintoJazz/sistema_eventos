package example;

import evento.EventoController;
import exceptions.PermissaoNegadaException;
import exceptions.RecursoNaoEncontradoException;
import exceptions.ValidacaoException;
import persistencias.LoginService;
import usuario.UsuarioController;

import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location;
import io.javalin.rendering.template.JavalinMustache;

public class Main {
    public static LoginService login = new LoginService();

    public static void main(String[] args) {
        var app = Javalin.create(config -> {
            config.fileRenderer(new JavalinMustache());
            config.staticFiles.add("/static", Location.CLASSPATH);
        }).start(7070);

        app.before(ctx -> {
            System.out.println(ctx.path());
            login.isLogado(ctx);
        });

        app.get("/", ctx -> ctx.redirect("/read/evento"));

        app.get("/login", ctx -> login.formularioLogin(ctx));
        app.post("/login", ctx -> login.login(ctx));
        app.post("/logout", ctx -> login.logout(ctx));

        app.get("/perfil", ctx -> ((UsuarioController) Injector.verificarController("usuario")).meuPerfil(ctx));
        
        app.get("/read/{tabela}", ctx -> Injector.verificarController(ctx.pathParam("tabela")).dashboard(ctx));
        
        app.get("/read/{tabela}/id/{id}", ctx -> Injector.verificarController(ctx.pathParam("tabela")).perfil(ctx));

        app.get("/create/{tabela}", ctx -> Injector.verificarController(ctx.pathParam("tabela")).formularioCriar(ctx));
        app.post("/create/{tabela}", ctx -> Injector.verificarController(ctx.pathParam("tabela")).criar(ctx));

        app.get("/update/{tabela}/{id}", ctx -> Injector.verificarController(ctx.pathParam("tabela")).formularioAtualizar(ctx));
        app.put("/update/{tabela}/{id}", ctx -> Injector.verificarController(ctx.pathParam("tabela")).atualizar(ctx));
        
        app.delete("/delete/{tabela}/{id}", ctx -> Injector.verificarController(ctx.pathParam("tabela")).excluir(ctx));
        
        app.get("/download/evento/{id}/alvara", ctx -> ((EventoController) Injector.verificarController("evento")).downloadAlvara(ctx));
        app.post("/upload/evento/{id}/alvara", ctx -> ((EventoController) Injector.verificarController("evento")).uploadAlvara(ctx));
        app.delete("/delete/evento/{id}/alvara", ctx -> ((EventoController) Injector.verificarController("evento")).deletarAlvara(ctx));

        app.exception(ValidacaoException.class, (e, ctx) -> ctx.status(400).json(e.getErrors()));
        app.exception(PermissaoNegadaException.class, (e, ctx) -> ctx.status(403).result(e.getMessage()));
        app.exception(RecursoNaoEncontradoException.class, (e, ctx) -> ctx.status(404).result(e.getMessage()));
        app.exception(NumberFormatException.class, (e, ctx) -> ctx.status(400).result("ID inválido fornecido."));
        app.exception(Exception.class, (e, ctx) -> {
            e.printStackTrace();
            ctx.status(500).result("Erro interno do servidor: " + e.getMessage());
        });
    }
}