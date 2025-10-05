package persistencias;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import negocio.Palestrante;

public class DAOPalestrante extends ADAO<Palestrante>{

    public DAOPalestrante(){
        this.nomeTabela = "palestrante";
        this.colunasUnicas = Arrays.asList("id", "cpf");
    }

    @Override public List<Palestrante> getByQuery(String query, Object... parametros) throws SQLException {
        ResultSet resultSet = this.getQuery(query, parametros);
        List<Palestrante> lista = new ArrayList<>();
        while (resultSet.next()) {
            lista.add(
                new Palestrante(
                    resultSet.getInt("id"),
                    resultSet.getString("nome"),
                    resultSet.getString("biografia"),
                    resultSet.getString("cpf")
                )
            );
        }
        this.fechar();
        return lista;
    }

}
