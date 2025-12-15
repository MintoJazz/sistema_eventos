package palestra.parser;

import java.util.ArrayList;

import palestra.Palestra;
import palestra.dto.PalestraPerfil;
import persistencias.IParser;
import persistencias.OpcaoDTO;
import util.Formatadores;

public class PalestraToPerfil implements IParser<Palestra, PalestraPerfil> {

    @Override public PalestraPerfil toDTO(Palestra entidade) {
        PalestraPerfil dto = new PalestraPerfil();
        dto.setId(entidade.getId());
        dto.setNome(entidade.getNome());
        dto.setDuracao(entidade.getDuracao());

        if (entidade.getDataHoraInicio() != null) dto.setDataHora(Formatadores.dataTime2String(entidade.getDataHoraInicio()));


        OpcaoDTO evento = new OpcaoDTO();
        evento.setId(entidade.getEventoId());
        evento.setNome("Carregando...");
        dto.setEvento(evento);

        dto.setPalestrantes(new ArrayList<>());

        return dto;
    }

    @Override
    public Palestra toEntidade(PalestraPerfil dto) {
        throw new UnsupportedOperationException("Perfil é apenas leitura.");
    }

    @Override
    public Class<PalestraPerfil> getDTOClass() {
        return PalestraPerfil.class;
    }
}