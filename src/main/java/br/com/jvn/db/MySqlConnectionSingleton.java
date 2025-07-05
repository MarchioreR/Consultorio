package br.com.jvn.db;

// MEXER 

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySqlConnectionSingleton {
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String URL = "jdbc:mysql://127.0.0.1";
    private static final String PORT = "3306";
    private static final String DATABASE = "Consultorio";
    private static final String TIMEZONE = "useTimezone=true&serverTimezone=UTC";

    private Connection conn;
    private static MySqlConnectionSingleton instance = null;

    private MySqlConnectionSingleton(String USER, String PASS){
        String connect = URL + ":" + PORT + "/" + DATABASE + "?" + TIMEZONE;

        try{
            Class.forName(DRIVER);
            System.out.println("Criou DRIVER!");
            conn = DriverManager.getConnection(connect,USER,PASS);
            System.out.println("Criou conn");
        }catch (ClassNotFoundException | SQLException ex) {
            System.out.println("Erro ao conectar ao banco de dados!");
            System.out.println(ex.getMessage());
        }
    }

    public Connection getConn() {
        return conn;
    }
    
    public static MySqlConnectionSingleton getInstance(String USER, String PASS) throws SQLException{
        if(instance == null){
            instance = new MySqlConnectionSingleton(USER,PASS);
            System.out.println("Criou instance 1");
        }
       
        if (instance.getConn().isClosed()){
            instance = new MySqlConnectionSingleton(USER,PASS);
            System.out.println("Criou instance 2");
        }
            
        return instance;
    }
}