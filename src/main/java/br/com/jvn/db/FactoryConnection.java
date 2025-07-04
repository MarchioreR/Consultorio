package br.com.jvn.db;

import java.sql.Connection;
import java.sql.SQLException;

public class FactoryConnection {
    public static Connection createConnection() throws SQLException{
        return (Connection) MySqlConnectionSingleton.getInstance().getConn();
    }
}
