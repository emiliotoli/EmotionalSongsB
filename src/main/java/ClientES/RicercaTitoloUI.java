package ClientES;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RicercaTitoloUI extends JFrame {
    private JTextField songNameField;
    private JButton submitButton;
    private Client client;

    public void ricercaTitolo() {

        setTitle("Cerca Canzone");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(300, 150);
        setLocationRelativeTo(null);
        setVisible(true);


        JPanel panel = new JPanel(new BorderLayout());

        songNameField = new JTextField(20);
        songNameField.setColumns(15); // Imposta la larghezza della casella di testo a 15 colonne
        GridBagConstraints gbc = new GridBagConstraints();
        submitButton = new JButton("Submit");

        panel.add(songNameField, BorderLayout.CENTER);
        panel.add(submitButton, BorderLayout.SOUTH);

        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handleSubmit();
            }
        });

        add(panel);
    }

    private void handleSubmit() {
        String songName = songNameField.getText();
        String[] matchingSongs = new String[20]; //client.searchSongs(songName);
        matchingSongs[0]= "jjajajaja";
        if (matchingSongs.length > 0) {
            StringBuilder message = new StringBuilder("Canzoni corrispondenti:\n");
            for (String song : matchingSongs) {
                message.append(song).append("\n");
            }

            JTextArea textArea = new JTextArea(message.toString(), 10, 30); // 10 righe, 30 colonne
            textArea.setWrapStyleWord(true);
            textArea.setLineWrap(true);
            textArea.setEditable(false);

            JScrollPane scrollPane = new JScrollPane(textArea);

            JOptionPane.showMessageDialog(this, scrollPane, "Risultati della ricerca", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Nessuna canzone corrispondente trovata.", "Risultati della ricerca", JOptionPane.INFORMATION_MESSAGE);
        }
    }



}
