package view.swing;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import controller.AuthController;
import model.entities.User;


public class RecoverPassword extends JDialog {
    private JTextField emailField = new JTextField(25);
    private JButton recoverButton = new JButton("Recuperar");
    private JButton cancelButton = new JButton("Cancelar");
    private AuthController authController;

    public RecoverPassword() {
        authController = new AuthController();

        setTitle("Recuperar senha");
        setSize(420, 150);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.gridx = 0; gbc.gridy = 0;
        add(new JLabel("E-mail:"), gbc);
        gbc.gridx = 1;
        add(emailField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        add(recoverButton, gbc);
        gbc.gridx = 1;
        add(cancelButton, gbc);

        recoverButton.addActionListener(e -> onRecover());
        cancelButton.addActionListener(e -> {
            dispose();
        });
      
        setModal(true);
        setLocationRelativeTo(null);
    }

    private void onRecover() {
        String email = emailField.getText().trim();
        if (email.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Informe o e-mail.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        User user;
        try {
            user = authController.findByEmail(email);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao buscar usuário: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (user == null) {
            // se não encontrado, mostrar erro e voltar para o login (parent)
            JOptionPane.showMessageDialog(this, "E-mail não encontrado.", "Erro", JOptionPane.ERROR_MESSAGE);
            dispose();
            return;
        }

        // se encontrado, abrir pop-up para digitar nova senha
        boolean updated = askAndUpdatePassword(user);
        if (updated) {
            JOptionPane.showMessageDialog(this, "Senha atualizada com sucesso.", "Informação", JOptionPane.INFORMATION_MESSAGE);
        }
        dispose();
    }

    private boolean askAndUpdatePassword(User user) {
        JPasswordField pf1 = new JPasswordField(20);
        JPasswordField pf2 = new JPasswordField(20);
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Nova senha:"), gbc);
        gbc.gridx = 1;
        panel.add(pf1, gbc);
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("Confirmar senha:"), gbc);
        gbc.gridx = 1;
        panel.add(pf2, gbc);

        int option = JOptionPane.showConfirmDialog(this, panel, "Digite a nova senha", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (option != JOptionPane.OK_OPTION) return false;

        String s1 = new String(pf1.getPassword()).trim();
        String s2 = new String(pf2.getPassword()).trim();

        if (s1.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Senha não pode ser vazia.", "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (!s1.equals(s2)) {
            JOptionPane.showMessageDialog(this, "Senhas não conferem.", "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // chama o serviço para atualizar a senha (não envia e-mail de fato)
        boolean passwordUpdated;
        try {
            passwordUpdated = authController.recoverPassword(user.getEmail(), s1);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao atualizar senha: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (!passwordUpdated) {
            JOptionPane.showMessageDialog(this, "Não foi possível atualizar a senha.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
        return passwordUpdated;
    }
}