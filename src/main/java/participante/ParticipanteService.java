package participante;

import java.util.Map;

import participante.dto.ParticipanteCriacaoDTO;
import participante.dto.ParticipanteDashboardDTO;
import participante.dto.ParticipanteOpcaoDTO;
import participante.dto.ParticipantePerfilDTO;
import persistencias.AService;

public class ParticipanteService extends AService <
    ParticipanteDashboardDTO, 
	ParticipantePerfilDTO, 
	ParticipanteOpcaoDTO,
	ParticipanteCriacaoDTO,
	Participante, 
	ParticipanteDAO, 
	ParticipanteFactory
> {

    public ParticipanteService(ParticipanteDAO dao, ParticipanteFactory factory) {
        super(dao, factory);
        //TODO Auto-generated constructor stub
    }

    @Override
    public Map<String, String> validador(ParticipanteCriacaoDTO criacaoDTO) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'validador'");
    }

    @Override
    public void salvar(ParticipanteCriacaoDTO criacaoDTO) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'salvar'");
    }

    @Override
    public ParticipantePerfilDTO getPerfil(String chave, String coluna) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getPerfil'");
    }

}
