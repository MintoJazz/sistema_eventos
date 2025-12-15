package palestra.parser;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

import palestra.Palestra;
import palestra.dto.PalestraForm;
import persistencias.IParser;

public class PalestraToForm implements IParser<Palestra, PalestraForm> {

    @Override 
    public PalestraForm toDTO(Palestra entidade) {
        PalestraForm dto = new PalestraForm();
        dto.setId(entidade.getId());
        dto.setNome(entidade.getNome());
        dto.setDuracao(entidade.getDuracao());
        dto.setEventoId(entidade.getEventoId());
        if (entidade.getDataHoraInicio() != null) dto.setDataHoraInicio(entidade.getDataHoraInicio().toString());
        if (entidade.getPalestrantesIds() != null) dto.setPalestrantesIds(entidade.getPalestrantesIds());

        return dto;
    }

    @Override public Palestra toEntidade(PalestraForm dto) {
        Palestra entidade = new Palestra();
        entidade.setId(dto.getId());
        entidade.setNome(dto.getNome());
        entidade.setDuracao(dto.getDuracao());
        entidade.setEventoId(dto.getEventoId());
        if (dto.getPalestrantesIds() != null) entidade.setPalestrantesIds(dto.getPalestrantesIds());

        if (dto.getDataHoraInicio() != null && !dto.getDataHoraInicio().isBlank()) try {
            entidade.setDataHoraInicio(LocalDateTime.parse(dto.getDataHoraInicio()));
        } catch (DateTimeParseException e) {
            entidade.setDataHoraInicio(null);
        }

        return entidade;
    }

    @Override 
    public Class<PalestraForm> getDTOClass() {
        return PalestraForm.class;
    }
}