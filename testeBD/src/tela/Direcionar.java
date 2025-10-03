package tela;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import entity.*;
import DAO.*;

public class Direcionar extends JFrame implements ActionListener {
    private JTextField campoUsuario;
    private JTextField campoSenha;
    private JButton botaoLogin;
    UsersDAO usersDAO = new UsersDAO();
    TaskDAO taskDAO = new TaskDAO(); 

    public Direcionar() {
        setTitle("Direcionar Task");
        setSize(300, 150);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel painel = new JPanel(new GridLayout(3, 2));

        JLabel labelUsuario = new JLabel("Id da task:");
        campoUsuario = new JTextField();
        JLabel labelSenha = new JLabel("Id do usuário:");
        campoSenha = new JTextField();;
        botaoLogin = new JButton("Direcionar");

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
        Task taskNovo = new Task();
        taskNovo.id = Integer.parseInt(campoUsuario.getText());
        taskNovo.user_id = Integer.parseInt(campoSenha.getText());
        
        taskDAO.direcionarTask(taskNovo.id , taskNovo.user_id);

    }

}    