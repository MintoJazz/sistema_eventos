package persistencias;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class ADAO<Entidade> {
	String tabela;

	protected abstract Entidade mapEntidade(ResultSet resultSet);

	public List<Entidade> getAll(Connection conexao) throws SQLException {
		List<Entidade> lista = new ArrayList<>();
		String query = "SELECT * FROM " + this.tabela + ";";
		
		try (
			PreparedStatement preparedStatement = conexao.prepareStatement(query);
			ResultSet resultSet = preparedStatement.executeQuery();
		) {
			while(resultSet.next()) lista.add(this.mapEntidade(resultSet));
		}

		return lista;
	}
}