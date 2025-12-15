package palestra.parser;

import java.time.format.DateTimeFormatter;

import palestra.Palestra;
import palestra.dto.CronogramaItem;
import persistencias.IParser;

public class PalestraToCronogramaItem implements IParser<Palestra, CronogramaItem> {

    @Override public CronogramaItem toDTO(Palestra entidade) {
        CronogramaItem dto = new CronogramaItem();

        dto.setId(entidade.getId());
        dto.setNome(entidade.getNome());
        dto.setDuracao(entidade.getDuracao());
        if (entidade.getDataHoraInicio() != null) dto.setHoraInicio(entidade.getDataHoraInicio().format(DateTimeFormatter.ofPattern("HH:mm")));

        return dto;
    }

    @Override public Palestra toEntidade(CronogramaItem dto) { throw new UnsupportedOperationException("CronogramaItem é apenas leitura."); }
    @Override public Class<CronogramaItem> getDTOClass() { return CronogramaItem.class; }
}