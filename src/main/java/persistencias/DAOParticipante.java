package persistencias;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import negocio.Participante;
import negocio.Evento;

public class DAOParticipante extends ADAO<Participante>{

    public DAOParticipante() {
        this.nomeTabela = "participante";
        this.colunasUnicas = Arrays.asList("id", "cpf");
    }

    @Override public List<Participante> getByQuery(String query, Object... parametros) throws SQLException {
        ResultSet resultSet = this.getQuery(query, parametros);
        List<Participante> lista = new ArrayList<>();
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            lista.add(
                new Participante(
                    id,
                    resultSet.getString("nome"),
                    resultSet.getString("cpf"),
                    resultSet.getString("email"),
                    resultSet.getDate("data_nascimento"),
                    this.getEventos(id)
                )
            );
        }
        this.fechar();
        return lista;
    }

    public List<Evento> getEventos(Participante participante) throws SQLException {
        return this.getEventos(participante.getId());
    }

    public List<Evento> getEventos(int id) throws SQLException {
        ResultSet resultSet = this.getQuery("SELECT e.* FROM inscricao i INNER JOIN evento e ON i.evento_id = e.id WHERE i.participante_id = ?", id);
        List<Evento> eventos = new ArrayList<>();
        while (resultSet.next()) {
            eventos.add(
                new Evento(
                    resultSet.getInt("id"),
                    resultSet.getString("nome"),
                    resultSet.getDate("data_inicio"),
                    resultSet.getDate("data_fim"),
                    resultSet.getString("localidade")
                )
            );
        } 
        this.fechar();
        return eventos;
    }
}
