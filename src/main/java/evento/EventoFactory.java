package evento;

import java.util.List;

import evento.dto.EventoCriacaoDTO;
import evento.dto.EventoDashboardDTO;
import evento.dto.EventoOpcaoDTO;
import evento.dto.EventoPerfilDTO;
import persistencias.IFactory;

public class EventoFactory implements IFactory <
    Evento, EventoDashboardDTO, EventoPerfilDTO, EventoOpcaoDTO, EventoCriacaoDTO
> {

    @Override
    public List<EventoDashboardDTO> getDashboard(List<Evento> entidades) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getDashboard'");
    }

    @Override
    public List<EventoOpcaoDTO> getOpcao(List<Evento> entidades) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getOpcao'");
    }

    @Override
    public EventoPerfilDTO getPerfil(Evento entidade) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getPerfil'");
    }

    @Override
    public Evento toEntidade(EventoCriacaoDTO criacaoDTO) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'toEntidade'");
    }

}
