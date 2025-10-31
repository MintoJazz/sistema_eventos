package palestra;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Arrays;

import persistencias.ADAO;

public class PalestraDAO extends ADAO<Palestra>{

    public PalestraDAO() {
        super("palestra", Arrays.asList("id"));

        this.queryInsert = "INSERT INTO palestra (nome, duracao, data_hora_inicio) VALUES (?, ?, ?);";
        this.queryUpdate = "UPDATE palestra SET nome = ?, duracao = ?, data_hora_inicio = ? WHERE id = ?;";
    }

    @Override protected Palestra mapEntidade(ResultSet resultSet) throws SQLException {
        return new Palestra(
            resultSet.getInt("id"),
            resultSet.getInt("duracao"),
            resultSet.getInt("evento_id"),
            resultSet.getString("nome"),
            resultSet.getObject("data_hora_inicio", LocalDateTime.class)
            );
        }
        
    @Override protected void mapAdd(PreparedStatement preparedStatement, Palestra entidade) throws SQLException {
        preparedStatement.setString(1, entidade.getNome());
        preparedStatement.setInt(2, entidade.getDuracao());
        preparedStatement.setObject(3, entidade.getDataHoraInicio());
    }
    
    @Override protected void mapUpdate(PreparedStatement preparedStatement, Palestra entidade) throws SQLException {
        preparedStatement.setString(1, entidade.getNome());
        preparedStatement.setInt(2, entidade.getDuracao());
        preparedStatement.setObject(3, entidade.getDataHoraInicio());

        preparedStatement.setInt(4, entidade.getId());
    }

    public void addPalestrante(Connection conexao, int idPalestra, int id) throws SQLException {
        try (PreparedStatement preparedStatement = conexao.prepareStatement("INSERT INTO palestra_palestrante (palestra_id, palestrante_id) VALUES (?, ?);")) {
            preparedStatement.setInt(1, id);
            preparedStatement.setInt(2, idPalestra);
        }
    }

}
