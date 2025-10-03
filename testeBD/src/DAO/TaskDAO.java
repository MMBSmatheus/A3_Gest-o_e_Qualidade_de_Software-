package DAO;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import conexao.Conexao;
import entity.*;
public class TaskDAO {
    public int criaTask(Task task){
        int retorno = 0;
        String sql  = "INSERT INTO todo_list_db.tasks(description,status)VALUES('" + task.description + "','Aberta')";
        PreparedStatement ps = null;
        try{
            // Insere os dados
            ps = Conexao.getConexao().prepareStatement(sql);
            ps.execute();
            ps.close();

            
            // Retornar os dados do cliente cadastrado
            String sql2  = "SELECT id FROM todo_list_db.tasks WHERE description = '" + task.description + "'";
            ps = Conexao.getConexao().prepareStatement(sql2);
            ResultSet result2 = ps.executeQuery();
            while (result2.next()) {
                retorno = Integer.parseInt(result2.getString("id"));
                System.out.println("Id da task:" + retorno);
                return retorno;
            }
                    
                
                ps.close();
                
            } catch (SQLException se) {
                // Trata os erros de SQL
                se.printStackTrace();
            } catch (Exception e) {
                // Trata outros erros
                e.printStackTrace();
            }finally{
                Conexao.fechaConexao();
            }
            return retorno;                      
    }

    public void direcionarTask(int task,int user){
        String sql  = "update todo_list_db.tasks set user_id = " + user + ", status = 'Encaminhada' where id =" + task;
        PreparedStatement ps = null;
        try{
            // Insere os dados
            ps = Conexao.getConexao().prepareStatement(sql);
            ps.execute();
            ps.close();

            System.out.println("Task direcionada");  
            } catch (SQLException se) {
                // Trata os erros de SQL
                se.printStackTrace();
            } catch (Exception e) {
                // Trata outros erros
                e.printStackTrace();
            }finally{
                Conexao.fechaConexao();
            }
    }

    public void deletarTask(int task){
        String sql  = "delete from todo_list_db.tasks where id = " + task;
        PreparedStatement ps = null;
        try{
            // Insere os dados
            ps = Conexao.getConexao().prepareStatement(sql);
            ps.execute();
            ps.close();

            System.out.println("Task deletada");  
            } catch (SQLException se) {
                // Trata os erros de SQL
                se.printStackTrace();
            } catch (Exception e) {
                // Trata outros erros
                e.printStackTrace();
            }finally{
                Conexao.fechaConexao();
            }
    }

    public void editarTask(int task,String description){
        String sql  = "update todo_list_db.tasks set description ='" + description +  "' where id = " + task;
        PreparedStatement ps = null;
        try{
            // Insere os dados
            ps = Conexao.getConexao().prepareStatement(sql);
            ps.execute();
            ps.close();

            System.out.println("Task editada");  
            } catch (SQLException se) {
                // Trata os erros de SQL
                se.printStackTrace();
            } catch (Exception e) {
                // Trata outros erros
                e.printStackTrace();
            }finally{
                Conexao.fechaConexao();
            }
    }

}
