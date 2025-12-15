package inscricao.parser;

import inscricao.Inscricao;
import inscricao.dto.InscricaoForm;
import persistencias.IParser;

public class InscricaoToForm implements IParser<Inscricao, InscricaoForm> {

    @Override public InscricaoForm toDTO(Inscricao entidade) {
        InscricaoForm dto = new InscricaoForm();

        dto.setId(entidade.getId());
        dto.setEventoId(entidade.getEventoId());
        dto.setUsuarioId(entidade.getUsuarioId());

        return dto;
    }

    @Override public Inscricao toEntidade(InscricaoForm dto) {
        Inscricao entidade = new Inscricao();

        entidade.setId(dto.getId());
        entidade.setEventoId(dto.getEventoId());
        entidade.setUsuarioId(dto.getUsuarioId());
        
        return entidade;
    }

    @Override public Class<InscricaoForm> getDTOClass() { return InscricaoForm.class; }
}