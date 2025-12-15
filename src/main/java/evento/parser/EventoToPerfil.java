package evento.parser;

import java.util.Map; // Import necessário

import evento.Evento;
import evento.dto.EventoPerfil;
import persistencias.IParser;
import persistencias.OpcaoDTO;
import util.Formatadores;

public class EventoToPerfil implements IParser<Evento, EventoPerfil> {

    @Override 
    public EventoPerfil toDTO(Evento entidade) {
        EventoPerfil dto = new EventoPerfil();
        
        dto.setId(entidade.getId());
        dto.setNome(entidade.getNome());
        dto.setLocalidade(entidade.getLocalidade());
        if (entidade.getDataInicio() != null) dto.setDataInicio(Formatadores.data2String(entidade.getDataInicio()));
        if (entidade.getDataFim() != null) dto.setDataFim(Formatadores.data2String(entidade.getDataFim()));

        if (entidade.getDetalhes() != null && !entidade.getDetalhes().isBlank()) {
            Map<String, Object> json = Formatadores.parseJsonStringToMap(entidade.getDetalhes());
            
            if (json.containsKey("documentacao_legal")) {
                @SuppressWarnings("unchecked") Map<String, Object> doc = (Map<String, Object>) json.get("documentacao_legal");
                
                dto.setTemArquivo(true);
                dto.setNomeArquivo((String) doc.get("nome_arquivo"));
            }
        }

        OpcaoDTO organizadorPlaceholder = new OpcaoDTO();
        organizadorPlaceholder.setId(entidade.getOrganizadorId());
        dto.setOrganizador(organizadorPlaceholder); 

        return dto;
    }

    @Override public Evento toEntidade(EventoPerfil dto) { throw new UnsupportedOperationException("Perfil é apenas leitura."); }
    @Override public Class<EventoPerfil> getDTOClass() { return EventoPerfil.class; }
}