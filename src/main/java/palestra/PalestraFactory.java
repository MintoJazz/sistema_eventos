package palestra;

import java.util.List;

import palestra.dto.PalestraCriacaoDTO;
import palestra.dto.PalestraDashboardDTO;
import palestra.dto.PalestraOpcaoDTO;
import palestra.dto.PalestraPerfilDTO;
import persistencias.IFactory;

public class PalestraFactory implements IFactory <Palestra, PalestraDashboardDTO, PalestraPerfilDTO, PalestraOpcaoDTO, PalestraCriacaoDTO> {

    @Override
    public List<PalestraDashboardDTO> getDashboard(List<Palestra> entidades) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getDashboard'");
    }

    @Override
    public List<PalestraOpcaoDTO> getOpcao(List<Palestra> entidades) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getOpcao'");
    }

    @Override
    public PalestraPerfilDTO getPerfil(Palestra entidade) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getPerfil'");
    }

    @Override
    public Palestra toEntidade(PalestraCriacaoDTO criacaoDTO) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'toEntidade'");
    }
    
}
