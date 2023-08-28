package ClientES;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class AggiungiCanzonePlaylist extends JFrame {
    private JTextField playlistNameField;
    private JTextField songTitleField;
    private JTextField songAuthorField;

    public void aggiungiCanzoniAPlaylist() {
        setTitle("Aggiungi Canzone alla Playlist");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(4, 2, 10, 10));

        JLabel playlistNameLabel = new JLabel("Nome Playlist:");
        playlistNameField = new JTextField();
        JLabel songTitleLabel = new JLabel("Titolo Canzone:");
        songTitleField = new JTextField();
        JLabel songAuthorLabel = new JLabel("Autore Canzone:");
        songAuthorField = new JTextField();

        add(playlistNameLabel);
        add(playlistNameField);
        add(songTitleLabel);
        add(songTitleField);
        add(songAuthorLabel);
        add(songAuthorField);

        JButton searchButton = new JButton("Ricerca Canzoni");
        JButton submitButton = new JButton("Submit");

        add(searchButton);
        add(submitButton);

        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String title = JOptionPane.showInputDialog(AggiungiCanzonePlaylist.this, "Inserisci il titolo della canzone da cercare:");
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
                    // Chiamare il metodo del Client per aggiungere la canzone alla playlist
                    boolean success = true;//Client.aggiungiCanzoneAPlaylist(playlistName, songTitle, songAuthor);
                    if (success) {
                        JOptionPane.showMessageDialog(AggiungiCanzonePlaylist.this, "Canzone aggiunta alla playlist!");
                    } else {
                        JOptionPane.showMessageDialog(AggiungiCanzonePlaylist.this, "Errore nell'aggiunta della canzone alla playlist.", "Errore", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(AggiungiCanzonePlaylist.this, "Riempi tutti i campi prima di procedere.", "Errore", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
    }

    private void showSongListPopup(ArrayList<Canzone> songList) {
        if (songList.isEmpty()) {
            JOptionPane.showMessageDialog(AggiungiCanzonePlaylist.this, "Nessuna canzone trovata.");
        } else {
            StringBuilder message = new StringBuilder("Lista Canzoni:\n");
            for (Canzone song : songList) {
                message.append(song.getTitoloCanzone()).append(song.getAutoreCanzone()).append("\n");
            }

            JTextArea textArea = new JTextArea(message.toString(), 10, 30);
            textArea.setWrapStyleWord(true);
            textArea.setLineWrap(true);
            textArea.setEditable(false);

            JScrollPane scrollPane = new JScrollPane(textArea);

            JOptionPane.showMessageDialog(AggiungiCanzonePlaylist.this,
                    scrollPane,
                    "Lista Canzoni",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }


}
