package palestrante;

import java.util.List;

import palestrante.dto.PalestranteCriacaoDTO;
import palestrante.dto.PalestranteDashboardDTO;
import palestrante.dto.PalestranteOpcaoDTO;
import palestrante.dto.PalestrantePerfilDTO;
import persistencias.IFactory;

public class PalestranteFactory implements IFactory<Palestrante, PalestranteDashboardDTO, PalestrantePerfilDTO, PalestranteOpcaoDTO, PalestranteCriacaoDTO>{

    @Override public List<PalestranteDashboardDTO> getDashboard(List<Palestrante> entidades) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getDashboard'");
    }

    @Override public List<PalestranteOpcaoDTO> getOpcao(List<Palestrante> entidades) {
        List<PalestranteOpcaoDTO> lista = null;
        for (Palestrante palestrante : entidades) {
            PalestranteOpcaoDTO dto = new PalestranteOpcaoDTO();

            dto.setId(palestrante.getId());
            dto.setNome(palestrante.getNome());

            lista.add(dto);
        }

        return lista;
    }

    @Override public PalestrantePerfilDTO getPerfil(Palestrante entidade) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getPerfil'");
    }

    @Override public Palestrante toEntidade(PalestranteCriacaoDTO criacaoDTO) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'toEntidade'");
    }

}
