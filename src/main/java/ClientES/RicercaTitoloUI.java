package ClientES;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class RicercaTitoloUI extends JFrame {
    private JTextField songNameField;
    private JButton submitButton;

    public void ricercaTitolo() {

        setTitle("Cerca Canzone");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(300, 150);
        setLocationRelativeTo(null);
        setVisible(true);


        JPanel panel = new JPanel(new BorderLayout());

        songNameField = new JTextField(20);
        songNameField.setColumns(10); // Imposta la larghezza della casella di testo a 10 colonne
        songNameField.setPreferredSize(new Dimension(100, 5)); // Imposta le dimensioni della casella di testo (larghezza x altezza)
        GridBagConstraints gbc = new GridBagConstraints();
        submitButton = new JButton("Submit");

        panel.add(songNameField, BorderLayout.CENTER);
        panel.add(submitButton, BorderLayout.SOUTH);

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
        String songName = songNameField.getText();
       // ArrayList<Canzone> canzoni = new ArrayList<Canzone>(); //client.searchSongs(songName);
        ArrayList<Canzone> canzoni = Client.RicercaCanzoniTitolo(songName);
        if (!canzoni.isEmpty()) {
            StringBuilder message = new StringBuilder("Canzoni corrispondenti:\n");
            for (Canzone c : canzoni) {
                message.append(c.getTitoloCanzone()).append("\n");
                message.append(c.getAutoreCanzone()).append("\n");
                message.append(c.getAnnoCanzone()).append("\n");
            }

            JTextArea textArea = new JTextArea(message.toString(), 10, 30); // 10 righe, 30 colonne
            textArea.setWrapStyleWord(true);
            textArea.setLineWrap(true);
            textArea.setEditable(false);

            JScrollPane scrollPane = new JScrollPane(textArea);
            JList<Canzone> songsList = new JList<>(canzoni.toArray(new Canzone[0]));
            songsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

            int option = JOptionPane.showOptionDialog(this, scrollPane, "Risultati della ricerca", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);
            if (option == JOptionPane.OK_OPTION && !(canzoni.isEmpty())) {
                Canzone selectedSong = songsList.getSelectedValue();
                handleSongSelection(selectedSong);
            }
            JOptionPane.showMessageDialog(this, scrollPane, "Risultati della ricerca", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Nessuna canzone corrispondente trovata.", "Risultati della ricerca", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void handleSongSelection(Canzone selectedSong) {
        String titolo = selectedSong.getTitoloCanzone();
        String autore = selectedSong.getAutoreCanzone();
        int anno = selectedSong.getAnnoCanzone();
    }


}
