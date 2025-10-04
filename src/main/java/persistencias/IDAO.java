package persistencias;

import java.sql.SQLException;
import java.util.List;

public interface IDAO<T> {
    public abstract List<T> getAll() throws SQLException;
    public abstract T get4Id(int id) throws SQLException;
}
