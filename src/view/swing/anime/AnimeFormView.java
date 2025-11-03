package view.swing.anime;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import controller.AnimeController;
import model.entities.Anime;

public class AnimeFormView extends JDialog implements IAnimeFormView{
	
	 	private final JTextField nameField = new JTextField(20);
	    private final JTextField descriptionField = new JTextField(20);
	    private final JButton saveButton = new JButton("Salvar");
	    private final JButton closeButton = new JButton("Fechar");
	    private AnimeController controller;
	    private final boolean isNew;
	    private final AnimeListView parent;
	    private Anime anime;
	
	public AnimeFormView(AnimeListView parent, Anime anime, AnimeController controller){
		  super(parent, true);
	        this.controller = controller;
	        this.controller.setAnimeFormView(this);
	        
	        this.parent = parent;
	        this.anime = anime;
	        this.isNew = (anime == null);
	        
	        setTitle(isNew ? "Novo Anime" : "Editar Anime");
	        setSize(350, 220);
	        setLocationRelativeTo(parent);
	        setLayout(new GridBagLayout());
	        GridBagConstraints gbc = new GridBagConstraints();
	        gbc.insets = new Insets(5,5,5,5);
	        gbc.fill = GridBagConstraints.HORIZONTAL;
	        
	        gbc.gridx = 0; gbc.gridy = 0;
	        add(new JLabel("Nome:"), gbc);
	        gbc.gridx = 1;
	        add(nameField, gbc);
	        
	        gbc.gridx = 0; gbc.gridy = 2;
	        add(new JLabel("Descrição:"), gbc);
	        gbc.gridx = 1;
	        add(descriptionField, gbc);
	        
	        JPanel btnPanel = new JPanel();
	        btnPanel.add(saveButton);
	        btnPanel.add(closeButton);

	        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
	        add(btnPanel, gbc);

	        if (!isNew) setAnimeInForm(anime);

	        saveButton.addActionListener(e -> controller.saveOrUpdate(isNew));
	        closeButton.addActionListener(e -> close());
	}

	@Override
	public Anime getAnimeFromForm() {
		if (anime == null) anime = new Anime();
		anime.setName(nameField.getText());
		anime.setDescription(descriptionField.getText());
		return anime;
	}

	@Override
	public void setAnimeInForm(Anime user) {
		nameField.setText(user.getName());
        descriptionField.setText(user.getDescription());
	}

	@Override
	public void showInfoMessage(String msg) {
		 JOptionPane.showMessageDialog(this, msg, "Informação", JOptionPane.INFORMATION_MESSAGE);
	}

	@Override
	public void showErrorMessage(String msg) {
		JOptionPane.showMessageDialog(this, msg, "Erro", JOptionPane.ERROR_MESSAGE);
	}

	@Override
	public void close() {
		parent.refresh();
	     dispose();
	}
	
}
