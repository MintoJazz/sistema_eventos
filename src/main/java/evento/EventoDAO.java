package evento;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import persistencias.ADAO;

public class EventoDAO extends ADAO<
    Evento
> {

    public EventoDAO() {
        super("evento", Arrays.asList("id"));
        this.queryInsert = "INSERT INTO evento (nome, data_inicio, data_fim, localidade, detalhes, metadados, material) VALUES (?, ?, ?, ?, ?::jsonb, ?::jsonb, ?);";
        this.queryUpdate = "UPDATE evento SET nome = ?, data_inicio = ?, data_fim = ?, localidade z= ?, detalhes = ?::jsonb, metadados = ?::jsonb, material = ? WHERE id = ?;";
    }
    
    @Override protected Evento mapEntidade(ResultSet resultSet) throws SQLException {
        return new Evento(
            resultSet.getInt("id"),
            resultSet.getString("nome"),
            resultSet.getString("local"),
            resultSet.getObject("data_inicio", LocalDate.class),
            resultSet.getObject("data_fim", LocalDate.class),
            this.parseJsonStringToMap(resultSet.getString("detalhes")),
            this.parseJsonStringToMap(resultSet.getString("metadados")),
            resultSet.getBytes("material")
        );
    }

    @Override protected void mapAdd(PreparedStatement preparedStatement, Evento entidade) throws SQLException {
        preparedStatement.setString(1,entidade.getNome());
        preparedStatement.setObject(2,entidade.getDataInicio());
        preparedStatement.setObject(3,entidade.getDataFim());
        preparedStatement.setString(4,entidade.getLocal());
        preparedStatement.setString(5,this.parseMapToJsonString(entidade.getDetalhes()));
        preparedStatement.setString(6,this.parseMapToJsonString(entidade.getMetadados()));
        preparedStatement.setBytes(7,entidade.getMaterial());
    }

    @Override protected void mapUpdate(PreparedStatement preparedStatement, Evento entidade) throws SQLException {
        preparedStatement.setString(1,entidade.getNome());
        preparedStatement.setObject(2,entidade.getDataInicio());
        preparedStatement.setObject(3,entidade.getDataFim());
        preparedStatement.setString(4,entidade.getLocal());
        preparedStatement.setString(5,this.parseMapToJsonString(entidade.getDetalhes()));
        preparedStatement.setString(6,this.parseMapToJsonString(entidade.getMetadados()));
        preparedStatement.setBytes(7,entidade.getMaterial());

        preparedStatement.setInt(8,entidade.getId());
    }

    public List<Evento> getAll(Connection conexao, int id) throws SQLException {
        List<Evento> lista = new ArrayList<>();
        
        try (PreparedStatement preparedStatement = conexao.prepareStatement("SELECT * FROM evento WHERE id = (SELECT evento_id FROM inscricao WHERE participante_id = ?);");) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) lista.add(this.mapEntidade(resultSet));
            }

            return lista;
        }
    }
}
