package palestra.parser;

import palestra.Palestra;
import palestra.dto.PalestraResumo;
import persistencias.IParser;
import util.Formatadores;

public class PalestraToResumo implements IParser<Palestra, PalestraResumo> {

    @Override public PalestraResumo toDTO(Palestra entidade) {
        PalestraResumo resumo = new PalestraResumo();

        resumo.setId(entidade.getId());
        resumo.setNome(entidade.getNome());
        if (entidade.getDataHoraInicio() != null) resumo.setDataHora(Formatadores.dataTime2String(entidade.getDataHoraInicio()));

        return resumo;
    }

    @Override public Palestra toEntidade(PalestraResumo dto) { throw new UnsupportedOperationException("Resumo é apenas leitura."); }
    @Override public Class<PalestraResumo> getDTOClass() { return PalestraResumo.class; }
}