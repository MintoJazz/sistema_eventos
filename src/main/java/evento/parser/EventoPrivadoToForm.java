package evento.parser;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import evento.privado.EventoPrivado;
import evento.dto.EventoPrivadoForm;
import persistencias.IParser;

public class EventoPrivadoToForm implements IParser<EventoPrivado, EventoPrivadoForm> {

    @Override public Class<EventoPrivadoForm> getDTOClass() { return EventoPrivadoForm.class; }
    @Override public EventoPrivadoForm toDTO(EventoPrivado entidade) {
        EventoPrivadoForm dto = new EventoPrivadoForm();

        dto.setId(entidade.getId());
        dto.setNome(entidade.getNome());
        dto.setLocalidade(entidade.getLocalidade());
        dto.setOrganizadorId(entidade.getOrganizadorId());
        if (entidade.getDataInicio() != null) dto.setDataInicio(entidade.getDataInicio().toString());
        if (entidade.getDataFim() != null) dto.setDataFim(entidade.getDataFim().toString());
        dto.setSenhaAcesso(entidade.getSenhaAcesso());
        dto.setConvidados(entidade.getConvidados());
        dto.setPrivado(true);

        return dto;
    }

    @Override public EventoPrivado toEntidade(EventoPrivadoForm dto) {
        EventoPrivado entidade = new EventoPrivado();

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

        entidade.setSenhaAcesso(dto.getSenhaAcesso());
        entidade.setConvidados(dto.getConvidados());

        return entidade;
    }
}