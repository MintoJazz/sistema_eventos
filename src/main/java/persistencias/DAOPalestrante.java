package persistencias;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import negocio.Palestrante;

public class DAOPalestrante extends ADAO<Palestrante>{

    public DAOPalestrante(){
        this.nomeTabela = "dashboard_palestrantes";
        this.colunasUnicas = Arrays.asList("id", "cpf");
    }

    @Override public void setQueryAdd(Palestrante t) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setQueryAdd'");
    }

    @Override protected List<Palestrante> runGetQuery(PreparedStatement preparedStatement, List<Palestrante> lista) throws SQLException {
        try (ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                lista.add(
                    new Palestrante(
                        resultSet.getInt("id"),
                        resultSet.getString("nome"),
                        resultSet.getString("biografia"),
                        resultSet.getString("cpf"),
                        resultSet.getInt("nro_palestras")
                    )
                );
            }
        }
        return lista;
    }
}
