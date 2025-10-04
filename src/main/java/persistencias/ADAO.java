package persistencias;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class ADAO<T> implements IDAO<T> {
    private Connection conexao;
    private PreparedStatement preparedStatement;

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
