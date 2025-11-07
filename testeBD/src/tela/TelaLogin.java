package tela;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import entity.*;
import DAO.*;

public class TelaLogin extends JFrame implements ActionListener {
    private JTextField campoUsuario;
    private JPasswordField campoSenha;
    private JButton botaoLogin;
    UsersDAO usersDAO = new UsersDAO();

    public TelaLogin() {
        setTitle("Login");
        setSize(300, 150);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel painel = new JPanel(new GridLayout(3, 2));

        JLabel labelUsuario = new JLabel("Usuário:");
        campoUsuario = new JTextField();
        JLabel labelSenha = new JLabel("Senha:");
        campoSenha = new JPasswordField();
        botaoLogin = new JButton("Login");

        botaoLogin.addActionListener(this);
        
        painel.add(labelUsuario);
        painel.add(campoUsuario);
        painel.add(labelSenha);
        painel.add(campoSenha);
        painel.add(new JLabel()); // Espaço vazio
        painel.add(botaoLogin);

        add(painel);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Users usuario = new Users();
        usuario.username = campoUsuario.getText();
        usuario.password = new String(campoSenha.getPassword());
        
        
        // Aqui você pode fazer a verificação do usuário e senha
        if (usersDAO.validarUsuario(usuario) == 1) {
            JOptionPane.showMessageDialog(this, "Login bem sucedido!");
            dispose(); // Fecha a tela principal
            new Menu(); // Abre a tela secundária
        } else {
            JOptionPane.showMessageDialog(this, "Usuário ou senha inválidos");
        }
    }

    public static void main(String[] args) {
        new TelaLogin();
    }
}
