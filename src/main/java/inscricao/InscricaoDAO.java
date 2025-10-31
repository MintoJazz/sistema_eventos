package inscricao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

import persistencias.ADAO;

public class InscricaoDAO extends ADAO<Inscricao> {

    public InscricaoDAO() {
        super("inscricao", Arrays.asList("id"));

        this.queryInsert = "INSERT INTO inscricao (participante_id, evento_id) VALUES (?, ?);";
    }

    @Override public int atualizar(Connection conexao, Inscricao entidade) throws SQLException {
        throw new UnsupportedOperationException("Metodo não disponível para tabela 'atualizar'");
    }
    
    @Override protected void mapUpdate(PreparedStatement preparedStatement, Inscricao entidade) throws SQLException {
        throw new UnsupportedOperationException("Metodo não disponível para tabela 'mapUpdate'");
    }
    
    @Override protected Inscricao mapEntidade(ResultSet resultSet) throws SQLException {
        return new Inscricao(
            resultSet.getInt("id"),
            resultSet.getInt("evento_id"),
            resultSet.getInt("participante_id")
        );
    }

    @Override protected void mapAdd(PreparedStatement preparedStatement, Inscricao entidade) throws SQLException {
        preparedStatement.setInt(1, entidade.getParticipanteId());
        preparedStatement.setInt(2, entidade.getEventoId());
    }
}
