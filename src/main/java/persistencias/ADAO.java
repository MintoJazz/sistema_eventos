package persistencias;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public abstract class ADAO<T> implements IDAO<T> {
    protected String nomeTabela;
    protected List<String> colunasUnicas;
    
    protected abstract List<T> runGetQuery(PreparedStatement preparedStatement, List<T> lista) throws SQLException;

    @Override public List<T> getByQuery(String query, Object... parametros) throws SQLException {
        List<T> lista = new ArrayList<>();

        try (
            Connection conexao = new Conexao().getConexao();
            PreparedStatement preparedStatement = conexao.prepareStatement(query);
        ) {
            if (parametros != null) for (int i = 0; i < parametros.length; i++) preparedStatement.setObject(i + 1, parametros[i]);
            lista = this.runGetQuery(preparedStatement, lista);
        }

        return lista;
    }

    @Override public List<T> getAll() throws SQLException {
        return getByQuery("SELECT * FROM " + this.nomeTabela, null);
    }

    @Override public T getById(int id) throws SQLException{
        return get1ByParam("id", id);
    }

    public T get1ByParam(String parametro, Object valor) throws SQLException {
        if (!this.colunasUnicas.contains(parametro)) return null;
        return getByQuery("SELECT * FROM " + nomeTabela + " WHERE " + parametro + " = ?", valor).getFirst();
    }

    protected int addWithKey(String query, Object... parametros) throws SQLException {
        try (
            Connection conexao = new Conexao().getConexao();
            PreparedStatement preparedStatement = conexao.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
        ) {
            if (parametros != null) for (int i = 0; i < parametros.length; i++) preparedStatement.setObject(i + 1, parametros[i]);
            preparedStatement.executeUpdate();

            try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                if (resultSet.next()) return resultSet.getInt(1); 
            }

            throw new SQLException("A inserção falhou, nenhum ID foi retornado.");
        }
    }

    protected int addNoKey(String query, Object... parametros) throws SQLException {
        try (
            Connection conexao = new Conexao().getConexao();
            PreparedStatement preparedStatement = conexao.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
        ) {
            if (parametros != null) for (int i = 0; i < parametros.length; i++) preparedStatement.setObject(i + 1, parametros[i]);
            return preparedStatement.executeUpdate();
        }
    }
}