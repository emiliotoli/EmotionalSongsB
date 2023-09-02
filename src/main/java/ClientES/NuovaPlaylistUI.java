package ClientES;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;

public class NuovaPlaylistUI extends JFrame {
    private JTextField nomePlaylistField;
    private JButton submitButton;
    private Client client;
    private PlaylistCreationCallback callback;

    public void nuovaPlaylistUI(PlaylistCreationCallback callback) {
        this.callback = callback;

        setTitle("Nuova Playlist");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(300, 150);
        setLocationRelativeTo(null);
        setVisible(true);
        JPanel panel = new JPanel(new GridLayout(2, 1, 10, 10));

        nomePlaylistField = new JTextField(15);
        submitButton = new JButton("Crea Playlist");

        panel.add(new JLabel("Nome Playlist:"));
        panel.add(nomePlaylistField);
        panel.add(submitButton);

        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    handleSubmit();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });

        add(panel);
    }

    private void handleSubmit() throws IOException, SQLException {
        String nomePlaylist = nomePlaylistField.getText();
        int isPlaylistCreated = Client.creaPlayList(nomePlaylist, Client.idGlobale);

        callback.onPlaylistCreationResult(isPlaylistCreated);
    }
}
