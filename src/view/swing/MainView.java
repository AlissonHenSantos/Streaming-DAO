package view.swing;

import java.awt.BorderLayout;

import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import view.swing.anime.AnimeListView;

public class MainView extends JFrame {
    private static final long serialVersionUID = 1L;

	public MainView() {
        setTitle("Facebook CRUD - Swing");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JMenuBar menuBar = new JMenuBar();

       
        JMenu menu = new JMenu("Animes");
        JMenuItem animeListItem = new JMenuItem("Listar Animes");
        animeListItem.addActionListener(e -> new AnimeListView(this).setVisible(true));
        menu.add(animeListItem);
        menuBar.add(menu);

      
        JMenu episodeMenu = new JMenu("Episódios");
        JMenuItem episodeListItem = new JMenuItem("Listar Episódios");
        
        episodeMenu.add(episodeListItem);
        menuBar.add(episodeMenu);

       
        menuBar.add(Box.createHorizontalGlue());

        JMenu menuSair = new JMenu("...");
        JMenuItem sairItem = new JMenuItem("Fechar o sistema");
        sairItem.addActionListener(e -> System.exit(0));
        menuSair.add(sairItem);
        menuBar.add(menuSair);

        setJMenuBar(menuBar);

        JLabel label = new JLabel("Seja bem-vindo!", SwingConstants.CENTER);

       
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(32, 32, 32, 32));
        contentPanel.add(label, BorderLayout.CENTER);

        setContentPane(contentPanel);
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        SwingUtilities.invokeLater(() -> {
            LoginView login = new LoginView();
            login.setVisible(true);
            if (login.isAuthenticated()) {
                MainView mainView = new MainView();
                mainView.setVisible(true);
                mainView.setExtendedState(JFrame.MAXIMIZED_BOTH);
            } else {
                System.exit(0);
            }
        });
    }
}
