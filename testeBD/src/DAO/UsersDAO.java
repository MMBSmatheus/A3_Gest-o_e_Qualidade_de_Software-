package DAO;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import conexao.Conexao;
import entity.Users;

public class UsersDAO {
    public int validarUsuario(Users user){
        int retorno = 0;
        String sql  = "SELECT password FROM todo_list_db.users WHERE username = '" + user.username + "'";

        PreparedStatement ps = null;
        
        
        try{
            ps = Conexao.getConexao().prepareStatement(sql);
            
            ResultSet result = ps.executeQuery();

                while (result.next()) {
                    
                    String teste = result.getString("password");
                    System.out.println("senha: '" + user.password + "'");
                    System.out.println("banco: '" + teste + "'");
                    if (user.password.equals(teste.toString().intern())) {
                        System.out.println("entrei");
                        retorno = 1;
                    }
                }
                ps.close();
      
        } catch (SQLException se) {
            // Trata os erros de SQL
            se.printStackTrace();
        } catch (Exception e) {
            // Trata outros erros
            e.printStackTrace();
        }finally{
            
        }
        return retorno;
    }


    public Users criarUsuario(Users user){
        String sql  = "SELECT * FROM todo_list_db.users WHERE username = '" + user.username + "'";
        PreparedStatement ps = null;
        Users retorno = new Users();
        try{
            ps = Conexao.getConexao().prepareStatement(sql);
            
            ResultSet result = ps.executeQuery();
            
            if(!result.isBeforeFirst()){
                String sql2  = "insert into todo_list_db.users(username,password) values ('" + user.username + "','" + user.password + "')";
                
                ps = Conexao.getConexao().prepareStatement(sql2);
                ps.execute();
                ps.close();
                // Retornar os dados do cliente cadastrado
                String sql3  = "SELECT * FROM todo_list_db.users WHERE username = '" + user.username + "'";
                ps = Conexao.getConexao().prepareStatement(sql3);
                ResultSet result2 = ps.executeQuery();
                while (result2.next()) {
                    user.id = Integer.parseInt(result2.getString("id"));
                    user.username = result2.getString("username");
                    user.password = result2.getString("password");
                    System.out.println("usuario: " + user.username + " , id:" + user.id);
                    retorno = user;
                    return retorno;
                    
                }
            }
            else{
                System.out.println("usuario já cadastrado");
                while (result.next()) {
                    user.id = Integer.parseInt(result.getString("id"));
                    user.username = result.getString("username");
                    user.password = result.getString("password");
                    System.out.println("usuario: " + user.username + " , id:" + user.id);
                    retorno = user;
                    return retorno;
                    
                }
                
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


    

}
