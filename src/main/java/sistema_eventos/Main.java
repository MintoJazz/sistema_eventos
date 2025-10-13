package sistema_eventos;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location;
import io.javalin.rendering.template.JavalinMustache;
import persistencias.DAOEvento;
import persistencias.DAOPalestra;
import persistencias.DAOPalestrante;
import persistencias.DAOParticipante;
import persistencias.IDAO;
import negocio.Palestra;
import negocio.Palestrante;
import negocio.Participante;

public class Main {
    public static void main(String[] args) {
        
        Map <String,IDAO<?>> MapDAO = new HashMap<>();
        MapDAO.put("participantes", new DAOParticipante());
        MapDAO.put("eventos", new DAOEvento());
        MapDAO.put("palestrantes", new DAOPalestrante());
        MapDAO.put("palestras", new DAOPalestra());

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

        app.post(
            "/palestra", ctx -> {
                try {
                    DAOPalestra daoPalestra = (DAOPalestra) MapDAO.get("palestras");
                    DAOPalestrante daoPalestrante = (DAOPalestrante) MapDAO.get("palestrantes");
                    DAOEvento daoEvento = ((DAOEvento) MapDAO.get("eventos"));

                    String nome = ctx.formParam("palestra-nome");
                    String duracaoStr = ctx.formParam("duracao");
                    String eventoIdStr = ctx.formParam("evento-palestra");
                    List<Palestrante> palestrantes = new ArrayList<>();
                    List<String> palestrantesId = ctx.formParams("palestrantes");
                    Timestamp dataHora = null;
                    String dataHoraString = ctx.formParam("datetime");

                    if (nome == null || nome.isBlank() || duracaoStr == null || eventoIdStr == null || palestrantesId.isEmpty()) {
                        ctx.status(400).result("Erro de validação: Campos obrigatórios (nome, duração, evento, palestrantes) não podem ser vazios.");
                        return; // Para a execução
                    }

                    for (String id : palestrantesId) palestrantes.add(daoPalestrante.getById(Integer.parseInt(id)));
                    if (dataHoraString != null && !dataHoraString.isBlank()) dataHora = Timestamp.valueOf(java.time.LocalDateTime.parse(dataHoraString));
                    
                    daoPalestra.setQueryAdd(
                        new Palestra(
                            nome,
                            dataHora, 
                            Integer.parseInt(duracaoStr), 
                            daoEvento.getById(Integer.parseInt(eventoIdStr)),
                            palestrantes
                        )
                    );

                    ctx.result("Palestra criada com sucesso!");

                } catch (NumberFormatException e) {
                    ctx.status(400).result("Erro de formato: Duração e IDs devem ser números válidos.");
                } catch (SQLException e) {
                    ctx.status(500).result("Erro interno no servidor ao tentar salvar a palestra.");
                    e.printStackTrace();
                } catch (Exception e) {
                    ctx.status(400).result("Erro nos dados enviados: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        );
    }
}
