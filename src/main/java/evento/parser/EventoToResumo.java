package evento.parser;

import evento.Evento;
import evento.dto.EventoResumo;
import persistencias.IParser;
import util.Formatadores;

public class EventoToResumo implements IParser<Evento, EventoResumo> {

    @Override public EventoResumo toDTO(Evento entidade) {
        EventoResumo resumo = new EventoResumo();
        
        resumo.setId(entidade.getId());
        resumo.setNome(entidade.getNome());
        resumo.setLocalidade(entidade.getLocalidade());
        if (entidade.getDataInicio() != null) resumo.setData(Formatadores.data2String(entidade.getDataInicio()));
        
        return resumo;
    }

    @Override public Evento toEntidade(EventoResumo dto) { throw new UnsupportedOperationException("Resumo é apenas leitura."); }
    @Override public Class<EventoResumo> getDTOClass() { return EventoResumo.class; }
}