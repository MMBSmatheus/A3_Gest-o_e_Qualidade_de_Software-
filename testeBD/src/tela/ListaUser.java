package tela;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import entity.Users;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import java.util.ArrayList;

public class ListaUser  extends JFrame{
    private JTable tabela;
    private DefaultTableModel modelo;

    public ListaUser(List<Users> listaUsers) {
        setTitle("Tabela de Dados");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Criando o modelo da tabela
        modelo = new DefaultTableModel();
        tabela = new JTable(modelo);

        // Adicionando colunas ao modelo da tabela
        modelo.addColumn("ID");
        modelo.addColumn("Username");

        // Preenchendo a tabela com os dados da lista
        for (Users user : listaUsers) {
            modelo.addRow(user.toArray());
        }

        // Adicionando a tabela a um JScrollPane para possibilitar rolagem
        JScrollPane scrollPane = new JScrollPane(tabela);
        getContentPane().add(scrollPane, BorderLayout.CENTER);

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
    }

}
