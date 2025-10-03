package DAO;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

    public void editarTask(int task,String description, String status){
        String sql  = "update todo_list_db.tasks set description ='" + description +  "', status = '" + status + "' where id = " + task;
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
            }
    }

    public List<Task> listaTask(){
        String sql  = "SELECT * FROM todo_list_db.tasks";
        List<Task> listaUsers = new ArrayList<>();
        PreparedStatement ps = null;
        
        
        
        try{
            ps = Conexao.getConexao().prepareStatement(sql);
            // Salvando resultado da consulta o resultado da query
            ResultSet result = ps.executeQuery();
                // Montando lista para retornar
                while (result.next()) {
                    Task temp = new Task();
                    temp.id = Integer.parseInt(result.getString("id"));
                    if (result.getString("user_id") != null) {
                        temp.user_id = Integer.parseInt(result.getString("user_id"));
                    }
                    
                    temp.description = result.getString("description");
                    temp.status = result.getString("status");
                    listaUsers.add(temp);
                }
                ps.close();
            return listaUsers;
        } catch (SQLException se) {
            // Trata os erros de SQL
            se.printStackTrace();
        } catch (Exception e) {
            // Trata outros erros
            e.printStackTrace();
        }finally{
            
        }
        return listaUsers;
    }
}
