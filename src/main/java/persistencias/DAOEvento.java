package persistencias;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import negocio.Evento;

public class DAOEvento extends ADAO<Evento>{

    public DAOEvento() {
        this.nomeTabela = "dashboard_eventos";
        this.colunasUnicas = Arrays.asList("id");
        
    }

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
                        rs.getString("localidade"),
                        rs.getString("status_evento")
                    )
                );
            }
        }

        return lista;
    }
}
