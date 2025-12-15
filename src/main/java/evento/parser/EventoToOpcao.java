package evento.parser;

import evento.Evento;
import persistencias.IParser;
import persistencias.OpcaoDTO;

public class EventoToOpcao implements IParser<Evento, OpcaoDTO> {

    @Override
    public OpcaoDTO toDTO(Evento entidade) {
        OpcaoDTO dto = new OpcaoDTO();
        
        dto.setId(entidade.getId());
        dto.setNome(entidade.getNome());
        
        return dto;
    }

    @Override
    public Evento toEntidade(OpcaoDTO dto) {
        throw new UnsupportedOperationException("OpcaoDTO é apenas para leitura (Visualização/Seleção).");
    }

    @Override
    public Class<OpcaoDTO> getDTOClass() {
        return OpcaoDTO.class;
    }
}