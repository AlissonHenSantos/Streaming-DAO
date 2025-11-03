package view.swing.anime;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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

import controller.AnimeController;
import model.entities.Anime;

public class AnimeListView extends JDialog implements IAnimeListView {
	private static final long serialVersionUID = 1L;
	
	private AnimeController animeController;
	
	private final AnimeTableModel tableModel = new AnimeTableModel();
	private final JTable table = new JTable(tableModel);

	public AnimeListView(JFrame parent) {
		  super(parent, "Animes", true);
	        this.animeController = new AnimeController();
	        this.animeController.setAnimeListView(this);
	        
	        setSize(650, 400);
	        setLocationRelativeTo(null);
	        
	        JScrollPane scrollPane = new JScrollPane(table);

	        table.setRowHeight(36);

	        table.setShowGrid(true);
	        table.setGridColor(Color.LIGHT_GRAY);
	        
	        JButton addButton = new JButton("Adicionar anime");
	        addButton.addActionListener(e -> {
	            AnimeFormView form = new AnimeFormView(this, null, animeController);
	            form.setVisible(true);
	        });
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
	                Anime anime = tableModel.getAnimeAt(row);
	                AnimeFormView form = new AnimeFormView(this, anime, animeController);
	                form.setVisible(true);
	            }
	        });
	        
	        deleteItem.addActionListener(e -> {
	            int row = table.getSelectedRow();
	            if (row >= 0) {
	                Anime anime = tableModel.getAnimeAt(row);
	                int confirm = JOptionPane.showConfirmDialog(this, "Excluir Anime?", "Confirmação", JOptionPane.YES_NO_OPTION);
	                if (confirm == JOptionPane.YES_OPTION) {
	                    animeController.excluirUsuario(anime);
	                }
	            }
	        });
	        JPanel panel = new JPanel(new BorderLayout());
	        panel.add(addButton, BorderLayout.EAST);

	        add(scrollPane, BorderLayout.CENTER);
	        add(panel, BorderLayout.SOUTH);

	        animeController.loadAnimes();
	}
	
	
	@Override
	public void setAnimeList(List<Anime> animes) {
		tableModel.setAnimes(animes);
	}

	@Override
	public void showMessage(String msg) {
		JOptionPane.showMessageDialog(this, msg);
	}
	
	public void refresh() {
        animeController.loadAnimes();
    }
	
	static class AnimeTableModel extends AbstractTableModel{

		private final String[] columns =  {"ID", "Nome", "Descrição"};
		
		private List<Anime> animes = new ArrayList<>();
		
		public void setAnimes(List<Anime> animes) {
			this.animes = animes;
			fireTableDataChanged();
		}
		public Anime getAnimeAt(int row) {
            return animes.get(row);
        }
		@Override public int getRowCount() { return animes.size(); }

        @Override public int getColumnCount() { return columns.length; }

        @Override public String getColumnName(int col) { return columns[col]; }
        
        
        
		@Override
		public Object getValueAt(int row, int col) {
			 Anime a = animes.get(row);
	            switch (col) {
	                case 0: return a.getId();
	                case 1: return a.getName();
	                case 2: return a.getDescription();
	                default: return null;
	            }
		}

		  @Override public boolean isCellEditable(int row, int col) { return false; }
	
	}

}
