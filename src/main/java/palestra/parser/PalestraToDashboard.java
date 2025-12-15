package palestra.parser;

import palestra.Palestra;
import palestra.dto.PalestraDashboard;
import persistencias.IParser;
import util.Formatadores;

public class PalestraToDashboard implements IParser<Palestra, PalestraDashboard> {

    @Override public PalestraDashboard toDTO(Palestra entidade) {
        PalestraDashboard dto = new PalestraDashboard();
        dto.setId(entidade.getId());
        dto.setNome(entidade.getNome());
        dto.setDuracao(entidade.getDuracao());
        if (entidade.getDataHoraInicio() != null) dto.setDataHora(Formatadores.dataTime2String(entidade.getDataHoraInicio()));

        return dto;
    }

    @Override public Palestra toEntidade(PalestraDashboard dto) {
        throw new UnsupportedOperationException("Dashboard é apenas leitura.");
    }

    @Override public Class<PalestraDashboard> getDTOClass() {
        return PalestraDashboard.class;
    }
}