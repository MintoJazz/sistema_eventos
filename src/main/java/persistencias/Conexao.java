package persistencias;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {
    private String host, port, database, password, url;
    
    public Conexao() {
        this.host = "localhost";
        this.port = "5432";
        this.password = "111";
        this.database = "sistema_eventos";

        this.url = "jdbc:postgresql://" + host + ":" + port + "/" + database;
    }

    public Connection getConexao(String role) throws SQLException {
        return DriverManager.getConnection(this.url, role, this.password);
    }
}