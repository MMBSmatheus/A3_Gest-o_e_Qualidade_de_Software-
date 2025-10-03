import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
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
        System.out.println("usuário:");
        usuario.username = ler.next();
        System.out.println("senha:");
        usuario.password = ler.next();

        UsersDAO usersDAO = new UsersDAO();
        TaskDAO taskDAO = new TaskDAO(); 

        if (usersDAO.validarUsuario(usuario) == 1) {
            System.out.println("Opções");
            System.out.println("1 - Cadastrar usuário");
            System.out.println("2 - Criar nova tarefa");
            System.out.println("3 - Direcionar uma tarefa");
            System.out.println("4 - Deletar uma tarefa");
            System.out.println("5 - Editar uma tarefa");
            System.out.println("Escolha:");
            op = ler.nextInt();
            if (op == 1) {
                System.out.println("usuário novo:");
                usuarioNovo.username = ler.next();
                System.out.println("senha:");
                usuarioNovo.password = ler.next();
                usersDAO.criarUsuario(usuarioNovo);
            }
            if (op == 2) {
                System.out.println("Descrição da história:");
                taskNovo.description = ler.next();
                taskDAO.criaTask(taskNovo);
            }
            if (op == 3) {
                System.out.println("Id da task:");
                taskNovo.id = ler.nextInt();
                System.out.println("Id do usuario:");
                usuarioNovo.id = ler.nextInt();
                taskDAO.direcionarTask(taskNovo.id , usuarioNovo.id);
            }
            if (op == 4) {
                System.out.println("Id da task:");
                taskNovo.id = ler.nextInt();
                taskDAO.deletarTask(taskNovo.id);
            }
            if (op == 5) {
                System.out.println("Id da task:");
                taskNovo.id = ler.nextInt();
                System.out.println("Descrição:");
                taskNovo.description = ler.next();
                taskDAO.editarTask(taskNovo.id , taskNovo.description);
            }
            
        }
        else{
            System.out.println("usuário não cadastrado");
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
