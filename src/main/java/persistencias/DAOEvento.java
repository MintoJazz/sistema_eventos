package persistencias;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import negocio.Evento;

public class DAOEvento extends ADAO<Evento>{

    public DAOEvento() {
        this.nomeTabela = "evento";
        this.colunasUnicas = Arrays.asList("id");
        
    }

    // @Override public List<Evento> getByQuery(String query, Object... parametros) throws SQLException {
    //     List<Evento> lista = new ArrayList<>();

    //     try (
    //         Connection conexao = new Conexao().getConexao();
    //         PreparedStatement preparedStatement = conexao.prepareStatement(query);
    //     ) {
    //         if (parametros != null) for (int i = 0; i < parametros.length; i++) preparedStatement.setObject(i + 1, parametros[i]);
    //     }
        
    //     return lista;
    // }

    @Override
    public void setQueryAdd(Evento t) {
    }

    @Override
    protected List<Evento> runGetQuery(PreparedStatement preparedStatement, List<Evento> lista) throws SQLException {
        try (ResultSet rs = preparedStatement.executeQuery()) {
            while (rs.next()) {
                lista.add(
                    new Evento(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getDate("data_inicio"),
                        rs.getDate("data_fim"),
                        rs.getString("localidade")
                    )
                );
            }
        }

        return lista;
    }
}
