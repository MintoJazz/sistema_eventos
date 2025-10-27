package evento;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

import persistencias.ADAO;

public class EventoDAO extends ADAO<
    Evento
> {

    public EventoDAO() {
        super("evento", Arrays.asList("id"));
        this.queryInsert = "INSERT INTO eventos (nome, data_inicio, data_fim, localidade, detalhes, metadados, material) VALUES (?, ?, ?, ?, ?::jsonb, ?::jsonb, ?)";
    }

    @Override protected void mapAddQuery(PreparedStatement preparedStatement, Evento entidade) throws SQLException {
        preparedStatement.setString(1,entidade.getNome());
        preparedStatement.setDate(2,entidade.getDataInicio());
        preparedStatement.setDate(3,entidade.getDataFim());
        preparedStatement.setString(4,entidade.getLocal());
        preparedStatement.setString(5,this.parseMapToJsonString(entidade.getDetalhes()));
        preparedStatement.setString(6,this.parseMapToJsonString(entidade.getMetadados()));
        preparedStatement.setBytes(7,entidade.getMaterial());
    }

    @Override protected Evento mapEntidade(ResultSet resultSet) throws SQLException {
        return new Evento(
            resultSet.getInt("id"),
            resultSet.getString("nome"),
            resultSet.getString("local"),
            resultSet.getDate("data_inicio"),
            resultSet.getDate("data_fim"),
            this.parseJsonStringToMap(resultSet.getString("detalhes")),
            this.parseJsonStringToMap(resultSet.getString("metadados")),
            resultSet.getBytes("material")
        );
    }
}
