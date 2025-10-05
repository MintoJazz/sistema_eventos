package persistencias;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public abstract class ADAO<T> implements IDAO<T> {
    private Connection conexao;
    private PreparedStatement preparedStatement;
    protected String nomeTabela;
    protected List<String> colunasUnicas;

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

    public void abrir(String query, Object... parametros) throws SQLException {
        this.conexao = new Conexao().getConexao();
        this.preparedStatement = conexao.prepareStatement(query);
        if (parametros != null) for (int i = 0; i < parametros.length; i++) this.preparedStatement.setObject(i + 1, parametros[i]);
    }
    
    public void fechar() throws SQLException {
        this.preparedStatement.close();
        this.conexao.close();
    }

    public ResultSet getQuery(String query, Object... parametros) throws SQLException {
        this.abrir(query, parametros);
        return preparedStatement.executeQuery();
    }

    public int setQuery(String query, Object... parametros) throws SQLException {
        this.abrir(query, parametros);
        this.fechar();
        return preparedStatement.executeUpdate();
    }
}
