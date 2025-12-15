package usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import exceptions.RecursoNaoEncontradoException;
import persistencias.DAOCRUD;

public class UsuarioDAO extends DAOCRUD<Usuario>{

    public UsuarioDAO(String tabela) {
        super(tabela);
        this.queryInsert = "INSERT INTO usuario(nome, cpf, email, senha) VALUES (?,?,?,?)";
        this.queryUpdate = "UPDATE usuario SET nome = ?, cpf = ?, email = ?, senha = ? WHERE id = ?";
    }

    @Override protected Usuario mapEntidade(ResultSet resultSet) throws SQLException {
        return new Usuario(
            resultSet.getInt("id"),
            resultSet.getString("nome"),
            resultSet.getString("cpf"),
            resultSet.getString("email"),
            resultSet.getString("senha")
        );
    }

    @Override protected void mapAdd(PreparedStatement preparedStatement, Usuario entidade) throws SQLException {
        preparedStatement.setString(1, entidade.getNome());
        preparedStatement.setString(2, entidade.getCpf());
        preparedStatement.setString(3, entidade.getEmail());
        preparedStatement.setString(4, entidade.getSenha());
    }

    @Override protected void mapUpdate(PreparedStatement preparedStatement, Usuario entidade) throws SQLException {
        preparedStatement.setString(1, entidade.getNome());
        preparedStatement.setString(2, entidade.getCpf());
        preparedStatement.setString(3, entidade.getEmail());
        preparedStatement.setString(4, entidade.getSenha());
        preparedStatement.setInt(5, entidade.getId());
    }

    public Usuario selectOrganizador(Connection conexao, int id) throws SQLException {
        try (PreparedStatement preparedStatement = conexao.prepareStatement("SELECT u.* FROM usuario u INNER JOIN evento e ON u.id = e.organizador WHERE e.id = ?;")) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) return this.mapEntidade(resultSet);
                throw new RecursoNaoEncontradoException("Não foi encontrado organizador para o evento com ID: " + id);
            }
        }
    }

    public Usuario autenticar(Connection conexao, String email, String senha) throws SQLException {
        String sql = "SELECT * FROM usuario WHERE email = ? AND senha = ?";
        
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, email);
            stmt.setString(2, senha); // Em produção, usaria hash (BCrypt), mas para TCC texto puro serve
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return this.mapEntidade(rs); // Retorna o usuário se achou
                }
            }
        }
        return null; // Não achou
    }
    
}
