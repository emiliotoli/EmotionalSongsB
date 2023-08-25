package ClientES;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegistrazioneUI extends JFrame {
    private JLabel[] labels; // Array per memorizzare le etichette
    private JButton submitButton;
    private JTextField userIDField;
    private JPasswordField passwordField;
    private int controlloErrore;

    public int registrazione() {

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
        labels[4].setText("Numero civico");
        labels[5].setText("CAP");
        labels[6].setText("Comune");
        labels[7].setText("Provincia");
        labels[8].setText("Email");
        labels[9].setText("Nome Utente");
        labels[10].setText("PASSWORD");

        JButton loginButton = new JButton("Login");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.PAGE_END; // Ancoraggio al centro

        submitButton = new JButton("Submit"); // Crea il pulsante di submit
        gbc.gridx = 0;
        gbc.gridy = 11; // Posiziona il pulsante sotto le caselle di testo
        gbc.gridwidth = 2; // Larghezza di due colonne
        gbc.anchor = GridBagConstraints.PAGE_END; // Ancoraggio in basso
        panel.add(submitButton, gbc);

        add(panel);

        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controlloErrore= handleSubmit(); // Chiamato quando il pulsante di submit è premuto
            }
        });
        return controlloErrore;
    }

    private int handleSubmit(){
        //Controllo con if se la registrazione è andata a buon fine con codice 0. altrimenti metti alert su errore!
        return 0;
    }




    // Resto del codice...
}
