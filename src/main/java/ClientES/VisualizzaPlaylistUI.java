package ClientES;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author Emilio Toli
 *         Classe che gestisce la visualizzazione
 *         delle playlist associate ad un utente
 */

public class VisualizzaPlaylistUI extends JFrame {
    // <editor-fold desc= "Attributi">
    private JTextArea playlistTextArea;
    // </editor-fold>

    /**
     * @author Emilio Toli
     *         Metodo per visualizzare le playlist dato il nome
     *         della playlist inserito in un'area di testo
     */
    // <editor-fold desc= "Visualizzazione a video delle playlist">
    public void visualizzaPlaylist() {
        setTitle("Visualizza Playlist");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);
        setVisible(true);
        setLayout(new BorderLayout());

        playlistTextArea = new JTextArea();
        playlistTextArea.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(playlistTextArea);
        add(scrollPane, BorderLayout.CENTER);

        JButton viewButton = new JButton("Visualizza Playlist");
        add(viewButton, BorderLayout.SOUTH);

        viewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ArrayList<PlayList> playlist = null; // Sostituisci con il metodo corretto
                try {
                    playlist = Client.visualizzaPlaylist(Client.idGlobale);
                } catch (RemoteException | SQLException remoteException) {
                    remoteException.printStackTrace();
                }
                updatePlaylistTextArea(playlist);
            }
        });
    }
    // </editor-fold>

    /**
     * @author Emilio Toli
     * @param playlist Lista che contiene tutte le playlist dell'utente
     *
     *                 Metodo per visualizzare le playlist dell'utente una volta
     *                 premuto il pulsante
     */

    // <editor-fold desc= "Visualizza playlist">
    private void updatePlaylistTextArea(ArrayList<PlayList> playlist) {
        playlistTextArea.setText("");
        if (playlist.isEmpty()) {
            playlistTextArea.append("Nessuna playlist disponibile.");
        } else {
            for (PlayList pl : playlist) {
                playlistTextArea.append(pl.getnomePlalist() + "\n");
            }
        }
    }
    // </editor-fold>
}
