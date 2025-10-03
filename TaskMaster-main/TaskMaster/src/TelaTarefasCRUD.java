
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.awt.event.*;

public class TelaTarefasCRUD extends JFrame {
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField tituloField;
    private JTextArea descricaoArea;
    private JComboBox<String> statusComboBox;
    private JComboBox<String> userComboBox;
    private JButton addButton, updateButton, deleteButton, backButton;
    private int selectedTaskId = -1;
    private Connection connection;

    public TelaTarefasCRUD() throws SQLException {
        connection = ConexaoDB.obtemConexao();
        initComponents();
        loadTasks();
        loadUsers();
    }

    private void initComponents() {
        setTitle("Gerenciamento de Tarefas - TaskMaster");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        tableModel = new DefaultTableModel(new String[]{"ID", "Título", "Descrição", "Status", "Responsável"}, 0);
        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getSelectionModel().addListSelectionListener(e -> loadSelectedTask());

        JLabel tituloLabel = new JLabel("Título:");
        tituloField = new JTextField(20);

        JLabel descricaoLabel = new JLabel("Descrição:");
        descricaoArea = new JTextArea(5, 20);
        descricaoArea.setLineWrap(true);
        descricaoArea.setWrapStyleWord(true);

        JLabel statusLabel = new JLabel("Status:");
        statusComboBox = new JComboBox<>(new String[]{"pendente", "em_andamento", "concluida", "atrasada"});

        JLabel userLabel = new JLabel("Responsável:");
        userComboBox = new JComboBox<>();

        addButton = new JButton("Adicionar");
        addButton.addActionListener(e -> addTask());

        updateButton = new JButton("Atualizar");
        updateButton.addActionListener(e -> updateTask());

        deleteButton = new JButton("Deletar");
        deleteButton.addActionListener(e -> deleteTask());

        // Criação do botão de voltar
        backButton = new JButton("Voltar");
        backButton.addActionListener(e -> {
            dispose(); // Fecha a janela atual
            // Aqui você abre a janela anterior
            // Exemplo:
            // new TelaAnterior().setVisible(true);
        });

        JPanel inputPanel = new JPanel(new GridLayout(1, 2, 10, 10));

        JPanel leftPanel = new JPanel(new GridBagLayout());
        GridBagConstraints leftGbc = new GridBagConstraints();
        leftGbc.insets = new Insets(5, 5, 5, 5);
        leftGbc.anchor = GridBagConstraints.WEST;

        leftGbc.gridx = 0;
        leftGbc.gridy = 0;
        leftPanel.add(tituloLabel, leftGbc);
        leftGbc.gridy = 1;
        leftPanel.add(descricaoLabel, leftGbc);

        leftGbc.gridx = 1;
        leftGbc.gridy = 0;
        leftPanel.add(tituloField, leftGbc);
        leftGbc.gridy = 1;
        leftGbc.fill = GridBagConstraints.BOTH;
        leftPanel.add(new JScrollPane(descricaoArea), leftGbc);

        JPanel rightPanel = new JPanel(new GridBagLayout());
        GridBagConstraints rightGbc = new GridBagConstraints();
        rightGbc.insets = new Insets(5, 5, 5, 5);
        rightGbc.anchor = GridBagConstraints.WEST;

        rightGbc.gridx = 0;
        rightGbc.gridy = 0;
        rightPanel.add(statusLabel, rightGbc);
        rightGbc.gridy = 1;
        rightPanel.add(userLabel, rightGbc);

        rightGbc.gridx = 1;
        rightGbc.gridy = 0;
        rightPanel.add(statusComboBox, rightGbc);
        rightGbc.gridy = 1;
        rightPanel.add(userComboBox, rightGbc);

        inputPanel.add(leftPanel);
        inputPanel.add(rightPanel);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(backButton); // Adiciona o botão de voltar ao painel de botões

        setLayout(new BorderLayout());
        add(new JScrollPane(table), BorderLayout.CENTER);
        add(inputPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void loadTasks() {
        tableModel.setRowCount(0);
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT Tarefa.idTarefa, Tarefa.titulo, Tarefa.descricao, Tarefa.status, Tarefa.responsavel, Usuario.nome AS responsavel " +
                                              "FROM Tarefa LEFT JOIN Usuario ON Tarefa.idUsuario = Usuario.idUsuario")) {
            while (rs.next()) {
                tableModel.addRow(new Object[]{
                        rs.getInt("idTarefa"),
                        rs.getString("titulo"),
                        rs.getString("descricao"),
                        rs.getString("status"),
                        rs.getString("responsavel")
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro ao carregar tarefas.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadSelectedTask() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            selectedTaskId = (int) tableModel.getValueAt(selectedRow, 0);
            tituloField.setText((String) tableModel.getValueAt(selectedRow, 1));
            descricaoArea.setText((String) tableModel.getValueAt(selectedRow, 2));
            statusComboBox.setSelectedItem((String) tableModel.getValueAt(selectedRow, 3));
            userComboBox.setSelectedItem((String) tableModel.getValueAt(selectedRow, 4));
        }
    }

    private void loadUsers() {
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT nome FROM Usuario")) {
            while (rs.next()) {
                userComboBox.addItem(rs.getString("nome"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro ao carregar usuários.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addTask() {
        String titulo = tituloField.getText();
        String descricao = descricaoArea.getText();
        String status = (String) statusComboBox.getSelectedItem();
        String responsavel = (String) userComboBox.getSelectedItem();

        try (PreparedStatement ps = connection.prepareStatement(
                "INSERT INTO Tarefa (titulo, descricao, status, responsavel, idUsuario) VALUES (?, ?, ?, ?, " +
                "(SELECT idUsuario FROM Usuario WHERE nome = ?))")) {
            ps.setString(1, titulo);
            ps.setString(2, descricao);
            ps.setString(3, status);
            ps.setString(4, responsavel);
            ps.setString(5, responsavel);
            ps.executeUpdate();
            loadTasks();
            clearInputFields();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro ao adicionar tarefa.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateTask() {
        if (selectedTaskId == -1) {
            JOptionPane.showMessageDialog(this, "Selecione uma tarefa para atualizar.", "Erro", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String titulo = tituloField.getText();
        String descricao = descricaoArea.getText();
        String status = (String) statusComboBox.getSelectedItem();
        String responsavel = (String) userComboBox.getSelectedItem();

        try (PreparedStatement ps = connection.prepareStatement(
                "UPDATE Tarefa SET titulo = ?, descricao = ?, status = ?, responsavel = ?, idUsuario = " +
                "(SELECT idUsuario FROM Usuario WHERE nome = ?) WHERE idTarefa = ?")) {
            ps.setString(1, titulo);
            ps.setString(2, descricao);
            ps.setString(3, status);
            ps.setString(4, responsavel);
            ps.setString(5, responsavel);
            ps.setInt(6, selectedTaskId);
            ps.executeUpdate();
            loadTasks();
            clearInputFields();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro ao atualizar tarefa.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteTask() {
        if (selectedTaskId == -1) {
            JOptionPane.showMessageDialog(this, "Selecione uma tarefa para deletar.", "Erro", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try (PreparedStatement ps = connection.prepareStatement(
                "DELETE FROM Tarefa WHERE idTarefa = ?")) {
            ps.setInt(1, selectedTaskId);
            ps.executeUpdate();
            loadTasks();
            clearInputFields();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro ao deletar tarefa.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearInputFields() {
        selectedTaskId = -1;
        tituloField.setText("");
        descricaoArea.setText("");
        statusComboBox.setSelectedIndex(0);
        userComboBox.setSelectedIndex(0);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                new TelaTarefasCRUD().setVisible(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }
}
