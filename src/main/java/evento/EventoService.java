package evento;

import java.util.Map;

import evento.dto.EventoCriacaoDTO;
import evento.dto.EventoDashboardDTO;
import evento.dto.EventoOpcaoDTO;
import evento.dto.EventoPerfilDTO;
import persistencias.AService;

public class EventoService extends AService <
    EventoDashboardDTO, 
	EventoPerfilDTO, 
	EventoOpcaoDTO,
	EventoCriacaoDTO,
	Evento, 
	EventoDAO, 
	EventoFactory
> {

    public EventoService(EventoDAO dao, EventoFactory factory) {
        super(dao, factory);
        //TODO Auto-generated constructor stub
    }

    @Override public Map<String, String> validador(EventoCriacaoDTO criacaoDTO) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'validador'");
    }

    @Override public void salvar(EventoCriacaoDTO criacaoDTO) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'salvar'");
    }

    @Override public EventoPerfilDTO getPerfil(String chave, String coluna) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getPerfil'");
    }

}
