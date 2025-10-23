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
import persistencias.DAOInscricao;
import persistencias.DAOPalestra;
import persistencias.DAOPalestrante;
import persistencias.DAOParticipante;
import persistencias.IDAO;
import negocio.Evento;
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
        MapDAO.put("inscricoes", new DAOInscricao());

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

        app.get(
            "/eventos/disponiveis/{cpf}", ctx -> {
                DAOEvento daoEvento = (DAOEvento) MapDAO.get("eventos");
                DAOParticipante daoParticipante = (DAOParticipante) MapDAO.get("participantes");
                Map<String, Object> model = new HashMap<>();
                String cpf = ctx.pathParam("cpf");

                model.put(
                    "eventos", daoEvento.getByQuery(
                        "SELECT * FROM dashboard_eventos WHERE id NOT IN (SELECT evento_id FROM inscricao WHERE participante_id = (SELECT id FROM participante WHERE cpf = ?)) AND NOT status_evento = 'Encerrado';",
                        cpf
                    )
                );

                model.put("participante", daoParticipante.get1ByParam("cpf", cpf));

                ctx.render("templates/nova-inscricao.html",model);
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
                        return;
                    } 

                    int duracao = Integer.parseInt(duracaoStr);
                    if (duracao <= 0) {
                        ctx.status(400).result("Erro de validação: A duração da palestra deve ser um número positivo.");
                        return;
                    }

                    for (String id : palestrantesId) palestrantes.add(daoPalestrante.getById(Integer.parseInt(id)));
                    if (dataHoraString != null && !dataHoraString.isBlank()) dataHora = Timestamp.valueOf(java.time.LocalDateTime.parse(dataHoraString));
                    
                    daoPalestra.setQueryAdd(
                        new Palestra(
                            nome,
                            dataHora, 
                            duracao, 
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

        app.post(
            "/inscricao", ctx -> {
                try {
                    DAOEvento daoEvento = (DAOEvento) MapDAO.get("eventos");
                    DAOParticipante daoParticipante = (DAOParticipante) MapDAO.get("participantes");
                    DAOInscricao daoInscricao = (DAOInscricao) MapDAO.get("inscricoes");

                    String participanteId = ctx.formParam("participante_id");
                    System.out.println(participanteId);
                    Participante participante = daoParticipante.getById(Integer.parseInt(participanteId));

                    if (participante == null) {
                        ctx.status(404).result("Erro: Participante com o ID fornecido não foi encontrado.");
                        return;
                    }

                    System.out.println(participante.getNome());
                    List<String> sucessos = new ArrayList<>();
                    List<String> falhas = new ArrayList<>();
                    List<String> eventoIds = ctx.formParams("eventos_ids");
                    for (String string : eventoIds) System.out.println(string);

                    for (String string : eventoIds) {
                        Evento evento = daoEvento.getById(Integer.parseInt(string));
                        System.out.println(evento.getNome());
                        if (evento == null) {
                            falhas.add("Evento com ID " + string + " não encontrado.");
                            continue;
                        }

                        if ("Encerrado".equals(evento.getStatus())) {
                            falhas.add("Não é possível se inscrever no evento '" + evento.getNome() + "' pois ele já foi encerrado.");
                            continue;
                        }

                        if (daoInscricao.jaInscrito(participante.getId(), evento.getId())) {
                            falhas.add("Você já está inscrito no evento '" + evento.getNome() + "'.");
                            continue;
                        }

                        daoInscricao.setQueryAdd(participante, evento);
                        System.out.println("Inscrição no evento '" + evento.getNome() + "' realizada com sucesso.");
                        sucessos.add("Inscrição no evento '" + evento.getNome() + "' realizada com sucesso.");
                    }

                    if (sucessos.isEmpty() && !falhas.isEmpty()) {
                        ctx.status(400).result("Nenhuma inscrição pôde ser realizada. Motivos: " + String.join("; ", falhas));
                    } else {
                        ctx.redirect("/participante/" + participante.getCpf() + "?inscricao=sucesso");
                    }

                } catch (NumberFormatException e) {
                    ctx.status(400).result("Erro: IDs de participante ou evento são inválidos.");
                }catch (SQLException e) {
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

