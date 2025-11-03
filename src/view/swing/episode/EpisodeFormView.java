package view.swing.episode;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.text.ParseException;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JOptionPane;
import javax.swing.text.MaskFormatter;

import controller.EpisodeController;
import model.entities.Episode;
import model.entities.Anime;

public class EpisodeFormView extends JDialog implements IEpisodeFormView {
    private final JTextArea titleArea = new JTextArea(5, 20);
    private final JTextArea numberArea = new JTextArea(5, 20);
    private JFormattedTextField releaseDateField;
    private final JComboBox<Anime> animeComboBox = new JComboBox<>();
    private final JButton saveButton = new JButton("Salvar");
    private final JButton closeButton = new JButton("Fechar");
    private EpisodeController controller;
    private final boolean isNew;
    private final EpisodeListView parent;
    private Episode episode;

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    public EpisodeFormView(EpisodeListView parent, Episode episode, EpisodeController controller) {
        super(parent, true);
        this.controller = controller;
        this.controller.setEpisodeFormView(this);

        this.parent = parent;
        this.episode = episode;
        this.isNew = (episode == null);

        setTitle(isNew ? "Novo Episode" : "Editar Episode");
        setSize(600, 500);
        setLocationRelativeTo(parent);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        try {
            MaskFormatter mf = new MaskFormatter("##-##-####");
            mf.setPlaceholderCharacter('_');
            this.releaseDateField = new JFormattedTextField(mf);
            this.releaseDateField.setColumns(10);
        } catch (ParseException ex) {
            this.releaseDateField = new JFormattedTextField();
            this.releaseDateField.setColumns(10);
        }

        gbc.gridx = 0; gbc.gridy = 0;
        add(new JLabel("Anime:"), gbc);
        gbc.gridx = 1;
        add(animeComboBox, gbc);

        List<Anime> animes = controller.getAllAnimes();
        DefaultComboBoxModel<Anime> comboModel = new DefaultComboBoxModel<>();
        for (Anime a : animes) {
            comboModel.addElement(a);
        }
        animeComboBox.setModel(comboModel);

        gbc.gridx = 0; gbc.gridy = 1;
        add(new JLabel("title:"), gbc);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.BOTH;
        add(new JScrollPane(titleArea), gbc);


        gbc.gridx = 0; gbc.gridy = 2;
        add(new JLabel("Número:"), gbc);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.BOTH;
        add(new JScrollPane(numberArea), gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        add(new JLabel("Data de Lançamento:"), gbc);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.NONE;
        add(releaseDateField, gbc);

        JPanel btnPanel = new JPanel();
        btnPanel.add(saveButton);
        btnPanel.add(closeButton);

        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        add(btnPanel, gbc);
        if (!isNew) setEpisodeInForm(episode);

        saveButton.addActionListener(e -> controller.saveOrUpdate(isNew));
        closeButton.addActionListener(e -> close());
    }

    @Override
    public Episode getEpisodeFromForm() {
        if (episode == null) episode = new Episode();
        episode.setTitle(titleArea.getText());
        episode.setNumber(Integer.parseInt(numberArea.getText()));
        String dateText = releaseDateField.getText().trim();
        try {
            episode.setReleaseDate(LocalDate.parse(dateText, DATE_FORMATTER));
        } catch (Exception ex) {
            throw new IllegalArgumentException("Data inválida. Use o formato dd-MM-yyyy");
        }
        episode.setAnime((Anime) animeComboBox.getSelectedItem());
        return episode;
    }
    @Override
    public void setEpisodeInForm(Episode episode) {
        titleArea.setText(episode.getTitle());
        numberArea.setText(String.valueOf(episode.getNumber()));
        if (episode.getReleaseDate() != null) {
            releaseDateField.setText(episode.getReleaseDate().format(DATE_FORMATTER));
        } else {
            releaseDateField.setValue(null);
        }
        if (episode.getAnime() != null) {
            boolean found = false;
            for (int i = 0; i < animeComboBox.getItemCount(); i++) {
                Anime a = animeComboBox.getItemAt(i);
                if (a.getId() == episode.getAnime().getId()) {
                    animeComboBox.setSelectedIndex(i);
                    found = true;
                    break;
                }
            }

            if (!found) {
                animeComboBox.addItem(episode.getAnime());
                animeComboBox.setSelectedItem(episode.getAnime());
            }
        }
    }


    @Override
    public void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Erro", JOptionPane.ERROR_MESSAGE);
    }

    @Override
    public void showInfoMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Informação", JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public void close() {
        parent.refresh();
        dispose();
    }
    
}