package ClientES;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author Emilio Toli
 *         Classe per l'aggiunta di canzoni in
 *         una playlist
 */

public class AggiungiCanzonePlaylistUI extends JFrame {
    // <editor-fold desc= "Attributi">
    private JTextField playlistNameField;
    private JTextField songTitleField;
    private JTextField songAuthorField;
    private int val;
    // </editor-fold>

    /**
     * @author Emilio Toli
     *         Metodo per la creazione dell'interfaccia utente e per la gestione dei
     *         click sui pulsanti
     *         Mostra inoltre la lista di canzoni che hanno il titolo cercato
     */
    // <editor-fold desc= "Aggiunta canzoni alla playlist">
    public void aggiungiCanzoniAPlaylist() {
        setTitle("Aggiungi Canzone alla Playlist");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(4, 2, 10, 10));
        setVisible(true);

        JLabel playlistNameLabel = GraphicUtils.createLabels("Nome Playlist:");
        playlistNameField = GraphicUtils.createTextFields(20);
        JLabel songTitleLabel = GraphicUtils.createLabels("Titolo Canzone:");
        songTitleField = GraphicUtils.createTextFields(20);
        JLabel songAuthorLabel = GraphicUtils.createLabels("Autore Canzone:");
        songAuthorField = GraphicUtils.createTextFields(20);

        add(playlistNameLabel);
        add(playlistNameField);
        add(songTitleLabel);
        add(songTitleField);
        add(songAuthorLabel);
        add(songAuthorField);

        JButton searchButton = GraphicUtils.createButtons("Ricerca Canzoni");
        JButton submitButton = GraphicUtils.createButtons("Ricerca");

        add(searchButton);
        add(submitButton);

        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String title = JOptionPane.showInputDialog(AggiungiCanzonePlaylistUI.this,
                        "Inserisci il titolo della canzone da cercare:");
                if (title != null && !title.isEmpty()) {
                    ArrayList<Canzone> songList = null; // Sostituisci con il metodo corretto
                    try {
                        songList = Client.RicercaCanzoniTitolo(title);
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                    showSongListPopup(songList);
                }
            }
        });

        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String playlistName = playlistNameField.getText();
                String songTitle = songTitleField.getText();
                String songAuthor = songAuthorField.getText();

                if (!playlistName.isEmpty() && !songTitle.isEmpty() && !songAuthor.isEmpty()) {
                    try {
                        val = Client.aggiuntaCanzoniPlaylist(playlistName, Client.idGlobale, songTitle, songAuthor);
                    } catch (RemoteException remoteException) {
                        remoteException.printStackTrace();
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                    switch (val) {
                        case 0 -> JOptionPane.showMessageDialog(AggiungiCanzonePlaylistUI.this,
                                "Canzone aggiunta con successo dalla playlist", "Successo",
                                JOptionPane.INFORMATION_MESSAGE);
                        case -1 -> JOptionPane.showMessageDialog(AggiungiCanzonePlaylistUI.this,
                                "Nome playlist inesistente", "Errore", JOptionPane.ERROR_MESSAGE);
                        case 1 -> JOptionPane.showMessageDialog(AggiungiCanzonePlaylistUI.this,
                                "Errore nell'aggiunta della canzone al database. Riprova", "Errore",
                                JOptionPane.ERROR_MESSAGE);
                        case -2 -> JOptionPane.showMessageDialog(AggiungiCanzonePlaylistUI.this,
                                "Errore nel collegamento al server. Riprova", "Errore", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(AggiungiCanzonePlaylistUI.this,
                            "Riempi tutti i campi prima di procedere.", "Errore", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
    }
    // </editor-fold>

    /**
     * @author Emilio Toli
     * @param songList Lista delle canzoni che l'utente ricerca prima di inserirle
     *                 in playlist
     *
     *                 Metodo per la visualizzazione a video di tutte le canzoni che
     *                 vengono ricercate
     *                 usando la funzione cerca canzone
     */
    // <editor-fold desc= "Visualizza canzoni per titolo a video">
    private void showSongListPopup(ArrayList<Canzone> songList) {
        if (songList.isEmpty()) {
            JOptionPane.showMessageDialog(AggiungiCanzonePlaylistUI.this, "Nessuna canzone trovata.");
        } else {
            StringBuilder message = new StringBuilder("Lista Canzoni:\n");
            for (Canzone song : songList) {
                message.append(song.getTitoloCanzone()).append(" ").append(song.getAutoreCanzone()).append("\n");
            }

            JTextArea textArea = new JTextArea(message.toString(), 10, 30);
            textArea.setWrapStyleWord(true);
            textArea.setLineWrap(true);
            textArea.setEditable(false);
            textArea.setFont(new Font("Arial", Font.BOLD, 16));

            JScrollPane scrollPane = new JScrollPane(textArea);

            JOptionPane.showMessageDialog(AggiungiCanzonePlaylistUI.this,
                    scrollPane,
                    "Lista Canzoni",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }
    // </editor-fold>
}
