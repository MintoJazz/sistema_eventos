package palestrante;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import persistencias.DAOCRUD;

public class PalestranteDAO extends DAOCRUD<Palestrante> {

    public PalestranteDAO(String tabela) {
        super(tabela);
    
        this.queryInsert = "INSERT INTO perfil_palestrante (usuario_id, biografia) VALUES (?, ?) RETURNING usuario_id";
        this.queryUpdate = "UPDATE perfil_palestrante SET biografia = ? WHERE usuario_id = ?";
        this.querySelectAll = "SELECT u.*, p.biografia FROM perfil_palestrante p JOIN usuario u ON u.id = p.usuario_id";
        this.querySelectOne = "SELECT u.*, p.biografia FROM perfil_palestrante p JOIN usuario u ON u.id = p.usuario_id WHERE u.id = ?";
    }

    @Override protected void mapAdd(PreparedStatement stmt, Palestrante entidade) throws SQLException {
        stmt.setInt(1, entidade.getId());
        stmt.setString(2, entidade.getBiografia());
    }

    @Override protected void mapUpdate(PreparedStatement stmt, Palestrante entidade) throws SQLException {
        stmt.setString(1, entidade.getBiografia());
        stmt.setInt(2, entidade.getId());
    }

    @Override protected Palestrante mapEntidade(ResultSet rs) throws SQLException {
        return new Palestrante(
            rs.getInt("id"),
            rs.getString("nome"),
            rs.getString("cpf"),
            rs.getString("email"),
            rs.getString("senha"),
            rs.getString("biografia")
        );
    }

    public List<Palestrante> selectPalestrantes(Connection conexao, int id) throws SQLException {
        try (PreparedStatement preparedStatement = conexao.prepareStatement(
            """
            SELECT u.*, p.biografia 
            FROM usuario u
            INNER JOIN perfil_palestrante p ON u.id = p.usuario_id
            INNER JOIN palestra_palestrante pp ON p.usuario_id = pp.perfil_palestrante_id
            WHERE pp.palestra_id = ?
            """
        )) {
            preparedStatement.setInt(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                List<Palestrante> lista = new ArrayList<>();
                while (resultSet.next()) lista.add(this.mapEntidade(resultSet));

                return lista;
            }
        }
    }   
}
