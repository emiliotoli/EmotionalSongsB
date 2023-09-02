package ClientES;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.rmi.NotBoundException;
import java.sql.SQLException;
import java.util.ArrayList;

public class RegistrazioneUI extends JFrame {
    private JTextField[] textFields; // Array per memorizzare le caselle di testo
    private RegistrazioneCallback callback;
    int val;

    public void registrazioneUI(RegistrazioneCallback callback) {
        this.callback = callback;

        setTitle("Registrazione");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 600);
        setLocationRelativeTo(null);
        setVisible(true);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);

        String[] labelNames = {
                "Nome", "Cognome", "Codice Fiscale", "Indirizzo", "Numero civico",
                "CAP", "Comune", "Provincia", "Email", "Nome Utente", "PASSWORD", "Reinserisci password"
        };

        textFields = new JTextField[labelNames.length]; // Inizializza l'array

        gbc.anchor = GridBagConstraints.EAST;
        gbc.gridx = 0;

        for (int i = 0; i < labelNames.length; i++) {
            JLabel label = new JLabel(labelNames[i]);
            gbc.gridy = i;
            panel.add(label, gbc);

            gbc.gridx = 1;
            gbc.anchor = GridBagConstraints.WEST;

            JTextField textField = new JTextField(15);
            textFields[i] = textField; // Memorizza la casella di testo nell'array
            panel.add(textField, gbc);
            gbc.gridx = 0;
        }
        JButton submitButton = new JButton("Submit");
        gbc.gridy = labelNames.length;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.PAGE_END;
        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    handleSubmit();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (NotBoundException notBoundException) {
                    notBoundException.printStackTrace();
                }
            }
        });
        panel.add(submitButton, gbc);

        add(panel);
    }

    private void handleSubmit() throws IOException, SQLException, NotBoundException {

        String nome = textFields[0].getText();
        String cognome = textFields[1].getText();
        String codiceFiscale = textFields[2].getText();
        String indirizzo = textFields[3].getText();
        String numeroCivico = textFields[4].getText();
        String cap = textFields[5].getText();
        String comune = textFields[6].getText();
        String provincia = textFields[7].getText();
        String email = textFields[8].getText();
        String nomeUtente = textFields[9].getText();
        String password = textFields[10].getText();
        String password2 = textFields[11].getText();
        int val = Client.registrazione(nome, cognome, codiceFiscale, indirizzo, numeroCivico, cap, comune, provincia,
                email, nomeUtente, password, password2);
        // dispose();
        if (callback != null) {
            callback.registrazioneCompleted(val);
        }

    }

}
