package ClientES;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;

public class AggiungiCanzonePlaylistUI extends JFrame {
    private JTextField playlistNameField;
    private JTextField songTitleField;
    private JTextField songAuthorField;
    private int val;

    public void aggiungiCanzoniAPlaylist() {
        setTitle("Aggiungi Canzone alla Playlist");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(4, 2, 10, 10));
        setVisible(true);

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
                String title = JOptionPane.showInputDialog(AggiungiCanzonePlaylistUI.this, "Inserisci il titolo della canzone da cercare:");
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
                        val= Client.aggiuntaCanzoniPlaylist(playlistName , Client.idGlobale , songTitle , songAuthor);
                    } catch (RemoteException remoteException) {
                        remoteException.printStackTrace();
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                    switch (val) {
                        case 0 -> JOptionPane.showMessageDialog(AggiungiCanzonePlaylistUI.this, "Canzone aggiunta con successo dalla playlist", "Successo", JOptionPane.INFORMATION_MESSAGE);
                        case -1 -> JOptionPane.showMessageDialog(AggiungiCanzonePlaylistUI.this, "Nome playlist inesistente", "Errore", JOptionPane.ERROR_MESSAGE);
                        case 1 -> JOptionPane.showMessageDialog(AggiungiCanzonePlaylistUI.this, "Errore nell'aggiunta della canzone al database. Riprova", "Errore", JOptionPane.ERROR_MESSAGE);
                        case -2 -> JOptionPane.showMessageDialog(AggiungiCanzonePlaylistUI.this, "Errore nel collegamento al server. Riprova", "Errore", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(AggiungiCanzonePlaylistUI.this, "Riempi tutti i campi prima di procedere.", "Errore", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
    }

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

            JScrollPane scrollPane = new JScrollPane(textArea);

            JOptionPane.showMessageDialog(AggiungiCanzonePlaylistUI.this,
                    scrollPane,
                    "Lista Canzoni",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }


}
