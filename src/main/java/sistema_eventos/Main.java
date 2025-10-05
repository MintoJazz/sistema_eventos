package sistema_eventos;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location;
import io.javalin.rendering.template.JavalinMustache;
import persistencias.ADAO;
import persistencias.DAOEvento;
import persistencias.DAOPalestrante;
import persistencias.DAOParticipante;
import negocio.Participante;

public class Main {
    public static void main(String[] args) {
        Map <String,ADAO<?>> MapDAO = new HashMap<>();
        MapDAO.put("participantes", new DAOParticipante());
        MapDAO.put("eventos", new DAOEvento());
        MapDAO.put("palestrantes", new DAOPalestrante());

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

        app.get(
            "/participante/{cpf}", ctx -> {
                DAOParticipante daoParticipante = (DAOParticipante) MapDAO.get("participantes");
                Participante participante = daoParticipante.get1ByParam("cpf", ctx.pathParam("cpf"));
                if (participante == null) {
                    ctx.result("Participante não encontrado.");
                    return;
                }
                
                participante.setEventos(daoParticipante.getEventos(participante));
                Map<String,Object> model = new HashMap<>();
                model.put("participante", participante);
                ctx.render("/templates/perfil-participante.html",model);
            }
        );

        app.get(
            "/palestra/nova", ctx -> {
                Map<String, Object> model = new HashMap<>();
                for (String string : Arrays.asList("eventos","palestrantes")) model.put(string, MapDAO.get(string).getAll());
                ctx.render("/templates/nova-palestra.html",model);
            }
        );
    }
}
