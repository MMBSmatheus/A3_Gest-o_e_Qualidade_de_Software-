
import javax.swing.*;
import java.awt.*;
import java.sql.*;

class TelaCadastroUsuario extends JFrame {
    private JTextField nomeField;
    private JPasswordField senhaField;
    private JComboBox<String> tipoComboBox;
    private JButton cadastrarButton;
    private JButton voltarButton;

    public TelaCadastroUsuario() {
        initComponents();
    }

    private void initComponents() {
        setTitle("Cadastro de Usuário");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JLabel nomeLabel = new JLabel("Nome:");
        nomeField = new JTextField(20);

        JLabel senhaLabel = new JLabel("Senha:");
        senhaField = new JPasswordField(20);

        JLabel tipoLabel = new JLabel("Tipo:");
        tipoComboBox = new JComboBox<>(new String[]{"user", "adm"});

        cadastrarButton = new JButton("Cadastrar");
        cadastrarButton.addActionListener(e -> cadastrarUsuario());

        voltarButton = new JButton("Voltar");
        voltarButton.addActionListener(e -> this.dispose());

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        add(nomeLabel, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        add(nomeField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        add(senhaLabel, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        add(senhaField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        add(tipoLabel, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        add(tipoComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(cadastrarButton, gbc);

        gbc.gridy = 4;
        add(voltarButton, gbc);

        pack();
    }

    private void cadastrarUsuario() {
        String nome = nomeField.getText();
        String senha = new String(senhaField.getPassword());
        String tipo = (String) tipoComboBox.getSelectedItem();

        if ("adm".equalsIgnoreCase(tipo)) {
            String senhaAdm = JOptionPane.showInputDialog(this, "Digite a senha de administrador:");
            if (!"TaskMaster".equals(senhaAdm)) {  
                JOptionPane.showMessageDialog(this, "Senha de administrador incorreta.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        try {
            Connection connection = ConexaoDB.obtemConexao();
            String sql = "INSERT INTO Usuario (nome, senha, tipo) VALUES (?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, nome);
            statement.setString(2, senha);
            statement.setString(3, tipo);
            statement.executeUpdate();
            connection.close();

            JOptionPane.showMessageDialog(this, "Usuário cadastrado com sucesso.", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            this.dispose();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro ao cadastrar usuário.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}