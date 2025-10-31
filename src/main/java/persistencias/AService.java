package persistencias;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AService <
	DashboardDTO,
	PerfilDTO,
	CriacaoDTO,
	OpcaoDTO,
	Entidade extends IGetId, 
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
		try (Connection conexao = new Conexao().getConexao()) {
			return this.listar(conexao);
		}
	}

	public List<DashboardDTO> listar(Connection conexao) throws SQLException {
		return this.factory.getDashboard(this.dao.getAll(conexao));
	}

    public abstract Map<String, String> validador(CriacaoDTO criacaoDTO) throws SQLException;

	public abstract void salvar(CriacaoDTO criacaoDTO) throws SQLException;

	public abstract PerfilDTO getPerfil(String chave, String coluna) throws SQLException;

    public Map<String,Object> elementosFormulario() throws SQLException {
		return new HashMap<>();
	}

	public List<OpcaoDTO> getOpcoes(Connection conexao) throws SQLException {
		return factory.getOpcao(dao.getAll(conexao));
	}

	public boolean existe(int id, Connection conexao) throws SQLException {
		return this.dao.getOne(conexao, "id", id) != null;
	}
}