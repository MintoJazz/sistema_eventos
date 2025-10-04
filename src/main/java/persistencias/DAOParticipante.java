package persistencias;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import negocio.Participante;

public class DAOParticipante extends ADAO<Participante>{

    @Override public List<Participante> getAll() throws SQLException {
        ResultSet resultSet = this.getQuery("SELECT * FROM participante", null);
        List<Participante> lista = new ArrayList<>();
        while (resultSet.next()) {
            lista.add(
                new Participante(
                    resultSet.getInt("id"),
                    resultSet.getString("nome"),
                    resultSet.getString("cpf"),
                    resultSet.getString("email"),
                    resultSet.getDate("data_nascimento")
                )
            );
        }
        this.fechar();
        return lista;
    }

    @Override public Participante get4Id(int id) throws SQLException{
        ResultSet resultSet = this.getQuery("SELECT * FROM participante WHERE id = ?", id);
        while (resultSet.next()) {
            return new Participante(
                resultSet.getInt("id"), 
                resultSet.getString("nome"), 
                resultSet.getString("cpf"), 
                resultSet.getString("email"), 
                resultSet.getDate("data_nascimento")
            );
        } 
        this.fechar();
        return null;
    }
    
}
