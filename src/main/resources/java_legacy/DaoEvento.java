package persistencias;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import negocio.Evento;

public class DaoEvento extends DaoAbstracao<Evento>{

    @Override public List<Evento> getAll() throws SQLException {
        List<Evento> lista = new ArrayList<>();
        PSQL banco = new PSQL();
        ResultSet resultSet = banco.getQuery("SELECT * FROM evento", null);
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
        } banco.fechar();
        return lista;
    }

    @Override public Evento get4Id(int id) throws SQLException {
        PSQL banco = new PSQL();
        ResultSet resultSet = banco.getQuery("SELECT * FROM evento WHERE id = ?", id);
        while (resultSet.next()) {
            return new Evento(
                resultSet.getInt("id"),
                resultSet.getString("nome"),
                resultSet.getDate("data_inicio"),
                resultSet.getDate("data_fim"),
                resultSet.getString("localidade")
            );
        } banco.fechar();
        return null;
    }
}
