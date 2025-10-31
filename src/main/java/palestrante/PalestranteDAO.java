package palestrante;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import persistencias.ADAO;

public class PalestranteDAO extends ADAO<Palestrante>{

    public PalestranteDAO() {
        super("palestrante", Arrays.asList("id", "cpf"));

        this.queryInsert = "INSERT INTO palestrante (nome, biografia, cpf) VALUES (?, ?, ?);";
        this.queryUpdate = "UPDATE palestrante SET nome = ?, biografia = ?, cpf = ? WHERE  id = ?;";
    }

    // public List<PalestranteDashboardDTO> dashboard(Connection conexao) throws SQLException {
    //     List<PalestranteDashboardDTO> lista = new ArrayList<>();

    //     try (
	// 		PreparedStatement preparedStatement = conexao.prepareStatement("SELECT * FROM dashboard_palestrantes;");
	// 		ResultSet resultSet = preparedStatement.executeQuery();
	// 	) {
	// 		while(resultSet.next()) lista.add(
    //             new PalestranteDashboardDTO(
    //                 resultSet.getString("nome"), 
    //                 resultSet.getString("cpf"), 
    //                 resultSet.getString("biografia"), 
    //                 resultSet.getInt("qtd_palestras"), 
    //                 resultSet.getInt("id")
    //             )   
    //         );
    //         return lista;
	// 	}
    // }

    @Override protected Palestrante mapEntidade(ResultSet resultSet) throws SQLException {
        return new Palestrante(
            resultSet.getInt("id"),
            resultSet.getString("nome"),
            resultSet.getString("biografia"),
            resultSet.getString("cpf")
        );
    }

    @Override protected void mapAdd(PreparedStatement preparedStatement, Palestrante entidade) throws SQLException {
        preparedStatement.setString(1, entidade.getNome());
        preparedStatement.setString(2, entidade.getBiografia());
        preparedStatement.setString(3, entidade.getCpf());
    }

    @Override protected void mapUpdate(PreparedStatement preparedStatement, Palestrante entidade) throws SQLException {
        preparedStatement.setString(1, entidade.getNome());
        preparedStatement.setString(2, entidade.getBiografia());
        preparedStatement.setString(3, entidade.getCpf());
        preparedStatement.setInt(4, entidade.getId());
    }
    
}
