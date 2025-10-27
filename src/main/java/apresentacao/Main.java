package apresentacao;

import java.util.HashMap;
import java.util.Map;

import exceptions.RecursoNaoEncontradoException;
import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location;
import io.javalin.rendering.template.JavalinMustache;
import persistencias.AController;

public class Main {
    public static Map<String, AController> controle = new HashMap<>();

    public static AController verificar(String tabela) {
        if (!controle.keySet().contains(tabela)) throw new RecursoNaoEncontradoException("Recurso não encontrado para a tabela: " + tabela);
        return controle.get(tabela);
    }

    public static void main(String[] args) {

        var app = Javalin.create(
            config -> {
                config.fileRenderer(new JavalinMustache());
                config.staticFiles.add("/static", Location.CLASSPATH);
            }
        ).start(7070);

        app.get("/", ctx -> ctx.result("Trabalho IOBD - Sistema de Eventos by Jaaziel Machado"));

        app.get("/admin/{tabela}", ctx -> verificar(ctx.pathParam("tabela")).dashboard(ctx));
        
        app.get("/{tabela}/{coluna}/{valor}", ctx -> verificar(ctx.pathParam("tabela")).perfil(ctx, ctx.pathParam("coluna")));
        
        app.get("/{tabela}/nova", ctx -> verificar(ctx.pathParam("tabela")).formulario(ctx));

        app.get("eventos/disponiveis/{cpf}", ctx -> ctx.redirect("/inscricao/nova"));
        
        app.post("/{tabela}", ctx -> verificar(ctx.pathParam("tabela")).criacao(ctx));
        
        app.exception(RecursoNaoEncontradoException.class, (e, ctx) -> ctx.status(404).result(e.getMessage()));
    }
}