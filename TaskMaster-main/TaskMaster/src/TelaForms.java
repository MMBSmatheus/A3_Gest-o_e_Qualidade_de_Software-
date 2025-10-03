import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class TelaForms extends JFrame {

    private JButton loginButton;
    private JButton cadastrarButton;

    public TelaForms() {
        initComponents();
    }

    private void initComponents() {
        JLabel titleLabel = new JLabel("TaskMaster", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));

        loginButton = new JButton("Login");
        cadastrarButton = new JButton("Cadastrar");

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(titleLabel, gbc);
        
        gbc.gridwidth = 1;
        gbc.gridy = 1;
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.EAST;
        add(loginButton, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        add(cadastrarButton, gbc);

        loginButton.addActionListener(e -> loginAction());
        cadastrarButton.addActionListener(e -> new TelaCadastroUsuario().setVisible(true));

        setPreferredSize(new Dimension(400, 200));
        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private void loginAction() {
        String nomeUsuario = JOptionPane.showInputDialog(this, "Nome de usuário:");
        String senha = JOptionPane.showInputDialog(this, "Senha:");

        if (nomeUsuario != null && senha != null) {
            try {
                Connection connection = ConexaoDB.obtemConexao();
                String sql = "SELECT tipo FROM Usuario WHERE nome = ? AND senha = ?";
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setString(1, nomeUsuario);
                statement.setString(2, senha);
                ResultSet resultSet = statement.executeQuery();

                if (resultSet.next()) {
                    String tipo = resultSet.getString("tipo");
                    if ("adm".equalsIgnoreCase(tipo)) {
                        new TelaTarefasCRUD().setVisible(true);
                    } else {
                        new TelaTarefasCreateRead(nomeUsuario).setVisible(true);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Nome de usuário ou senha incorretos.", "Erro", JOptionPane.ERROR_MESSAGE);
                }

                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Erro ao acessar o banco de dados.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> new TelaForms().setVisible(true));
    }
}

