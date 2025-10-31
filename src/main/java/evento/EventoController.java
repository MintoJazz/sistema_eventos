package evento;

import java.io.IOException;

import evento.dto.EventoCriacaoDTO;
import evento.dto.EventoDashboardDTO;
import evento.dto.EventoOpcaoDTO;
import evento.dto.EventoPerfilDTO;
import io.javalin.http.Context;
import io.javalin.http.UploadedFile;
import persistencias.AController;

public class EventoController extends AController<
    EventoDashboardDTO,
    EventoPerfilDTO,
    EventoCriacaoDTO,
    EventoOpcaoDTO,
    Evento,
    EventoDAO,
    EventoFactory,
    EventoService
> {
    public EventoController(EventoService service) {
        super("evento", service);
    }

    @Override protected EventoCriacaoDTO getCriacaoDTO(Context ctx) {
        EventoCriacaoDTO dto = new EventoCriacaoDTO();

        dto.setNome(ctx.formParam("nome"));
        dto.setDataInicio(ctx.formParam("dataInicio"));
        dto.setDataFim(ctx.formParam("dataFim"));
        dto.setLocal(ctx.formParam("local"));
        // dto.setDetalhes(ctx.formParam("detalhes"));
        // dto.setMetadados(ctx.formParam("metadados"));

        UploadedFile uploadedFile = ctx.uploadedFile("material");

        if (uploadedFile != null && uploadedFile.size() > 0) {
            try {
                dto.setMaterial(uploadedFile.content().readAllBytes());
            } catch (IOException e) {
                throw new RuntimeException("Erro ao processar o upload do arquivo", e);
            }
        }

        return dto;
    }
    
}
