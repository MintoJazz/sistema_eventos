package palestra;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import persistencias.DAOCRUD;

public class PalestraDAO extends DAOCRUD<Palestra> {

    public PalestraDAO(String tabela) {
        super(tabela);

        this.queryInsert = 
            """
                INSERT INTO palestra (nome, duracao, data_hora_inicio, evento_id, evento_privado_id)
                SELECT ?, ?, ?, 
                    CASE WHEN ep.id IS NULL THEN e.id ELSE NULL END,
                    ep.id -- Se for privado, preenche aqui
                FROM evento e
                LEFT JOIN evento_privado ep ON e.id = ep.id
                WHERE e.id = ?;
            """;

        this.queryUpdate = 
            """
                UPDATE palestra p
                SET nome = ?, 
                    duracao = ?, 
                    data_hora_inicio = ?,
                    evento_id = CASE WHEN ep.id IS NULL THEN e.id ELSE NULL END,
                    evento_privado_id = ep.id
                FROM evento e
                LEFT JOIN evento_privado ep ON e.id = ep.id
                WHERE e.id = ?
                AND p.id = ?
            """;
    }

    // --- SOBRESCREVENDO INSERT ---
    @Override public int insert(Connection conexao, Palestra entidade) throws SQLException {
        int idPalestra = super.insert(conexao, entidade);

        inserirVinculos(conexao, idPalestra, entidade.getPalestrantesIds());

        return idPalestra;
    }

    // --- SOBRESCREVENDO UPDATE ---
    @Override public int update(Connection conexao, Palestra entidade) throws SQLException {
        int linhas = super.update(conexao, entidade);

        try (PreparedStatement preparedStatement = conexao.prepareStatement("DELETE FROM palestra_palestrante WHERE palestra_id = ?")) {
            preparedStatement.setInt(1, entidade.getId());
            preparedStatement.executeUpdate();
        }

        inserirVinculos(conexao, entidade.getId(), entidade.getPalestrantesIds());

        return linhas;
    }

    private void inserirVinculos(Connection conexao, int idPalestra, List<Integer> idsUsuarios) throws SQLException {
        if (idsUsuarios == null || idsUsuarios.isEmpty()) return;

        try (PreparedStatement preparedStatement = conexao.prepareStatement("INSERT INTO perfil_palestrante (usuario_id, biografia) VALUES (?, 'Biografia pendente...') ON CONFLICT (usuario_id) DO NOTHING")) {
            for (Integer idUser : idsUsuarios) {
                preparedStatement.setInt(1, idUser);
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
        }
        
        try (PreparedStatement preparedStatement = conexao.prepareStatement("INSERT INTO palestra_palestrante (palestra_id, perfil_palestrante_id) VALUES (?, ?)")) {
            for (Integer idUser : idsUsuarios) {
                preparedStatement.setInt(1, idPalestra);
                preparedStatement.setInt(2, idUser);
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
        }
    }

    public List<Integer> selectIdsPalestrantes(Connection conexao, int idPalestra) throws SQLException {
        List<Integer> ids = new ArrayList<>();
        String sql = "SELECT perfil_palestrante_id FROM palestra_palestrante WHERE palestra_id = ?";
        
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, idPalestra);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    ids.add(rs.getInt("perfil_palestrante_id"));
                }
            }
        }
        return ids;
    }

    @Override protected void mapAdd(PreparedStatement preparedStatement, Palestra entidade) throws SQLException {
        preparedStatement.setString(1, entidade.getNome());
        preparedStatement.setInt(2, entidade.getDuracao());
        preparedStatement.setTimestamp(3, Timestamp.valueOf(entidade.getDataHoraInicio()));
        preparedStatement.setInt(4, entidade.getEventoId());
    }

    @Override protected Palestra mapEntidade(ResultSet resultSet) throws SQLException {
        return new Palestra(
            resultSet.getInt("id"),
            resultSet.getString("nome"),
            resultSet.getInt("duracao"),
            resultSet.getObject("data_hora_inicio",LocalDateTime.class),
            Integer.max(resultSet.getInt("evento_id"), resultSet.getInt("evento_privado_id"))
        );
    }

    @Override protected void mapUpdate(PreparedStatement preparedStatement, Palestra entidade) throws SQLException {
        preparedStatement.setString(1, entidade.getNome());
        preparedStatement.setInt(2, entidade.getDuracao());
        preparedStatement.setTimestamp(3, Timestamp.valueOf(entidade.getDataHoraInicio()));
        preparedStatement.setInt(4, entidade.getEventoId());
        preparedStatement.setInt(5, entidade.getId());
    }

    public List<Palestra> selectCronograma(Connection conexao, int id) throws SQLException {
        try (PreparedStatement preparedStatement = conexao.prepareStatement(
            """
            SELECT * FROM palestra 
            WHERE COALESCE(evento_id, evento_privado_id) = ? 
            ORDER BY data_hora_inicio ASC;
            """
        )) {
            preparedStatement.setInt(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                List<Palestra> lista = new ArrayList<>();
                while (resultSet.next()) lista.add(this.mapEntidade(resultSet));

                return lista;
            }
        }
    }

    public List<Palestra> selectAgenda(Connection conexao, int id) throws SQLException {
        try (PreparedStatement preparedStatement = conexao.prepareStatement(
            """
            SELECT p.* FROM palestra p
            INNER JOIN palestra_palestrante pp ON p.id = pp.palestra_id
            WHERE pp.perfil_palestrante_id = ? 
            ORDER BY p.data_hora_inicio ASC;        
            """
        )) {
            preparedStatement.setInt(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                List<Palestra> lista = new ArrayList<>();
                while (resultSet.next()) lista.add(this.mapEntidade(resultSet));

                return lista;
            }
        }
    }
    
}
