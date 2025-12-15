package inscricao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import persistencias.DAOCRUD;

public class InscricaoDAO extends DAOCRUD<Inscricao> {

    public InscricaoDAO() {
        super("inscricao");

        this.queryInsert = """
            INSERT INTO inscricao (usuario_id, evento_id, evento_privado_id, valor, pago)
            SELECT ?, 
                   CASE WHEN ep.id IS NULL THEN e.id ELSE NULL END, -- Se não for privado, é público
                   ep.id, -- Se for privado, preenche aqui
                   0.00,  -- Valor default
                   TRUE   -- Assume pago (ou FALSE se tiver pgto)
            FROM evento e
            LEFT JOIN evento_privado ep ON e.id = ep.id
            WHERE e.id = ?;
        """;
    }

    @Override protected void mapAdd(PreparedStatement preparedStatement, Inscricao entidade) throws SQLException {
        preparedStatement.setInt(1, entidade.getUsuarioId());
        preparedStatement.setInt(2, entidade.getEventoId());
    }

    @Override protected Inscricao mapEntidade(ResultSet resultSet) throws SQLException {
        int idEventoReal = resultSet.getInt("evento_id");
        if (resultSet.wasNull()) {
            idEventoReal = resultSet.getInt("evento_privado_id");
        }

        return new Inscricao(
            resultSet.getInt("id"),
            resultSet.getInt("usuario_id"),
            idEventoReal,
            resultSet.getObject("data_hora", java.time.LocalDateTime.class),
            resultSet.getDouble("valor"),
            resultSet.getBoolean("pago")
        );
    }

    @Override protected void mapUpdate(PreparedStatement stmt, Inscricao entidade) throws SQLException {}
}