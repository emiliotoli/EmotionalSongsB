package ClientES;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;

public class EliminaCanzonePlaylistUI extends JFrame {
    private JTextField playlistNameField;
    private JTextField songTitleField;
    private JTextField songAuthorField;
    private int val;

    public void eliminaCanzoneDaPlaylist() {
        setTitle("Elimina canzone dalla Playlist");
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
                String title = JOptionPane.showInputDialog(EliminaCanzonePlaylistUI.this, "Inserisci il titolo della canzone da rimuovere dalla playlist :");
                if (title != null && !title.isEmpty()) {
                    ArrayList<Canzone> songList = null; // Sostituisci con il metodo corretto
                    try {
                        songList = Client.ricercaCanzoneTitoloInPlaylist(Client.idGlobale , songTitleField.getText() , songAuthorField.getText());
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
                        val=Client.eliminaCanzoniPlaylist(playlistName , Client.idGlobale , songTitle , songAuthor);
                    } catch (RemoteException | SQLException remoteException) {
                        remoteException.printStackTrace();
                    }
                    switch (val) {
                        case 0 -> JOptionPane.showMessageDialog(EliminaCanzonePlaylistUI.this, "Canzone rimossa con successo dalla playlist", "Successo", JOptionPane.INFORMATION_MESSAGE);
                        case -1 -> JOptionPane.showMessageDialog(EliminaCanzonePlaylistUI.this, "Nome playlist inesistente", "Errore", JOptionPane.ERROR_MESSAGE);
                        case 1 -> JOptionPane.showMessageDialog(EliminaCanzonePlaylistUI.this, "Errore nella rimozione della canzone al database. Riprova", "Errore", JOptionPane.ERROR_MESSAGE);
                        case -2 -> JOptionPane.showMessageDialog(EliminaCanzonePlaylistUI.this, "Errore nel collegamento al server. Riprova", "Errore", JOptionPane.ERROR_MESSAGE);
                        case -3 -> JOptionPane.showMessageDialog(EliminaCanzonePlaylistUI.this, "Errore nel inserimento dei dati della canzone. Titolo o autore errati", "Errore", JOptionPane.ERROR_MESSAGE);
                    }


                } else {
                    JOptionPane.showMessageDialog(EliminaCanzonePlaylistUI.this, "Riempi tutti i campi prima di procedere", "Errore", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
    }

    private void showSongListPopup(ArrayList<Canzone> songList) {
        if (songList.isEmpty()) {
            JOptionPane.showMessageDialog(EliminaCanzonePlaylistUI.this, "Nessuna canzone trovata.");
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

            JOptionPane.showMessageDialog(EliminaCanzonePlaylistUI.this,
                    scrollPane,
                    "Lista Canzoni",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

}
