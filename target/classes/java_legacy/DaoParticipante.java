package persistencias;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import negocio.Evento;
import negocio.Participante;

public class DaoParticipante extends DaoAbstracao<Participante>{
    
    public List<Participante> getAll() throws SQLException {
        List<Participante> lista = new ArrayList<>();
        PSQL banco = new PSQL();
        ResultSet resultSet = banco.getQuery("SELECT * FROM participante", null);
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
        } banco.fechar();
        return lista;
        
    }

    @Override public Participante get4Id(int id) throws SQLException {
        PSQL banco = new PSQL();
        ResultSet resultSet = banco.getQuery("SELECT * FROM participante WHERE id = ?", id);
        while (resultSet.next()) {
            return new Participante(
                resultSet.getInt("id"), 
                resultSet.getString("nome"), 
                resultSet.getString("cpf"), 
                resultSet.getString("email"), 
                resultSet.getDate("data_nascimento")
            );
        } banco.fechar();
        return null;
    }

    public List<Evento> getEventos(int id) throws SQLException {
        List<Evento> lista = new ArrayList<>();
        PSQL banco = new PSQL();
        ResultSet resultSet = banco.getQuery("SELECT e.* FROM inscricao i INNER JOIN evento e ON i.evento_id = e.id WHERE i.participante_id = ?", id);
        while (resultSet.next()) {
            lista.add(
                new Evento(
                    resultSet.getInt("id"),
                    resultSet.getString("nome"),
                    resultSet.getDate("data_inicio"),
                    resultSet.getDate("data_fim"),
                    resultSet.getString("localidade")
                )
            );
        } banco.fechar();
        return lista;
    }

    public List<Evento> getEventos(Participante participante) throws SQLException {
        return this.getEventos(participante.getId());
    }

    public List<Participante> get4Query(String query, Object... parametros) throws SQLException {
        List<Participante> lista = new ArrayList<>();
        PSQL banco = new PSQL();
        ResultSet resultSet = banco.getQuery(query,parametros);
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
        } banco.fechar();
        return lista;
    }
}
