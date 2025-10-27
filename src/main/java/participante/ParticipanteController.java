package participante;

import java.util.Map;

import io.javalin.http.Context;
import participante.dto.ParticipanteCriacaoDTO;
import participante.dto.ParticipanteDashboardDTO;
import participante.dto.ParticipanteOpcaoDTO;
import participante.dto.ParticipantePerfilDTO;
import persistencias.AController;

public class ParticipanteController extends AController <
    Participante,
	ParticipanteDashboardDTO,
	ParticipanteOpcaoDTO,
	ParticipantePerfilDTO,
	ParticipanteCriacaoDTO,
	ParticipanteDAO ,
	ParticipanteFactory,
	ParticipanteService
> {

	public ParticipanteController(ParticipanteService service) {
		super("participante", service);
		//TODO Auto-generated constructor stub
	}

	@Override
	protected Map<String, Object> elementosFormulario(Context ctx) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'elementosFormulario'");
	}

	@Override
	protected ParticipanteCriacaoDTO getCriacaoDTO(Context ctx) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'getCriacaoDTO'");
	}
    
}
