
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class TelaTarefasCreateRead extends JFrame {
    private JTable table;
    private DefaultTableModel tableModel;
    private Connection connection;
    private String loggedUserName;

    public TelaTarefasCreateRead(String loggedUserName) throws SQLException {
        this.loggedUserName = loggedUserName;
        connection = ConexaoDB.obtemConexao();
        initComponents();
        loadTasks();
    }

    private void initComponents() {
        setTitle("Tarefas Atribuídas a " + loggedUserName);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        tableModel = new DefaultTableModel(new String[]{"ID", "Título", "Descrição", "Status"}, 0);
        table = new JTable(tableModel) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 3;
            }
        };

        JComboBox<String> statusComboBox = new JComboBox<>(new String[]{"pendente", "em_andamento", "concluída", "atrasada"});
        TableColumn statusColumn = table.getColumnModel().getColumn(3);
        statusColumn.setCellEditor(new DefaultCellEditor(statusComboBox));

        table.getModel().addTableModelListener(e -> {
            int row = e.getFirstRow();
            int column = e.getColumn();
            if (column == 3) {
                int taskId = (int) tableModel.getValueAt(row, 0);
                String newStatus = (String) tableModel.getValueAt(row, column);
                updateTaskStatus(taskId, newStatus);
            }
        });

        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setRowHeight(30);

        JPanel mainPanel = new JPanel(new BorderLayout());

        JLabel welcomeLabel = new JLabel("Seja bem-vindo, " + loggedUserName + "! Essas são as tarefas atribuídas a você!");
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        mainPanel.add(welcomeLabel, BorderLayout.NORTH);

        JScrollPane tableScrollPane = new JScrollPane(table);
        mainPanel.add(tableScrollPane, BorderLayout.CENTER);

        // Cria o painel inferior para o botão de voltar
        JPanel bottomPanel = new JPanel();
        JButton backButton = new JButton("Voltar");
        bottomPanel.add(backButton);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        // Adiciona o ActionListener ao botão de voltar
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Fecha a janela atual
                // Aqui você abre a janela anterior
                // Exemplo:
                // new TelaAnterior().setVisible(true);
            }
        });

        setContentPane(mainPanel);
    }

    private void loadTasks() {
        tableModel.setRowCount(0);
        try (PreparedStatement stmt = connection.prepareStatement(
                "SELECT * FROM Tarefa WHERE responsavel = ?")) {
            stmt.setString(1, loggedUserName);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                tableModel.addRow(new Object[]{
                        rs.getInt("idTarefa"),
                        rs.getString("titulo"),
                        rs.getString("descricao"),
                        rs.getString("status")
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro ao carregar tarefas.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateTaskStatus(int taskId, String newStatus) {
        try (PreparedStatement ps = connection.prepareStatement(
                "UPDATE Tarefa SET status = ? WHERE idTarefa = ?")) {
            ps.setString(1, newStatus);
            ps.setInt(2, taskId);
            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro ao atualizar status da tarefa.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                String loggedUserName = "Nome do Usuário";
                new TelaTarefasCreateRead(loggedUserName).setVisible(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }
}
