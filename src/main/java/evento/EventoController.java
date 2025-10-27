package evento;

import evento.dto.EventoCriacaoDTO;
import evento.dto.EventoDashboardDTO;
import evento.dto.EventoOpcaoDTO;
import evento.dto.EventoPerfilDTO;
import io.javalin.http.Context;
import persistencias.AController;

public class EventoController extends AController<
    Evento,
    EventoDashboardDTO,
    EventoOpcaoDTO,
    EventoPerfilDTO,
    EventoCriacaoDTO,
    EventoDAO,
    EventoFactory,
    EventoService
> {
    public EventoController(EventoService service) {
        super("evento", service);
    }

    @Override protected EventoCriacaoDTO getCriacaoDTO(Context ctx) {
        return ctx.formParamAsClass("novo-evento", EventoCriacaoDTO.class).get();
    }
    
}
