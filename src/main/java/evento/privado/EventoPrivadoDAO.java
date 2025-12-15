package evento.privado;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import persistencias.DAOCRUD;

public class EventoPrivadoDAO extends DAOCRUD<EventoPrivado> {

    public EventoPrivadoDAO() {
        super("evento_privado");
        
        this.queryInsert = "INSERT INTO evento_privado (nome, data_inicio, data_fim, localidade, organizador, senha_acesso, convidados) VALUES (?, ?, ?, ?, ?, ?, ?::jsonb)";
        this.queryUpdate = "UPDATE evento_privado SET nome = ?, data_inicio = ?, data_fim = ?, localidade = ?, organizador = ?, senha_acesso = ?, convidados = ?::jsonb WHERE id = ?";
        
    }

    @Override protected void mapAdd(PreparedStatement preparedStatement, EventoPrivado e) throws SQLException {
        preparedStatement.setString(1, e.getNome());
        preparedStatement.setObject(2, e.getDataInicio());
        preparedStatement.setObject(3, e.getDataFim());
        preparedStatement.setString(4, e.getLocalidade());
        preparedStatement.setInt(5, e.getOrganizadorId());

        preparedStatement.setString(6, e.getSenhaAcesso());
        preparedStatement.setString(7, e.getConvidados());
    }

    @Override protected void mapUpdate(PreparedStatement preparedStatement, EventoPrivado e) throws SQLException {
        preparedStatement.setString(1, e.getNome());
        preparedStatement.setObject(2, e.getDataInicio());
        preparedStatement.setObject(3, e.getDataFim());
        preparedStatement.setString(4, e.getLocalidade());
        preparedStatement.setInt(5, e.getOrganizadorId());
        preparedStatement.setString(6, e.getSenhaAcesso());
        preparedStatement.setString(7, e.getConvidados());
        preparedStatement.setInt(8, e.getId());
    }

    @Override protected EventoPrivado mapEntidade(ResultSet resultSet) throws SQLException {
        return new EventoPrivado(
            resultSet.getInt("id"),
            resultSet.getString("nome"),
            resultSet.getObject("data_inicio", LocalDate.class),
            resultSet.getObject("data_fim", LocalDate.class),
            resultSet.getString("localidade"),
            resultSet.getInt("organizador"),
            resultSet.getString("senha_acesso"),
            resultSet.getString("convidados")
        );
    }
}