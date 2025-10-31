package participante;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Arrays;

import persistencias.ADAO;

public class ParticipanteDAO extends ADAO <Participante> {

    public ParticipanteDAO() {
        super("participante", Arrays.asList("id", "cpf", "email"));
        this.queryInsert = "INSERT INTO participante (nome, email, cpf, data_nascimento) VALUES (?, ?, ?, ?);";
        this.queryUpdate = "UPDATE participante SET nome = ?, email = ?, data_nascimento = ? WHERE id = ?;";
    }

    @Override protected Participante mapEntidade(ResultSet resultSet) throws SQLException {
        return new Participante(
            resultSet.getInt("id"),
            resultSet.getString("nome"),
            resultSet.getString("email"),
            resultSet.getString("cpf"),
            resultSet.getObject("data_nascimento",LocalDate.class)
        );
    }

    @Override protected void mapAdd(PreparedStatement preparedStatement, Participante entidade) throws SQLException {
        preparedStatement.setString(1, entidade.getNome());
        preparedStatement.setString(2, entidade.getEmail());
        preparedStatement.setString(3, entidade.getCpf());
        preparedStatement.setObject(4, entidade.getDataNascimento());
    }

    @Override protected void mapUpdate(PreparedStatement preparedStatement, Participante entidade) throws SQLException {
        preparedStatement.setString(1, entidade.getNome());
        preparedStatement.setString(2, entidade.getEmail());
        preparedStatement.setObject(3, entidade.getDataNascimento());
        preparedStatement.setInt(4, entidade.getId());
    }
}
