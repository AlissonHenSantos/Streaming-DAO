package view.swing;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import controller.AuthController;
import model.ModelException;
import model.entities.User;

public class LoginView extends JDialog {
 
	private static final long serialVersionUID = 1L;
	
	private boolean authenticated = false;
    private final JTextField emailField = new JTextField(20);
    private final JPasswordField passwordField = new JPasswordField(20);
    private final JButton registerButton = new JButton("Registrar");
    private  JButton recoverButton = new JButton("Recuperar senha");
    private AuthController authController;

    public LoginView() {
    	this.authController = new AuthController();
    	
        setTitle("Streaming - Login");
        setModal(true);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel form = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.gridx = 0; gbc.gridy = 0; gbc.anchor = GridBagConstraints.EAST;
        form.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST;
        form.add(emailField, gbc);

        gbc.gridx = 0; gbc.gridy = 1; gbc.anchor = GridBagConstraints.EAST;
        form.add(new JLabel("Senha:"), gbc);
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST;
        form.add(passwordField, gbc);
        gbc.gridx = 1; gbc.gridy = 4; gbc.anchor = GridBagConstraints.CENTER;
        form.add(recoverButton, gbc);

        JPanel buttons = new JPanel();
        JButton loginBtn = new JButton("Entrar");
        JButton cancelBtn = new JButton("Cancelar");
        buttons.add(loginBtn);
        buttons.add(cancelBtn);
        buttons.add(registerButton);
        

        loginBtn.addActionListener(e -> {
            String email = emailField.getText();
            String password = new String(passwordField.getPassword());
            
            User user;
			try {
				user = authController.login(new User(0, "", email, password));
				 if (user != null) {
		                authenticated = true;
		                dispose();
		            } else {
		                JOptionPane.showMessageDialog(this, "Email ou senha inválidos.", "Erro", JOptionPane.ERROR_MESSAGE);
		            }
			} catch (ModelException e1) {
				JOptionPane.showMessageDialog(this, e1.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
			}
            
        });

        registerButton.addActionListener( e -> {
            new Register().setVisible(true);
        });

        cancelBtn.addActionListener(e -> {
            authenticated = false;
            this.setVisible(false);
            dispose();
        });
        recoverButton.addActionListener(e -> {
            RecoverPassword rp = new RecoverPassword();
            rp.setVisible(true);
        });

        add(form, BorderLayout.CENTER);
        add(buttons, BorderLayout.SOUTH);
        pack();
        setLocationRelativeTo(null);
    }

    public boolean isAuthenticated() {
        return authenticated;
    }
}

