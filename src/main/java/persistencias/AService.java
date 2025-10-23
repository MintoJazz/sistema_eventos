package persistencias;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public abstract class AService <
	DashboardDTO, 
	PerfilDTO, 
	OpcaoDTO,
	CriacaoDTO,
	Entidade, 
	DAO extends ADAO<Entidade>, 
	Factory extends IFactory<Entidade, DashboardDTO, PerfilDTO, OpcaoDTO, CriacaoDTO>
> {
	DAO dao;
	Factory factory;

	public List<DashboardDTO> listar() throws SQLException {
		List<Entidade> lista = null;

		try (Connection conexao = new Conexao().getConexao()) {
			lista = this.dao.getAll(conexao);
		}

		return this.factory.getDashboard(lista);
	}

    protected abstract Map<String, String> validador(CriacaoDTO criacaoDTO);

	protected abstract void salvar(CriacaoDTO criacaoDTO);
}