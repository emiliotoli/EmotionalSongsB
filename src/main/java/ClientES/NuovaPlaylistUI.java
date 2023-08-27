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

    public void nuovaPlaylist() {

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
        int isPlaylistCreated = Client.creaPlayList(nomePlaylist);

        if (isPlaylistCreated == 0) {
            dispose();
        } else {
            if(isPlaylistCreated==1)
                JOptionPane.showMessageDialog(this, "Playlist già esistente", "Errore", JOptionPane.ERROR_MESSAGE);
            else
                JOptionPane.showMessageDialog(this, "Playlist non creata correttamente", "Errore2", JOptionPane.ERROR_MESSAGE);
        }
    }
}

