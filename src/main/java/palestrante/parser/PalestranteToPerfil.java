package palestrante.parser;

import palestrante.Palestrante;
import palestrante.dto.PalestrantePerfil;
import persistencias.IParser;

public class PalestranteToPerfil implements IParser<Palestrante, PalestrantePerfil> {

    @Override public PalestrantePerfil toDTO(Palestrante entidade) {
        PalestrantePerfil dto = new PalestrantePerfil();
        
        dto.setId(entidade.getId());
        dto.setNome(entidade.getNome());
        dto.setCpf(entidade.getCpf());
        dto.setEmail(entidade.getEmail());
        dto.setBiografia(entidade.getBiografia());
        
        return dto;
    }

    @Override public Palestrante toEntidade(PalestrantePerfil dto) {
        throw new UnsupportedOperationException("Perfil é apenas leitura.");
    }

    @Override public Class<PalestrantePerfil> getDTOClass() {
        return PalestrantePerfil.class;
    }
}