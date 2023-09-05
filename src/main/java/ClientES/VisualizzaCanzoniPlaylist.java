package ClientES;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author Emilio Toli
 *         Classe che gestisce la UI che permette di visualizzare le canzoni
 *         presenti in una playlist
 */

public class VisualizzaCanzoniPlaylist extends JFrame {
    // <editor-fold desc= "Attributi">
    private JTextField playlistNameField;
    private JTextField songTitleField;
    private JTextField songAuthorField;
    private int val;
    private ArrayList<Canzone> songList;
    // </editor-fold>

    /**
     * @author Emilio Toli
     *         Metodo che crea la UI e che alla fine chiama il metodo per mostrare
     *         le canzoni
     */
    // <editor-fold desc= "Visualizza canzoni">
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
        // JButton submitButton = new JButton("Submit");

        add(searchButton);
        // add(submitButton);

        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    songList = Client.visualizzaCanzoniPlaylist(Client.idGlobale, playlistNameField.getText());
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                showSongListPopup(songList, playlistNameField.getText());
            }

        });
    }
    // </editor-fold>

    /**
     * @author Emilio Toli
     * @param songList
     * @param nomePlaylist
     *                     Metodo che permette di mostrare le canzoni di una
     *                     playlist aprendo un popup
     */

    // <editor-fold desc= "Apertura popup e visualizzazione canzoni">
    private void showSongListPopup(ArrayList<Canzone> songList, String nomePlaylist) {
        if (songList == null) {
            JOptionPane.showMessageDialog(VisualizzaCanzoniPlaylist.this, "Nome Playlist errato!.");
        }
        if (songList.isEmpty()) {
            JOptionPane.showMessageDialog(VisualizzaCanzoniPlaylist.this, "Playlist vuota.");
        } else {
            StringBuilder message = new StringBuilder("Lista Canzoni nella playlist " + nomePlaylist + ":\n");
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
    // </editor-fold>

}
