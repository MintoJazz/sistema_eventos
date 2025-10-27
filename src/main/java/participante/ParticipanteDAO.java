package participante;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

import persistencias.ADAO;

public class ParticipanteDAO extends ADAO <Participante> {

    public ParticipanteDAO() {
        super("participante", Arrays.asList("id", "cpf", "email"));
        //TODO Auto-generated constructor stub
    }

    @Override protected void mapAddQuery(PreparedStatement preparedStatement, Participante entidade) throws SQLException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'mapAddQuery'");
    }

    @Override
    protected Participante mapEntidade(ResultSet resultSet) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'mapEntidade'");
    }

}
