package palestra;

import io.javalin.http.Context;
import palestra.dto.PalestraCriacaoDTO;
import palestra.dto.PalestraDashboardDTO;
import palestra.dto.PalestraOpcaoDTO;
import palestra.dto.PalestraPerfilDTO;
import persistencias.AController;

public class PalestraController extends AController<
    PalestraDashboardDTO,
	PalestraPerfilDTO,
	PalestraCriacaoDTO,
	PalestraOpcaoDTO,
	Palestra,
	PalestraDAO,
	PalestraFactory,
	PalestraService
> {

    public PalestraController(PalestraService service) {
        super("palestra", service);
    }

    @Override protected PalestraCriacaoDTO getCriacaoDTO(Context ctx) {
        PalestraCriacaoDTO criacaoDTO = new PalestraCriacaoDTO();

        criacaoDTO.setNome(ctx.formParam("nome"));
        criacaoDTO.setDuracao(ctx.formParam("duracao"));
        criacaoDTO.setDataHoraInicio(ctx.formParam("data-inicio"));
        criacaoDTO.setEventoId(ctx.formParam("evento"));
        criacaoDTO.setPalestranteId(ctx.formParams("palestrante"));

        return criacaoDTO;
    }
    
}
