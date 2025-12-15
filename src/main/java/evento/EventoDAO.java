package evento;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import exceptions.RecursoNaoEncontradoException;
import persistencias.DAOCRUD;

public class EventoDAO extends DAOCRUD<Evento> {

    public EventoDAO(String tabela) {
        super(tabela);

        // Ajuste no SELECT para trazer a coluna 'detalhes' explicitamente se necessário, 
        // mas o '*' já resolve. Mantive a lógica do LEFT JOIN para saber se é privado.
        this.querySelectOne = 
        """
            SELECT e.*, 
                   CASE WHEN ep.id IS NOT NULL THEN true ELSE false END AS privado
            FROM evento e
            LEFT JOIN evento_privado ep ON e.id = ep.id
            WHERE e.id = ?
        """;
        
        this.querySelectAll = 
        """
            SELECT e.*, 
                   CASE WHEN ep.id IS NOT NULL THEN true ELSE false END AS privado
            FROM evento e
            LEFT JOIN evento_privado ep ON e.id = ep.id
            ORDER BY e.data_inicio DESC
        """;

        // --- MUDANÇA 1: Adicionado coluna detalhes com cast ::jsonb ---
        this.queryInsert = "INSERT INTO evento (nome, data_inicio, data_fim, localidade, organizador, detalhes) VALUES (?, ?, ?, ?, ?, ?::jsonb)";
        
        // --- MUDANÇA 2: Adicionado coluna detalhes no update ---
        this.queryUpdate = "UPDATE evento SET nome = ?, data_inicio = ?, data_fim = ?, localidade = ?, organizador = ?, detalhes = ?::jsonb WHERE id = ?";
    }

    @Override 
    protected void mapAdd(PreparedStatement ps, Evento entidade) throws SQLException {
        ps.setString(1, entidade.getNome());
        ps.setObject(2, entidade.getDataInicio());
        ps.setObject(3, entidade.getDataFim());
        ps.setString(4, entidade.getLocalidade());
        ps.setInt(5, entidade.getOrganizadorId());
        
        // Se não tiver detalhes, grava JSON vazio
        String json = (entidade.getDetalhes() == null || entidade.getDetalhes().isBlank()) ? "{}" : entidade.getDetalhes();
        ps.setString(6, json);
    }

    @Override 
    protected void mapUpdate(PreparedStatement ps, Evento entidade) throws SQLException {
        ps.setString(1, entidade.getNome());
        ps.setObject(2, entidade.getDataInicio());
        ps.setObject(3, entidade.getDataFim());
        ps.setString(4, entidade.getLocalidade());
        ps.setInt(5, entidade.getOrganizadorId());
        
        // Garante que não quebre o JSONB no update
        String json = (entidade.getDetalhes() == null || entidade.getDetalhes().isBlank()) ? "{}" : entidade.getDetalhes();
        ps.setString(6, json);
        
        ps.setInt(7, entidade.getId());
    }

    @Override 
    protected Evento mapEntidade(ResultSet resultSet) throws SQLException {
        boolean isPrivado = false; 
        try {
            isPrivado = resultSet.getBoolean("privado");
        } catch (SQLException e) {}

        Evento evento = new Evento(
            resultSet.getInt("id"),
            resultSet.getString("nome"),
            resultSet.getObject("data_inicio", LocalDate.class),
            resultSet.getObject("data_fim", LocalDate.class),
            resultSet.getString("localidade"),
            resultSet.getInt("organizador"),
            isPrivado
        );

        // --- MUDANÇA 3: Ler o JSON do banco ---
        try {
            String json = resultSet.getString("detalhes");
            evento.setDetalhes(json != null ? json : "{}");
        } catch (SQLException e) {
            // Caso a coluna não venha no select (ex: views antigas), define padrão
            evento.setDetalhes("{}");
        }

        return evento;
    }

    // --- MUDANÇA 4: Método Específico para o Upload Separado ---
    public void updateDetalhes(Connection conexao, int id, String json) throws SQLException {
        // Atualiza APENAS o JSONB, sem mexer no resto do evento
        String sql = "UPDATE evento SET detalhes = ?::jsonb WHERE id = ?";
        
        try (PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setString(1, json);
            ps.setInt(2, id);
            ps.executeUpdate();
        }
    }

    // --- Métodos de Select Específicos (Mantidos Iguais) ---

    public Evento selectEvento(Connection conexao, int id) throws SQLException {
        try (
            PreparedStatement preparedStatement = conexao.prepareStatement(
                """
                    SELECT e.*, 
                        CASE WHEN ep.id IS NOT NULL THEN true ELSE false END AS privado
                    FROM evento e 
                    LEFT JOIN evento_privado ep ON e.id = ep.id
                    INNER JOIN palestra p ON e.id = COALESCE(p.evento_id, p.evento_privado_id) 
                    WHERE p.id = ?
                """
            )
        ) {
            preparedStatement.setInt(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) return this.mapEntidade(resultSet);
                throw new RecursoNaoEncontradoException("Não foi encontrado evento vinculado ao evento de ID: " + id);
            }
        }
    }

    public List<Evento> selectEventos(Connection conexao, int id) throws SQLException {
        try (PreparedStatement preparedStatement = conexao.prepareStatement(
            """
                SELECT e.*, 
                    CASE WHEN ep.id IS NOT NULL THEN true ELSE false END AS privado
                FROM evento e 
                LEFT JOIN evento_privado ep ON e.id = ep.id
                INNER JOIN inscricao i ON e.id = COALESCE(i.evento_id, i.evento_privado_id) 
                WHERE i.usuario_id = ?      
            """
        )) {
            preparedStatement.setInt(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                List<Evento> lista = new ArrayList<>();
                while (resultSet.next()) lista.add(this.mapEntidade(resultSet));

                return lista;
            }
        }
    }

    public List<Evento> selectEventosOrganizados(Connection conexao, int id) throws SQLException {
        try (PreparedStatement preparedStatement = conexao.prepareStatement(
            """
                SELECT e.*, 
                   CASE WHEN ep.id IS NOT NULL THEN true ELSE false END AS privado
                FROM evento e
                LEFT JOIN evento_privado ep ON e.id = ep.id
                WHERE e.organizador = ? 
                ORDER BY e.data_inicio DESC
            """
        )) {
            preparedStatement.setInt(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                List<Evento> lista = new ArrayList<>();
                while (resultSet.next()) lista.add(this.mapEntidade(resultSet));

                return lista;
            }
        }
    }
}