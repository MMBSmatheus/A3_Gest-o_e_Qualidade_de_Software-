package tela;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import entity.*;
import DAO.*;

public class Menu extends JFrame implements ActionListener {
    private JButton botao1, botao2, botao3, botao4, botao5, botao6, botao7;
    UsersDAO usersDAO = new UsersDAO();
    TaskDAO taskDAO = new TaskDAO(); 
    public Menu() {
        setTitle("Tela com Botões");
        setSize(800, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel painel = new JPanel(new GridLayout(2, 3, 10, 10));

        botao1 = new JButton("criar novo usuário");
        botao2 = new JButton("criar nova tarefa");
        botao3 = new JButton("delegar uma tarefa");
        botao4 = new JButton("deletar uma tarefa");
        botao5 = new JButton("editar uma tarefa");
        botao6 = new JButton("Lista de Usuário");
        botao7 = new JButton("Lista de Tarefas");

        botao1.addActionListener(this);
        botao2.addActionListener(this);
        botao3.addActionListener(this);
        botao4.addActionListener(this);
        botao5.addActionListener(this);
        botao6.addActionListener(this);
        botao7.addActionListener(this);

        painel.add(botao1);
        painel.add(botao2);
        painel.add(botao3);
        painel.add(botao4);
        painel.add(botao5);
        painel.add(botao6);
        painel.add(botao7);

        add(painel);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == botao1) {
            dispose();
            new NewUser();
        } else if (e.getSource() == botao2) {
            dispose();
            new NewTask();
        } else if (e.getSource() == botao3) {
            dispose();
            new Direcionar();
        } else if (e.getSource() == botao4) {
            dispose();
            new DeleteTask();
        } else if (e.getSource() == botao5) {
            dispose();
            new EditTask();
        } else if (e.getSource() == botao6) {
            dispose();
            SwingUtilities.invokeLater(() -> {
                ListaUser tela = new ListaUser(usersDAO.listaUsers());
                tela.setVisible(true);
            });
            
        } 
        else if (e.getSource() == botao7) {
            dispose();
            SwingUtilities.invokeLater(() -> {
                ListaTask tela = new ListaTask(taskDAO.listaTask());
                tela.setVisible(true);
            });
            
        } 
    }

    public static void main(String[] args) {
        new Menu();
    }
}

class TelaDestino extends JFrame {
    public TelaDestino(String botaoTexto) {
        setTitle("Tela Destino");
        setSize(300, 150);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel painel = new JPanel(new FlowLayout());

        JLabel label = new JLabel("Você clicou no botão: " + botaoTexto);

        painel.add(label);
        add(painel);

        setVisible(true);
    }
}