package sistema_eventos;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location;
import io.javalin.rendering.template.JavalinMustache;
import negocio.Participante;
// import persistencias.AbstracaoDAO;
import persistencias.EventoDAO;
import persistencias.InterfaceDAO;
import persistencias.ParticipanteDAO;

public class Main {
    public static void main(String[] args) {
        Map<String,InterfaceDAO<?>> DAOS = new HashMap<>();
        DAOS.put("participante", new ParticipanteDAO());
        DAOS.put("evento", new EventoDAO());

        var app = Javalin.create(
            config -> {
                config.fileRenderer(new JavalinMustache());
                config.staticFiles.add("/static", Location.CLASSPATH);
            }
        ).start(7070);

        app.get("/", ctx -> ctx.render("/templates/index.html"));

        app.get("/admin/eventos", ctx -> ctx.render("/templates/dashboard-eventos.html"));
        
        app.get("/palestra/nova", ctx -> ctx.render("/templates/nova-palestra.html"));

        app.get( 
            "/participante/{cpf}", ctx -> {
                Map<String, Object> model = new HashMap<>();
                Participante participante = ((ParticipanteDAO) DAOS.get("participante")).procurarCpf(ctx.pathParam("cpf"));
                model.put("participante", participante);
                model.put("eventos", participante.getEventosInscritos());
                ctx.render("/templates/perfil-participante.html", model);
            }
        );
        
        app.post( 
            "/queries", ctx -> {
                String tabela = ctx.body();
                List<?> lista = DAOS.get(tabela).listar();
                if (lista != null) ctx.json(lista); 
                else ctx.status(400).result("Tabela " + tabela + " não encontrada.");
            }
        );
    }
}