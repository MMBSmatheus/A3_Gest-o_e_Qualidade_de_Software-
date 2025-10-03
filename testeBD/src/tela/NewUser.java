package tela;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import entity.*;
import DAO.*;

public class NewUser extends JFrame implements ActionListener {
    private JTextField campoUsuario;
    private JPasswordField campoSenha;
    private JButton botaoLogin;
    UsersDAO usersDAO = new UsersDAO();

    public NewUser() {
        setTitle("Cadastro de Usuário");
        setSize(300, 150);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel painel = new JPanel(new GridLayout(3, 2));

        JLabel labelUsuario = new JLabel("Usuário:");
        campoUsuario = new JTextField();
        JLabel labelSenha = new JLabel("Senha:");
        campoSenha = new JPasswordField();
        botaoLogin = new JButton("Cadastrar");

        botaoLogin.addActionListener(this);

        // Criando e adicionando o botão "Voltar"
        JButton btnVoltar = new JButton("Voltar");
        btnVoltar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Fecha a janela quando o botão "Voltar" é clicado
                dispose();
                new Menu();
            }
        });
        getContentPane().add(btnVoltar, BorderLayout.SOUTH);
        
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
        
        usersDAO.criarUsuario(usuario);

    }

}    