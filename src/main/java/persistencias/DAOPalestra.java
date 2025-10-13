package persistencias;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import negocio.Palestra;
import negocio.Palestrante;

public class DAOPalestra extends ADAO<Palestra>{
    private static final String queryInsertPalestra = "INSERT INTO palestra (nome, data_hora_inicio, duracao) VALUES (?, ?, ?);";
    private static final String queryInsertPalestrante = "INSERT INTO palestra_palestrante (palestra_id, palestrante_id) VALUES (?, ?);";

    public DAOPalestra() {
        this.nomeTabela = "palestra";
        this.colunasUnicas = Arrays.asList("id");
    }

    @Override public void setQueryAdd(Palestra t) throws SQLException {
        int idNovo = addWithKey(queryInsertPalestra, t.getNome(), t.getInicioTimestamp(), t.getDuracao(), t.getEvento().getId());
        for (Palestrante palestrante : t.getPalestrantes()) addNoKey(queryInsertPalestrante, idNovo, palestrante.getId());
    }

    @Override protected List<Palestra> runGetQuery(PreparedStatement preparedStatement, List<Palestra> lista) throws SQLException {
        try (ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                lista.add(
                    new Palestra(
                        resultSet.getInt("id"),
                        resultSet.getString("nome"),
                        resultSet.getTimestamp("data_hora_inicio"),
                        resultSet.getInt("duracao")
                    )
                );
            }
        } 
        
        return lista;
    }
}
