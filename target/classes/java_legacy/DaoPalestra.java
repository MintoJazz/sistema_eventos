package persistencias;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import negocio.Palestra;

public class DaoPalestra extends DaoAbstracao<Palestra>{

    @Override
    public List<Palestra> getAll() throws SQLException {
        List<Palestra> lista = new ArrayList<>();
        PSQL banco = new PSQL();
        ResultSet resultSet = banco.getQuery("SELECT * FROM palestra", null);
        
    }

    @Override
    public Palestra get4Id(int id) throws SQLException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'get4Id'");
    }

}
