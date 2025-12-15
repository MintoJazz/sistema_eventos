package evento.parser;

import evento.Evento;
import evento.dto.EventoDashboard;
import persistencias.IParser;
import util.Formatadores;

public class EventoToDashboard implements IParser<Evento, EventoDashboard> {

    @Override public EventoDashboard toDTO(Evento entidade) {
        EventoDashboard dto = new EventoDashboard();
        
        dto.setId(entidade.getId());
        dto.setLocalidade(entidade.getLocalidade());
        if (entidade.getDataInicio() != null) dto.setDataInicio(Formatadores.data2String(entidade.getDataInicio()));
        if (entidade.getDataFim() != null) dto.setDataFim(Formatadores.data2String(entidade.getDataFim()));

        if (entidade.isPrivado()) {
            dto.setNome(entidade.getNome() + " - Privado");
            
            dto.setLinkVer("/read/evento-privado/id/" + entidade.getId());
            dto.setLinkEditar("/update/evento-privado/" + entidade.getId());
            dto.setLinkDeletar("/delete/evento-privado/" + entidade.getId());
        } else {
            dto.setNome(entidade.getNome());
            
            dto.setLinkVer("/read/evento/id/" + entidade.getId());
            dto.setLinkEditar("/update/evento/" + entidade.getId());
            dto.setLinkDeletar("/delete/evento/" + entidade.getId());
        }

        return dto;
    }

    @Override public Evento toEntidade(EventoDashboard dto) { throw new UnsupportedOperationException("Dashboard é apenas leitura."); }
    @Override public Class<EventoDashboard> getDTOClass() { return EventoDashboard.class; }
}