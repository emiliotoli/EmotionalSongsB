package ClientES;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;

public class VisualizzaCanzoniPlaylist extends JFrame {
    private JTextField playlistNameField;
    private JTextField songTitleField;
    private JTextField songAuthorField;
    private int val;

    public void visualizzaCanzoni() {
        setTitle("Elimina canzone dalla Playlist");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(4, 2, 10, 10));
        setVisible(true);

        JLabel playlistNameLabel = new JLabel("Nome Playlist:");
        playlistNameField = new JTextField();

        add(playlistNameLabel);
        add(playlistNameField);

        JButton searchButton = new JButton("Ricerca Canzoni");
        //JButton submitButton = new JButton("Submit");

        add(searchButton);
        //add(submitButton);

        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String title = JOptionPane.showInputDialog(VisualizzaCanzoniPlaylist.this, "Inserisci il titolo della canzone da rimuovere dalla playlist :");
                if (title != null && !title.isEmpty()) {
                    ArrayList<Canzone> songList = null; // Sostituisci con il metodo corretto
                    try {
                        songList = Client.visualizzaCanzoniPlaylist(Client.idGlobale , playlistNameField.getText());
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                    showSongListPopup(songList , playlistNameField.getText());
                }
            }
        });

        /*submitButton.addActionListener(new ActionListener() {
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
                        case 0 -> JOptionPane.showMessageDialog(VisualizzaCanzoniPlaylist.this, "Canzone rimossa con successo dalla playlist", "Successo", JOptionPane.INFORMATION_MESSAGE);
                        case -1 -> JOptionPane.showMessageDialog(VisualizzaCanzoniPlaylist.this, "Nome playlist inesistente", "Errore", JOptionPane.ERROR_MESSAGE);
                        case 1 -> JOptionPane.showMessageDialog(VisualizzaCanzoniPlaylist.this, "Errore nella rimozione della canzone al database. Riprova", "Errore", JOptionPane.ERROR_MESSAGE);
                        case -2 -> JOptionPane.showMessageDialog(VisualizzaCanzoniPlaylist.this, "Errore nel collegamento al server. Riprova", "Errore", JOptionPane.ERROR_MESSAGE);
                    }


                } else {
                    JOptionPane.showMessageDialog(VisualizzaCanzoniPlaylist.this, "Riempi tutti i campi prima di procedere", "Errore", JOptionPane.WARNING_MESSAGE);
                }
            }
        });*/
    }

    private void showSongListPopup(ArrayList<Canzone> songList , String nomePlaylist) {
        if (songList.isEmpty()) {
            JOptionPane.showMessageDialog(VisualizzaCanzoniPlaylist.this, "Nessuna canzone trovata.");
        } else {
            StringBuilder message = new StringBuilder("Lista Canzoni nella playlist "+ nomePlaylist + ":\n");
            for (Canzone song : songList) {
                message.append(song.getTitoloCanzone()).append(" ").append(song.getAutoreCanzone()).append("\n");
            }

            JTextArea textArea = new JTextArea(message.toString(), 10, 30);
            textArea.setWrapStyleWord(true);
            textArea.setLineWrap(true);
            textArea.setEditable(false);

            JScrollPane scrollPane = new JScrollPane(textArea);

            JOptionPane.showMessageDialog(VisualizzaCanzoniPlaylist.this,
                    scrollPane,
                    "Lista Canzoni",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }


}
