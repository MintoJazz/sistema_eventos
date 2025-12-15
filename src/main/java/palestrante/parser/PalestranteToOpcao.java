package palestrante.parser;

import palestrante.Palestrante;
import persistencias.IParser;
import persistencias.OpcaoDTO;

public class PalestranteToOpcao implements IParser<Palestrante, OpcaoDTO> {

    @Override
    public OpcaoDTO toDTO(Palestrante entidade) {
        OpcaoDTO dto = new OpcaoDTO();
        dto.setId(entidade.getId());
        dto.setNome(entidade.getNome());
        return dto;
    }

    @Override
    public Palestrante toEntidade(OpcaoDTO dto) {
        throw new UnsupportedOperationException("OpcaoDTO é apenas leitura.");
    }

    @Override
    public Class<OpcaoDTO> getDTOClass() {
        return OpcaoDTO.class;
    }
}