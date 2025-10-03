import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;
import entity.*;
import DAO.*;

public class App {
    static final String DB_URL = "jdbc:mysql://localhost:3306/todo_list_db";
    static final String USER = "root";
    static final String PASS = "teste";
    public static void main(String[] args) throws Exception {
        Scanner ler = new Scanner(System.in);
        Users usuario = new Users();
        Users usuarioNovo = new Users();
        Task taskNovo = new Task();
        int op ;


        UsersDAO usersDAO = new UsersDAO();
        TaskDAO taskDAO = new TaskDAO(); 
        List<Users> teste = usersDAO.listaUsers();
        for (Users a : teste) {
            System.out.println("id: " + a.id + " username: " + a.username);
        }
        

    }




    public static void ConnectionDB() {
        // Parâmetros de conexão

        Connection conn = null;
        try {
            //Class.forName("com.mysql.jdbc.Driver").newInstance();
            // Estabelece a conexão
            System.out.println("Conectando ao banco de dados...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            // Se a conexão for bem-sucedida, exibe uma mensagem
            System.out.println("Conexão bem-sucedida!");

            // Faça o que precisar com a conexão aqui...

        } catch (SQLException se) {
            // Trata os erros de SQL
            se.printStackTrace();
        } catch (Exception e) {
            // Trata outros erros
            e.printStackTrace();
        } finally {
            // Fecha a conexão no final
            try {
                if (conn != null) conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }
}
