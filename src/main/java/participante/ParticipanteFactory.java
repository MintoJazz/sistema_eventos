package participante;

import java.util.List;

import participante.dto.ParticipanteCriacaoDTO;
import participante.dto.ParticipanteDashboardDTO;
import participante.dto.ParticipanteOpcaoDTO;
import participante.dto.ParticipantePerfilDTO;
import persistencias.IFactory;

public class ParticipanteFactory implements IFactory<Participante, ParticipanteDashboardDTO, ParticipantePerfilDTO, ParticipanteOpcaoDTO, ParticipanteCriacaoDTO> {

    @Override
    public List<ParticipanteDashboardDTO> getDashboard(List<Participante> entidades) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getDashboard'");
    }

    @Override
    public List<ParticipanteOpcaoDTO> getOpcao(List<Participante> entidades) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getOpcao'");
    }

    @Override
    public ParticipantePerfilDTO getPerfil(Participante entidade) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getPerfil'");
    }

    @Override
    public Participante toEntidade(ParticipanteCriacaoDTO criacaoDTO) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'toEntidade'");
    }

}
