package ClientES;

import javax.swing.*;
import java.awt.*;

public class RegistrazioneUI extends JFrame {
    private JLabel[] labels; // Array per memorizzare le etichette

    public void registrazione() {

        setTitle("Registrazione");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 600);
        setLocationRelativeTo(null);
        setVisible(true);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);

        labels = new JLabel[11]; // Inizializza l'array

        for (int i = 0; i < 11; i++) {
            labels[i] = new JLabel("Campo " + (i + 1)); // Memorizza l'etichetta nell'array
            gbc.gridx = 0;
            gbc.gridy = i;
            gbc.anchor = GridBagConstraints.EAST;
            panel.add(labels[i], gbc);

            JTextField textField = new JTextField(15);
            gbc.gridx = 1;
            gbc.anchor = GridBagConstraints.WEST;
            panel.add(textField, gbc);
        }

        add(panel);

        labels[0].setText("Nome");
        labels[1].setText("Cognome");
        labels[2].setText("Codice Fiscale");
        labels[3].setText("Indirizzo");
    }

    // Metodo per impostare il testo di un'etichetta dato l'indice
    public void setLabelText(int index, String text) {
        if (index >= 0 && index < labels.length) {

        }
    }

    // Resto del codice...
}
