package palestrante;

import java.sql.SQLException;
import java.util.Map;

import palestrante.dto.PalestranteCriacaoDTO;
import palestrante.dto.PalestranteDashboardDTO;
import palestrante.dto.PalestranteOpcaoDTO;
import palestrante.dto.PalestrantePerfilDTO;
import persistencias.AService;

public class PalestranteService  extends AService <
    PalestranteDashboardDTO,
	PalestrantePerfilDTO,
	PalestranteCriacaoDTO,
	PalestranteOpcaoDTO,
	Palestrante, 
	PalestranteDAO, 
	PalestranteFactory
> {

	public PalestranteService(PalestranteDAO dao, PalestranteFactory factory) {
		super(dao, factory);
	}

	@Override
	public Map<String, String> validador(PalestranteCriacaoDTO criacaoDTO) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'validador'");
	}

	@Override
	public void salvar(PalestranteCriacaoDTO criacaoDTO) throws SQLException {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'salvar'");
	}

	@Override public PalestrantePerfilDTO getPerfil(String chave, String coluna) throws SQLException {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'getPerfil'");
	}
    
}
