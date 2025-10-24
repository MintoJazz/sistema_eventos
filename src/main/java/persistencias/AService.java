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
	protected final DAO dao;
	protected final Factory factory;

	public AService(DAO dao, Factory factory) {
		this.dao = dao;
		this.factory = factory;
	}

	public List<DashboardDTO> listar() throws SQLException {
		List<Entidade> lista = null;

		try (Connection conexao = new Conexao().getConexao()) {
			lista = this.dao.getAll(conexao);
		}

		return this.factory.getDashboard(lista);
	}

    public abstract Map<String, String> validador(CriacaoDTO criacaoDTO);

	public abstract void salvar(CriacaoDTO criacaoDTO);

	public abstract PerfilDTO getPerfil(String chave, String coluna);
}