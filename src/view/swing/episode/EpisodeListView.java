package view.swing.episode;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import java.awt.event.MouseEvent;
import java.time.format.DateTimeFormatter;

import controller.EpisodeController;
import model.entities.Episode;

public class EpisodeListView extends JDialog implements IEpisodeListView {
    private static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private EpisodeController controller;
    private final EpisodeTableModel tableModel = new EpisodeTableModel();
    private final JTable table = new JTable(tableModel);

    public EpisodeListView(JFrame parent) {
        super(parent, "Episodes", true);
        this.controller = new EpisodeController();
        this.controller.setEpisodeListView(this);

        setSize(650, 400);
        setLocationRelativeTo(null);

        JScrollPane scrollPane = new JScrollPane(table);

        table.setRowHeight(36);
        table.setShowGrid(true);
        table.setGridColor(Color.LIGHT_GRAY);

        JButton addButton = new JButton("Adicionar Episode");
        addButton.addActionListener(e -> {
            EpisodeFormView form = new EpisodeFormView(this, null, controller);
            form.setVisible(true);
        });

        // Menu de contexto
        JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem editItem = new JMenuItem("Editar");
        JMenuItem deleteItem = new JMenuItem("Excluir");
        popupMenu.add(editItem);
        popupMenu.add(deleteItem);

         table.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                showPopup(e);
            }
            @Override
            public void mouseReleased(MouseEvent e) {
                showPopup(e);
            }
            private void showPopup(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    int row = table.rowAtPoint(e.getPoint());
                    if (row >= 0 && row < table.getRowCount()) {
                        table.setRowSelectionInterval(row, row);
                        popupMenu.show(table, e.getX(), e.getY());
                    }
                }
            }
        });

        editItem.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                Episode episode = tableModel.getEpisodeAt(row);
                EpisodeFormView form = new EpisodeFormView(this, episode, controller);
                form.setVisible(true);
            }
        });

        deleteItem.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                Episode episode = tableModel.getEpisodeAt(row);
                int confirm = JOptionPane.showConfirmDialog(this, "Excluir episode?", "Confirmação", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    controller.excluirEpisode(episode);
                }
            }
        });

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(addButton, BorderLayout.EAST);

        add(scrollPane, BorderLayout.CENTER);
        add(panel, BorderLayout.SOUTH);

        controller.loadEpisodes();
    }

    @Override
    public void setEpisodeList(List<Episode> episodes) {
        tableModel.setEpisodes(episodes);
    }

    @Override
    public void showMessage(String msg) {
        JOptionPane.showMessageDialog(this, msg);
    }

    // Atualiza lista após cadastro/edição/exclusão
    public void refresh() {
        controller.loadEpisodes();
    }

    // Tabela de episodes
    static class EpisodeTableModel extends AbstractTableModel {
        private final String[] columns = {"titulo", "numero", "data de lancamento", "anime"};
        private List<Episode> episodes = new ArrayList<>();

        public void setEpisodes(List<Episode> episodes) {
            this.episodes = episodes;
            fireTableDataChanged();
        }

        public Episode getEpisodeAt(int row) {
            return episodes.get(row);
        }

        @Override public int getRowCount() { return episodes.size(); }

        @Override public int getColumnCount() { return columns.length; }

        @Override public String getColumnName(int col) { return columns[col]; }

        @Override
        public Object getValueAt(int row, int col) {
            Episode p = episodes.get(row);
            switch (col) {
                case 0: return p.getTitle();
                case 1: return p.getNumber();
                case 2: return p.getReleaseDate().format(dateFormatter);
                case 3: return p.getAnime().getName();
                default: return null;
            }
        }
        @Override public boolean isCellEditable(int row, int col) { return false; }
    }
}
