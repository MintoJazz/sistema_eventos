package persistencias;

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

    @Override public List<Evento> getByQuery(String query, Object... parametros) throws SQLException {
        ResultSet resultSet = this.getQuery(query, parametros);
        List<Evento> lista = new ArrayList<>();
        while (resultSet.next()) {
            lista.add(
                new Evento(
                    resultSet.getInt("id"),
                    resultSet.getString("nome"),
                    resultSet.getDate("data_inicio"),
                    resultSet.getDate("data_fim"),
                    resultSet.getString("localidade")
                )
            );
        }
        this.fechar();
        return lista;
    }

}
