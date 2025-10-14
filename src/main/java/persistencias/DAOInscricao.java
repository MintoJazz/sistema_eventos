package persistencias;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import negocio.Evento;
import negocio.Inscricao;
import negocio.Participante;

public class DAOInscricao extends ADAO<Inscricao>{
    private static final String add_query  = "INSERT INTO inscricao (participante_id, evento_id, data_hora, pago, valor) VALUES (?, ?, CURRENT_TIMESTAMP, false, 0.0);";

    public DAOInscricao() {
        this.nomeTabela = "dashboard_inscricoes";
    }

    @Override public void setQueryAdd(Inscricao t) throws SQLException { /* TODO document why this method is empty */ }

    public void setQueryAdd(Participante participante, Evento evento) throws SQLException {
        addNoKey(
            add_query,
            participante.getId(),
            evento.getId()
        );
    }

    @Override
    protected List<Inscricao> runGetQuery(PreparedStatement preparedStatement, List<Inscricao> lista) throws SQLException {
        try (ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                Evento evento = new Evento(
                    resultSet.getInt("evento_id"),
                    resultSet.getString("evento_nome"),
                    resultSet.getDate("data_inicio"),
                    resultSet.getDate("data_fim"),
                    resultSet.getString("localidade"),
                    resultSet.getString("status_evento")
                );

                Participante participante = new Participante(
                    resultSet.getInt("participante_id"),
                    resultSet.getString("participante_nome"),
                    resultSet.getString("cpf"),
                    resultSet.getString("email"),
                    resultSet.getDate("data_nascimento")
                );


                Inscricao inscricao = new Inscricao(
                    resultSet.getInt("inscricao_id"),
                    evento,
                    participante,
                    resultSet.getTimestamp("data_hora"),
                    resultSet.getDouble("valor"),
                    resultSet.getBoolean("pago")
                );
                lista.add(inscricao);
            }
        }
        return lista;
    }

    public boolean jaInscrito(int participanteId, int eventoId) throws SQLException {
        String sql = "SELECT 1 FROM inscricao WHERE participante_id = ? AND evento_id = ?";
        
        try (Connection conexao = new Conexao().getConexao();
            PreparedStatement preparedStatement = conexao.prepareStatement(sql)) {
            
            preparedStatement.setInt(1, participanteId);
            preparedStatement.setInt(2, eventoId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next();
            }
        }
    }
}
