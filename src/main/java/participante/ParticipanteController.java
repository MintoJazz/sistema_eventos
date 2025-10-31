package participante;

import io.javalin.http.Context;
import participante.dto.ParticipanteCriacaoDTO;
import participante.dto.ParticipanteDashboardDTO;
import participante.dto.ParticipanteOpcaoDTO;
import participante.dto.ParticipantePerfilDTO;
import persistencias.AController;

public class ParticipanteController extends AController <
    ParticipanteDashboardDTO,
    ParticipantePerfilDTO,
    ParticipanteCriacaoDTO,
    ParticipanteOpcaoDTO,
    Participante,
    ParticipanteDAO,
    ParticipanteFactory,
    ParticipanteService
> {

    public ParticipanteController(String nomeTabela, ParticipanteService service) {
        super(nomeTabela, service);
        //TODO Auto-generated constructor stub
    }

    @Override protected ParticipanteCriacaoDTO getCriacaoDTO(Context ctx) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getCriacaoDTO'");
    }
    
}
