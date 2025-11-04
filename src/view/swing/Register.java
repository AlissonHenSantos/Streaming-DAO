package view.swing;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JOptionPane;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import controller.AuthController;
import model.entities.User;

public class Register extends JDialog{

    private AuthController authController;
    private JTextField emailField = new JTextField(20);
    private  JPasswordField passwordField = new JPasswordField(20);
    private JTextField nameField = new JTextField(20);
    private JButton registerButton = new JButton("Registrar");
    private  JButton cancelButton = new JButton("Cancelar");


    public Register() {
        authController = new AuthController();
        setTitle("Register");
        setModal(true);
        setSize(400, 300);
        setLocationRelativeTo(null);

        JPanel form = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);

        gbc.gridx = 0; gbc.gridy = 0; gbc.anchor = GridBagConstraints.EAST;
        form.add(new JLabel("Name:"), gbc);
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST;
        form.add(nameField, gbc);

        gbc.gridx = 0; gbc.gridy = 1; gbc.anchor = GridBagConstraints.EAST;
        form.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST;
        form.add(emailField, gbc);

        gbc.gridx = 0; gbc.gridy = 2; gbc.anchor = GridBagConstraints.EAST;
        form.add(new JLabel("Password:"), gbc);
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST;
        form.add(passwordField, gbc);

        gbc.gridx = 1; gbc.gridy = 3; gbc.anchor = GridBagConstraints.LAST_LINE_END;
        form.add(registerButton, gbc);

        gbc.gridx = 1; gbc.gridy = 3; gbc.anchor = GridBagConstraints.LAST_LINE_START;
        form.add(cancelButton, gbc);

                registerButton.addActionListener(e -> {
                    String name = nameField.getText();
                    String email = emailField.getText();
                    String password = new String(passwordField.getPassword());
        
                    User newUser = new User(0, name, email, password);
                    try {
                        authController.register(newUser);
                        JOptionPane.showMessageDialog(this, "Usuário cadastrado", "Success", JOptionPane.INFORMATION_MESSAGE);
                        dispose();
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(this, ex.getMessage(), "Registration Error", JOptionPane.ERROR_MESSAGE);
                    }
                });

                cancelButton.addActionListener(e -> {
                    this.setVisible(false);
                    this.dispose();
                });

        add(form);
    }
}
