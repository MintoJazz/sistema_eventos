package persistencias;

import java.sql.SQLException;
import java.util.List;

public interface DaoInterface<T> {
    public List<T> getAll() throws SQLException;
    public T get4Id(int id) throws SQLException;
}
