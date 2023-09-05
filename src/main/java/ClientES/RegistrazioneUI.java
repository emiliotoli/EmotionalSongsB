package ClientES;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.rmi.NotBoundException;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author Emilio Toli
 *         Classe che gestisce l'interfaccia utente per la registrazione
 *         di un nuovo user
 *
 */

public class RegistrazioneUI extends JFrame {
    // <editor-fold desc= "Attributi">
    private JTextField[] textFields; // Array per memorizzare le caselle di testo
    private RegistrazioneCallback callback;
    int val;
    // </editor-fold>

    /**
     * @author Emilio Toli
     * @param callback
     *                 Metodo per la creazione della UI e per la gestione
     *                 della chiamata del metodo privato associato al pulsante
     */
    // <editor-fold desc= "Registrazione">
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
            JLabel label = GraphicUtils.createLabels(labelNames[i]);
            gbc.gridy = i;
            panel.add(label, gbc);

            gbc.gridx = 1;
            gbc.anchor = GridBagConstraints.WEST;

            JTextField textField = GraphicUtils.createTextFields(20);
            textFields[i] = textField; // Memorizza la casella di testo nell'array
            panel.add(textField, gbc);
            gbc.gridx = 0;
        }
        JButton submitButton = GraphicUtils.createButtons("Registrazione");
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
    // </editor-fold>

    /**
     * @author Emilio Toli
     * @throws IOException
     * @throws SQLException
     * @throws NotBoundException
     *
     *                           Metodo privato chiamato al click del pulsante
     *                           Chiama il metodo della classe client per effettuare
     *                           la registrazione
     */
    // <editor-fold desc= "Invio dati registrazione">
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
    // </editor-fold>
}
