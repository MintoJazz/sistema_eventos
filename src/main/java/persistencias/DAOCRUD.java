package persistencias;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import exceptions.ChaveNaoGeradaException;

public abstract class DAOCRUD<Entidade> {
    protected String tabela;
    
    protected String querySelectAll = "SELECT * FROM {tabela};";
    protected String querySelectOne = "SELECT * FROM {tabela} WHERE id = ?;";
    protected String queryDelete = "DELETE FROM {tabela} WHERE id = ?;";
    
    protected String queryInsert;
    protected String queryUpdate;

    public DAOCRUD(String tabela) {
        this.tabela = tabela;
        
        this.querySelectAll = this.querySelectAll.replace("{tabela}", tabela);
        this.querySelectOne = this.querySelectOne.replace("{tabela}", tabela);
        this.queryDelete = this.queryDelete.replace("{tabela}", tabela);
    }
    
    // --- CREATE ---
    protected abstract void mapAdd(PreparedStatement preparedStatement, Entidade entidade) throws SQLException;

    protected PreparedStatement setPreparedStatement(Connection conexao, Entidade entidade) throws SQLException {
        PreparedStatement preparedStatement = conexao.prepareStatement(this.queryInsert, Statement.RETURN_GENERATED_KEYS);
        mapAdd(preparedStatement, entidade); 
        return preparedStatement;
    }

    public int insert(Connection conexao ,Entidade entidade) throws SQLException {
        try (PreparedStatement preparedStatement = this.setPreparedStatement(conexao, entidade)) {
            preparedStatement.executeUpdate();
             
            try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                if (resultSet.next()) return resultSet.getInt(1);
                else throw new ChaveNaoGeradaException("A inserção foi bem-sucedida, mas nenhum ID foi retornado.");
            }
        }
    }

    // --- READ (Mapeamento) ---
    protected abstract Entidade mapEntidade(ResultSet resultSet) throws SQLException;

    public java.util.List<Entidade> selectAll(Connection conexao) throws SQLException {
        java.util.List<Entidade> lista = new java.util.ArrayList<>();
        try (
            PreparedStatement preparedStatement = conexao.prepareStatement(this.querySelectAll);
            ResultSet resultSet = preparedStatement.executeQuery();
        ) {
            while(resultSet.next()) lista.add(this.mapEntidade(resultSet));
        }
        return lista;
    } 

    // --- READ ONE (Refatorado para ID) ---
    
    public Entidade selectOne(Connection conexao, int id) throws SQLException {
        try (PreparedStatement preparedStatement = conexao.prepareStatement(this.querySelectOne)) {
            preparedStatement.setInt(1, id); // Direto no ID

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if(resultSet.next()) return this.mapEntidade(resultSet);
                return null;
            }
        }
    }

    // --- UPDATE ---
    protected abstract void mapUpdate(PreparedStatement preparedStatement, Entidade entidade) throws SQLException;

    public int update(Connection conexao, Entidade entidade) throws SQLException {
        try (PreparedStatement preparedStatement = conexao.prepareStatement(this.queryUpdate)) {
            this.mapUpdate(preparedStatement, entidade);
            return preparedStatement.executeUpdate();
        }
    }

    // --- DELETE ---
    public int delete(Connection conexao, int id) throws SQLException {
        try (PreparedStatement preparedStatement = conexao.prepareStatement(this.queryDelete)) {
            preparedStatement.setInt(1, id);
            return preparedStatement.executeUpdate();
        }
    }
}