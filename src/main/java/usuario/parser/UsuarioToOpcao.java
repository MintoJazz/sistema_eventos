package usuario.parser;

import persistencias.OpcaoDTO;
import persistencias.IParser;
import usuario.Usuario;

public class UsuarioToOpcao implements IParser<Usuario, OpcaoDTO> {

    @Override public OpcaoDTO toDTO(Usuario entidade) {
        OpcaoDTO dto = new OpcaoDTO();
        
        dto.setId(entidade.getId());
        dto.setNome(entidade.getNome());
        
        return dto;
    }

    @Override public Usuario toEntidade(OpcaoDTO dto) {
        throw new UnsupportedOperationException("OpcaoDTO é apenas para leitura (Visualização/Seleção).");
    }

    @Override public Class<OpcaoDTO> getDTOClass() {
        return OpcaoDTO.class;
    }
}