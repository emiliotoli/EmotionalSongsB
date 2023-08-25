package ClientES;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RicercaAutoreAnnoUI extends JFrame {
    private JTextField autoreField;
    private JTextField annoField;
    private JButton submitButton;

    public void ricercaCanzone() {


        setTitle("Ricerca Canzone per Autore e Anno");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(300, 150);
        setLocationRelativeTo(null);
        setVisible(true);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);

        autoreField = new JTextField(15);
        annoField = new JTextField(15);
        submitButton = new JButton("Cerca");

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(new JLabel("Autore:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(autoreField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(new JLabel("Anno:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(annoField, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(submitButton, gbc);

        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handleSearch();
            }
        });

        add(panel);
    }

    // Resto del codice...

    private void handleSearch() {
        String autore = autoreField.getText();
        int anno = Integer.parseInt(annoField.getText()); // Assumendo che l'anno sia un intero

        //Song[] matchingSongs = client.searchSongsByAuthorAndYear(autore, anno);

        // Puoi gestire i risultati come preferisci
        // Ad esempio, puoi visualizzarli in una nuova finestra o in un elenco
    }

}





