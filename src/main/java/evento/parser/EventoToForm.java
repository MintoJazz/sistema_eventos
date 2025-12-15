package evento.parser;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import evento.Evento;
import evento.dto.EventoForm;
import persistencias.IParser;

public class EventoToForm implements IParser<Evento, EventoForm> {

    @Override public EventoForm toDTO(Evento entidade) {
        EventoForm dto = new EventoForm();
        
        dto.setId(entidade.getId());
        dto.setNome(entidade.getNome());
        dto.setLocalidade(entidade.getLocalidade());
        dto.setOrganizadorId(entidade.getOrganizadorId());
        if (entidade.getDataInicio() != null) dto.setDataInicio(entidade.getDataInicio().toString());
        if (entidade.getDataFim() != null) dto.setDataFim(entidade.getDataFim().toString());

        return dto;
    }

    @Override public Evento toEntidade(EventoForm dto) {
        Evento entidade = new Evento();
        
        entidade.setId(dto.getId());
        entidade.setNome(dto.getNome());
        entidade.setLocalidade(dto.getLocalidade());
        entidade.setOrganizadorId(dto.getOrganizadorId());

        if (dto.getDataInicio() != null && !dto.getDataInicio().isBlank()) try {
            entidade.setDataInicio(LocalDate.parse(dto.getDataInicio()));
        } catch (DateTimeParseException e) {
            entidade.setDataInicio(null);
        }

        if (dto.getDataFim() != null && !dto.getDataFim().isBlank()) try {
            entidade.setDataFim(LocalDate.parse(dto.getDataFim()));
        } catch (DateTimeParseException e) {
            entidade.setDataFim(null);
        }

        return entidade;
    }

    @Override public Class<EventoForm> getDTOClass() {
        return EventoForm.class;
    }
}