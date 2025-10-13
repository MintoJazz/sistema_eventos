package persistencias;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import negocio.Participante;
import negocio.Evento;

public class DAOParticipante extends ADAO<Participante>{

    public DAOParticipante() {
        this.nomeTabela = "participante";
        this.colunasUnicas = Arrays.asList("id", "cpf");
    }

    public List<Evento> getEventos(Participante participante) throws SQLException {
        return this.getEventos(participante.getId());
    }

    public List<Evento> getEventos(int id) throws SQLException {
        List<Evento> eventos = new ArrayList<>();

        try (
            Connection conexao = new Conexao().getConexao();
            PreparedStatement preparedStatement = conexao.prepareStatement(
                "SELECT e.* FROM inscricao i INNER JOIN evento e ON i.evento_id = e.id WHERE i.participante_id = ?"
            );
        ) {
            preparedStatement.setObject(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    eventos.add(
                        new Evento(
                            resultSet.getInt("id"),
                            resultSet.getString("nome"),
                            resultSet.getDate("data_inicio"),
                            resultSet.getDate("data_fim"),
                            resultSet.getString("localidade")
                        )
                    );
                }
            }
        }

        return eventos;
    }

    @Override public void setQueryAdd(Participante t) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setQueryAdd'");
    }

    @Override  protected List<Participante> runGetQuery(PreparedStatement preparedStatement, List<Participante> lista) throws SQLException {
        try (ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                lista.add(
                    new Participante(
                        id,
                        resultSet.getString("nome"),
                        resultSet.getString("cpf"),
                        resultSet.getString("email"),
                        resultSet.getDate("data_nascimento")
                    )
                );
            }
        }
        
        return lista;
    }
}
