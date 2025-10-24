package persistencias;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import exceptions.ChaveNaoGeradaException;

public abstract class ADAO<Entidade> {
	String tabela, queryAdd;
	List<String> colunasUnicas;

	// ADD

	protected abstract void mapAddQuery(PreparedStatement preparedStatement, Entidade entidade) throws SQLException;

	protected PreparedStatement setPreparedStatement(Connection conexao, Entidade entidade) throws SQLException {
		PreparedStatement preparedStatement = conexao.prepareStatement(this.queryAdd, Statement.RETURN_GENERATED_KEYS);
		mapAddQuery(preparedStatement, entidade); 
		return preparedStatement;
	}

	public int adicionar(Connection conexao ,Entidade entidade) throws SQLException {
		try (
			PreparedStatement preparedStatement = this.setPreparedStatement(conexao, entidade);
		) {
			preparedStatement.executeUpdate();
			 
			try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                if (resultSet.next()) return resultSet.getInt(1);
				else throw new ChaveNaoGeradaException("A inserção foi bem-sucedida, mas nenhum ID foi retornado pelo banco.");
            }
		} catch (SQLException e) {
        	throw new RuntimeException("Erro de banco de dados ao adicionar entidade: " + e.getMessage(), e);
    	}
	}

	// READ ALL

	public ADAO(String tabela, String queryAdd, List<String> colunasUnicas) {
		this.tabela = tabela;
		this.queryAdd = queryAdd;
		this.colunasUnicas = colunasUnicas;
	}

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

	// READ ONE

	protected PreparedStatement setPreparedStatement(Connection conexao, String coluna, Object valor) throws SQLException {
		if (!colunasUnicas.contains(coluna)) throw new IllegalArgumentException("A coluna '" + coluna + "' não é uma coluna de busca válida ou segura.");

		String query = "SELECT * FROM " + this.tabela + " WHERE " + coluna + " = ?;";
		PreparedStatement preparedStatement = conexao.prepareStatement(query);
		preparedStatement.setObject(1, valor);
		return preparedStatement;
	}

	public Entidade getOne(Connection conexao, String coluna, Object valor) throws SQLException {
		try (
			PreparedStatement preparedStatement = this.setPreparedStatement(conexao, coluna, valor);
			ResultSet resultSet = preparedStatement.executeQuery();
		) {
			if(resultSet.next()) return this.mapEntidade(resultSet);
			return null;
		}
	}
}