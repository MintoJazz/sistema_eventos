package persistencias;

import java.sql.SQLException;
import java.util.List;

public interface IDAO<T> {
    public abstract List<T> getAll() throws SQLException;
    public abstract T getById(int id) throws SQLException;
    public abstract List<T> getByQuery(String query, Object... parametros) throws SQLException;
}
