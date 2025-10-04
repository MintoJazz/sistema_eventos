package sistema_eventos;

import java.util.HashMap;
import java.util.Map;

import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location;
import io.javalin.rendering.template.JavalinMustache;
import persistencias.ADAO;
import persistencias.DAOParticipante;

public class Main {
    public static void main(String[] args) {
        Map <String,ADAO<?>> MapDAO = new HashMap<>();
        MapDAO.put("participantes", new DAOParticipante());

        var app = Javalin.create(
            config -> {
                config.fileRenderer(new JavalinMustache());
                config.staticFiles.add("/static", Location.CLASSPATH);
            }
        ).start(7070);

        app.get("/", ctx -> ctx.result("Trabalho IOBD - Sistema de Eventos by Jaaziel Machado"));

        app.get(
            "/admin/{tabela}", ctx -> {
                String tabela = ctx.pathParam("tabela");
                if (!MapDAO.keySet().contains(tabela)) {
                    ctx.result("Tabela não encontrada ou sem permissao de acesso.");
                    return;
                }

                Map<String,Object> model = new HashMap<>();
                model.put(tabela, MapDAO.get(tabela).getAll());
                ctx.render("/templates/" + tabela + ".html",model);
            }
        );
    }
}