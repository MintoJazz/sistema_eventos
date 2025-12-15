package palestrante.parser;

import palestrante.Palestrante;
import palestrante.dto.PalestranteDashboard;
import persistencias.IParser;

public class PalestranteToDashboard implements IParser<Palestrante, PalestranteDashboard> {

    @Override public PalestranteDashboard toDTO(Palestrante entidade) {
        PalestranteDashboard dto = new PalestranteDashboard();
        
        dto.setId(entidade.getId());
        dto.setNome(entidade.getNome());
        dto.setQuantidadePalestras(entidade.getQuantidadePalestras());
        
        return dto;
    }

    @Override public Palestrante toEntidade(PalestranteDashboard dto) {
        throw new UnsupportedOperationException("Dashboard é apenas leitura.");
    }

    @Override public Class<PalestranteDashboard> getDTOClass() {
        return PalestranteDashboard.class;
    }
}