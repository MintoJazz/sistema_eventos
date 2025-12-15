package palestrante.parser;

import palestrante.Palestrante;
import palestrante.dto.PalestranteForm;
import persistencias.IParser;

public class PalestranteToForm implements IParser<Palestrante, PalestranteForm> {

    @Override public PalestranteForm toDTO(Palestrante entidade) {
        PalestranteForm dto = new PalestranteForm();

        dto.setUsuarioId(entidade.getId());
        dto.setBiografia(entidade.getBiografia());
        
        return dto;
    }

    @Override public Palestrante toEntidade(PalestranteForm dto) {
        Palestrante entidade = new Palestrante();
        
        entidade.setId(dto.getUsuarioId()); 
        entidade.setBiografia(dto.getBiografia());
        
        return entidade;
    }

    @Override public Class<PalestranteForm> getDTOClass() { return PalestranteForm.class; }
}