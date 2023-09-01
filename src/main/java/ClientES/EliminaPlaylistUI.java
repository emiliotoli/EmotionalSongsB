package ClientES;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;

public class EliminaPlaylistUI extends JFrame {
    private JTextField nomePlaylistField;
    private JButton submitButton;
    private Client client;
    private PlaylistCreationCallback callback;
    boolean res;

    public boolean eliminaPlaylistUI() {

        setTitle("Elimina Playlist");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(300, 150);
        setLocationRelativeTo(null);
        setVisible(true);
        JPanel panel = new JPanel(new GridLayout(2, 1, 10, 10));

        nomePlaylistField = new JTextField(15);
        submitButton = new JButton("Elimina Playlist");

        panel.add(new JLabel("Nome Playlist:"));
        panel.add(nomePlaylistField);
        panel.add(submitButton);

        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    res = handleSubmit();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });

        add(panel);
        return res;
    }

    private boolean handleSubmit() throws IOException, SQLException {
        boolean res;
        String nomePlaylist = nomePlaylistField.getText();
        res= Client.eliminaPlaylist(nomePlaylist);
        return res;
    }
}