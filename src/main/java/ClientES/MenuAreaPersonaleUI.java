package ClientES;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Emilio Toli
 *         Classe che gestisce l'interfaccia utente del menu dell'area personale
 */

public class MenuAreaPersonaleUI extends JFrame {

    /**
     * @author Emilio Toli
     *         Metodo che crea l'interfaccia utente con i pulsanti e che chiama i
     *         metodi degli eventi
     *         relativi ad essi
     */
    // <editor-fold desc= "Menu Area Personale">
    public void areaPersonale() {
        setTitle("Emotional Songs - Area Personale");
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        JLabel label = new JLabel("Area Personale");
        label.setFont(new Font("Arial", Font.BOLD, 24)); // Imposta il font e la dimensione
        label.setHorizontalAlignment(JLabel.CENTER);
        add(label, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets.top = 10;
        gbc.insets.bottom = 10;

        JButton creaPlaylistButton = GraphicUtils.createButtons("Crea nuova playlist");
        buttonPanel.add(creaPlaylistButton, gbc);
        gbc.gridy++;
        creaPlaylistButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                creaPlaylist();
            }
        });

        JButton visualizzaPlaylistButton = GraphicUtils.createButtons("Visualizza le tue playlist");
        buttonPanel.add(visualizzaPlaylistButton, gbc);
        gbc.gridy++;
        visualizzaPlaylistButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                visualizzaPlaylist();
            }
        });

        JButton visualizzaCanzoniPlaylistButton = GraphicUtils.createButtons("Visualizza una playlist");
        buttonPanel.add(visualizzaCanzoniPlaylistButton, gbc);
        gbc.gridy++;
        visualizzaCanzoniPlaylistButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                visualizzaCanzoniPlaylist();
            }
        });

        JButton aggiungiCanzonePlaylistButton = GraphicUtils.createButtons("Aggiungi canzone alla playlist");
        buttonPanel.add(aggiungiCanzonePlaylistButton, gbc);
        gbc.gridy++;
        aggiungiCanzonePlaylistButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                aggiungiAllaPlaylist();
            }
        });

        JButton rimuoviCanzonePlaylist = GraphicUtils.createButtons("Rimuovi canzone dalla playlist");
        buttonPanel.add(rimuoviCanzonePlaylist, gbc);
        gbc.gridy++;
        rimuoviCanzonePlaylist.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminaCanzoneDaPlaylist();
            }
        });

        JButton eliminaPlaylist = GraphicUtils.createButtons("Elimina Playlist");
        buttonPanel.add(eliminaPlaylist, gbc);
        gbc.gridy++;
        eliminaPlaylist.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminaPlaylist();
            }
        });

        add(buttonPanel, BorderLayout.CENTER);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    }

    /**
     * @author Emilio Toli
     *         Metodo per l'apertura dell'interfaccia che gestisce le nuove playlist
     *         e che gestisce i casi di errore
     */
    // <editor-fold desc= "Apertura UI per creare la playlist">
    private void creaPlaylist() {
        NuovaPlaylistUI nuovaPlaylist = new NuovaPlaylistUI();
        nuovaPlaylist.nuovaPlaylistUI(new PlaylistCreationCallback() {

            public void onPlaylistCreationResult(int result) {
                if (result == 0) {
                    // La playlist è stata creata con successo
                    // Puoi gestire il successo qui, ad esempio aggiornando l'interfaccia utente
                    JOptionPane.showMessageDialog(MenuAreaPersonaleUI.this, "Playlist creata con successo", "Successo",
                            JOptionPane.INFORMATION_MESSAGE);

                } else if (result == 1) {
                    // Playlist già esistente, mostra un messaggio di errore
                    JOptionPane.showMessageDialog(MenuAreaPersonaleUI.this, "Playlist già esistente", "Errore",
                            JOptionPane.ERROR_MESSAGE);
                } else {
                    // Altri casi di errore, gestisci secondo necessità
                    JOptionPane.showMessageDialog(MenuAreaPersonaleUI.this,
                            "Errore durante la creazione della playlist", "Errore", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

    }
    // </editor-fold>

    /**
     * @author Emilio Toli
     *         Metodo che apre l'interfaccia per l'aggiunta di canzoni alla playlist
     */

    // <editor-fold desc= "Apertura UI aggiunta canzoni alla playlist">
    private void aggiungiAllaPlaylist() {
        AggiungiCanzonePlaylistUI acp = new AggiungiCanzonePlaylistUI();
        acp.aggiungiCanzoniAPlaylist();
    }
    // </editor-fold>

    /**
     * @author Emilio Toli
     *         Metodo che apre l'interfaccia per la visualizzazione delle playlist
     *         dell'utente
     */
    // <editor-fold desc= "Apertura UI per visualizzare playlist">
    private void visualizzaPlaylist() {
        VisualizzaPlaylistUI vp = new VisualizzaPlaylistUI();
        vp.visualizzaPlaylist();
    }
    // </editor-fold>

    /**
     * @author Emilio Toli
     *         Metodo che apre l'interfaccia per l'eliminazione di una playlist
     *         e che gestisce i messaggi di errore
     */
    // <editor-fold desc= "Apertura UI per eliminare la playlist">
    private void eliminaPlaylist() {
        EliminaPlaylistUI ep = new EliminaPlaylistUI();
        ep.eliminaPlaylistUI(new PlaylistDeletionCallback() {
            @Override
            public void onPlaylistDeletionResult(int result) {
                switch (result) {
                    case 0 -> JOptionPane.showMessageDialog(MenuAreaPersonaleUI.this, "Playlist eliminata con successo",
                            "Successo", JOptionPane.INFORMATION_MESSAGE);
                    case 1 -> JOptionPane.showMessageDialog(MenuAreaPersonaleUI.this,
                            "Errore nella cancellazione della PlayList", "Errore", JOptionPane.ERROR_MESSAGE);
                    case -1 ->
                        JOptionPane.showMessageDialog(MenuAreaPersonaleUI.this, "Errore! Nome Plalist non trovato",
                                "Errore", JOptionPane.ERROR_MESSAGE);
                    case -2 -> JOptionPane.showMessageDialog(MenuAreaPersonaleUI.this,
                            "Errore durante l'inizializzazione dell'interfaccia", "Errore",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
    // </editor-fold>

    /**
     * @author Emilio Toli
     *         Metodo che apre l'interfaccia per l'eliminazione di una canzone dalla
     *         playlist
     */

    // <editor-fold desc= "Apertura UI per eliminazione di una canzone dalla
    // playlist">
    private void eliminaCanzoneDaPlaylist() {
        EliminaCanzonePlaylistUI edp = new EliminaCanzonePlaylistUI();
        edp.eliminaCanzoneDaPlaylist();
    }
    // </editor-fold>

    /**
     * @author Emilio Toli
     *         Metodo che apre l'interfaccia per la visualizzazione delle canzoni di
     *         una playlist
     */

    // <editor-fold desc= "Apertura UI per la visualizzazione di canzoni di una
    // playlist">
    private void visualizzaCanzoniPlaylist() {
        VisualizzaCanzoniPlaylist vcp = new VisualizzaCanzoniPlaylist();
        vcp.visualizzaCanzoni();
    }
    // </editor-fold>
}
