package conexao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public  class Conexao {
    static final String DB_URL = "jdbc:mysql://localhost:3306/todo_db";
    static final String USER = "root";
    static final String PASS = "teste";

    private static Connection conn = null;

    public static Connection getConexao(){
        try{
            if(conn==null){
                conn = DriverManager.getConnection(DB_URL, USER, PASS);
                return conn;
            }else{
                return conn;
            }
        }catch (SQLException se) {
            // Trata os erros de SQL
            se.printStackTrace();
            return null;
        } catch (Exception e) {
            // Trata outros erros
            e.printStackTrace();
            return null;
        }
    
    }
    public static void fechaConexao(){
        try {
            if (conn != null) conn.close();
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }
}