package tela;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import entity.*;
import DAO.*;

public class EditTask extends JFrame implements ActionListener {
    private JTextField campoUsuario;
    private JTextField campoSenha;
    private JTextField campoStatus;
    private JButton botaoLogin;
    UsersDAO usersDAO = new UsersDAO();
    TaskDAO taskDAO = new TaskDAO(); 

    public EditTask() {
        setTitle("Direcionar Task");
        setSize(400, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel painel = new JPanel(new GridLayout(4, 2));

        JLabel labelUsuario = new JLabel("Id da task:");
        campoUsuario = new JTextField();
        JLabel labelSenha = new JLabel("Descrição:");
        campoSenha = new JTextField();
        JLabel labelStatus = new JLabel("Status:");
        campoStatus = new JTextField();;
        botaoLogin = new JButton("Editar");

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
        painel.add(labelStatus);
        painel.add(campoStatus);
        painel.add(new JLabel()); // Espaço vazio
        painel.add(botaoLogin);

        add(painel);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Task taskNovo = new Task();
        taskNovo.id = Integer.parseInt(campoUsuario.getText());
        taskNovo.description = campoSenha.getText();
        taskNovo.status = campoStatus.getText();
        
        taskDAO.editarTask(taskNovo.id , taskNovo.description, taskNovo.status);

    }

}    