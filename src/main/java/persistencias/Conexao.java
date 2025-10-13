package persistencias;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {
    private String host, port, username, database, password, url;
    
    public Conexao() {
        this.host = "localhost";
        this.port = "5432";
        this.username = "postgres";
        this.password = "postgres";
        this.database = "sistema_eventos";

        this.url = "jdbc:postgresql://" + host + ":" + port + "/" + database;
    }

    public Connection getConexao() throws SQLException {
        return DriverManager.getConnection(this.url, this.username, this.password);
    }
}