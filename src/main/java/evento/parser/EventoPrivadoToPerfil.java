package evento.parser;

import java.util.ArrayList;
import java.util.Map;

import evento.dto.EventoPrivadoPerfil;
import evento.privado.EventoPrivado;
import persistencias.IParser;
import util.Formatadores;

public class EventoPrivadoToPerfil implements IParser<EventoPrivado, EventoPrivadoPerfil> {

    @Override public EventoPrivado toEntidade(EventoPrivadoPerfil dto) { throw new UnsupportedOperationException("Perfil é apenas leitura."); }
    @Override public Class<EventoPrivadoPerfil> getDTOClass() { return EventoPrivadoPerfil.class; }
    @Override public EventoPrivadoPerfil toDTO(EventoPrivado entidade) {
        EventoPrivadoPerfil dto = new EventoPrivadoPerfil();

        dto.setId(entidade.getId());
        dto.setNome(entidade.getNome());
        dto.setLocalidade(entidade.getLocalidade());
        if (entidade.getDataInicio() != null) dto.setDataInicio(Formatadores.data2String(entidade.getDataInicio()));
        if (entidade.getDataFim() != null) dto.setDataFim(Formatadores.data2String(entidade.getDataFim()));
        dto.setSenhaAcesso(entidade.getSenhaAcesso());
        dto.setConvidados(entidade.getConvidados());
        dto.setCronograma(new ArrayList<>());

        if (entidade.getDetalhes() != null && !entidade.getDetalhes().isBlank()) {
            Map<String, Object> json = Formatadores.parseJsonStringToMap(entidade.getDetalhes());
            if (json.containsKey("documentacao_legal")) {
                @SuppressWarnings("unchecked")
                Map<String, Object> doc = (Map<String, Object>) json.get("documentacao_legal");
                dto.setTemArquivo(true);
                dto.setNomeArquivo((String) doc.get("nome_arquivo"));
            }
        }

        return dto;
    }
    
}