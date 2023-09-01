package ClientES;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;

public class VisualizzaPlaylistUI extends JFrame {
    private JTextArea playlistTextArea;

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
                } catch (RemoteException remoteException) {
                    remoteException.printStackTrace();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                updatePlaylistTextArea(playlist);
            }
        });
    }

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

}
