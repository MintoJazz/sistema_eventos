package persistencias;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import exceptions.ChaveNaoGeradaException;

public abstract class ADAO<Entidade extends IGetId> {
	private static final ObjectMapper objectMapper = new ObjectMapper();

	protected String tabela;
	protected String querySelectAll = "SELECT * FROM {tabela};";
	protected String querySelectOne = "SELECT * FROM {tabela} WHERE id = ?;";
	protected String queryDelete = "DELETE FROM {tabela} WHERE id = ?;";
	protected String queryInsert, queryUpdate;
	protected List<String> colunasUnicas;

	public ADAO(String tabela, List<String> colunasUnicas) {
		this.tabela = tabela;
		this.colunasUnicas = colunasUnicas;
		this.querySelectAll = this.querySelectAll.replace("{tabela}", tabela);
		this.querySelectOne = this.querySelectOne.replace("{tabela}", tabela);
		this.queryDelete = this.querySelectOne.replace("{tabela}", tabela);
	}

	protected Map<String, Object> parseJsonStringToMap(String jsonString) {
        if (jsonString == null || jsonString.isBlank() || jsonString.equals("{}")) return new HashMap<>();

        try {
            return objectMapper.readValue(jsonString, new TypeReference<Map<String, Object>>() {});
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Falha ao parsear a string JSON: " + jsonString, e);
        }
    }
	
	protected String parseMapToJsonString(Map<String, Object> map) {
        if (map == null) return null;

        try {
            return objectMapper.writeValueAsString(map);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Falha ao converter Map para String JSON", e);
        }
    }
	
	// CREATE
	
	protected abstract void mapAdd(PreparedStatement preparedStatement, Entidade entidade) throws SQLException;

	protected PreparedStatement setPreparedStatement(Connection conexao, Entidade entidade) throws SQLException {
		PreparedStatement preparedStatement = conexao.prepareStatement(this.queryInsert, Statement.RETURN_GENERATED_KEYS);
		mapAdd(preparedStatement, entidade); 
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

	protected abstract Entidade mapEntidade(ResultSet resultSet) throws SQLException;

	public List<Entidade> getAll(Connection conexao) throws SQLException {
		List<Entidade> lista = new ArrayList<>();
		
		try (
			PreparedStatement preparedStatement = conexao.prepareStatement(this.querySelectAll);
			ResultSet resultSet = preparedStatement.executeQuery();
		) {
			while(resultSet.next()) lista.add(this.mapEntidade(resultSet));
		}

		return lista;
	} 

	// READ ONE

	protected PreparedStatement setPreparedStatement(Connection conexao, String coluna, Object valor) throws SQLException {
		if (!colunasUnicas.contains(coluna)) throw new IllegalArgumentException("A coluna '" + coluna + "' não é uma coluna de busca válida ou segura.");

		String query = this.querySelectOne.replace("{coluna}", coluna);
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

	// UPDATE

	public int atualizar(Connection conexao, Entidade entidade) throws SQLException {
		try (PreparedStatement preparedStatement = conexao.prepareStatement(this.queryUpdate)) {
			this.mapUpdate(preparedStatement, entidade);
			return preparedStatement.executeUpdate();
		} catch (SQLException e) {
        	throw new RuntimeException("Erro de banco de dados ao atualizar entidade: " + e.getMessage(), e);
    	}
	}

	protected abstract void mapUpdate(PreparedStatement preparedStatement, Entidade entidade) throws SQLException;

	// DELETE

	public int apagar(Connection conexao, Entidade entidade) {
		try (PreparedStatement preparedStatement = conexao.prepareStatement(this.queryDelete)) {
			preparedStatement.setInt(1, entidade.getId());
			return preparedStatement.executeUpdate();
		} catch (SQLException e) {
        	throw new RuntimeException("Erro de banco de dados ao apagar entidade: " + e.getMessage(), e);
    	}
	}
}