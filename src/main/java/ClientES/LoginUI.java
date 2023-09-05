package ClientES;

import javax.swing.*;
import javax.swing.border.LineBorder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * @author Emilio Toli
 *         Classe che gestisce l'interfaccia utente per il login degli
 *         utilizzatori
 */

public class LoginUI extends JFrame {
    // <editor-fold desc= "Attributi">
    private JTextField userIDField;
    private JPasswordField passwordField;
    boolean valLogin;
    // </editor-fold>

    /**
     * @author Emilio Toli
     * @param callback
     *                 Metodo che crea l'interfaccia utente e che richiama un metodo
     *                 privato che effettuerà il login. Gestirà l'eventualità che il
     *                 login non
     *                 vada a buon fine
     */
    // <editor-fold desc= "Gestione login">
    public void LoginGUI(LoginCallback callback) {
        setTitle("Login");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        setSize(450, 200);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);

        JLabel userIDLabel = GraphicUtils.createLabels("Nome Utente:");
        userIDField = GraphicUtils.createTextFields(20);
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(userIDLabel, gbc);
        gbc.gridx = 1;
        panel.add(userIDField, gbc);

        JLabel passwordLabel = GraphicUtils.createLabels("Password:");
        passwordField = new JPasswordField(20);
        passwordField.setForeground(new Color(76, 79, 105));
        passwordField.setFont(new Font("Arial", Font.BOLD, 16));
        passwordField.setPreferredSize(new Dimension(100, 25));

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(passwordLabel, gbc);
        gbc.gridx = 1;
        panel.add(passwordField, gbc);

        JButton loginButton = GraphicUtils.createButtons("Login");
        loginButton.setPreferredSize(new Dimension(250, 50));
        loginButton.setBorder(new LineBorder(new Color(76, 79, 105), 1));
        loginButton.setFont(new Font("Arial", Font.BOLD, 16));

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER; // Ancoraggio al centro

        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    boolean valLogin = performLogin();
                    if (valLogin) {
                        dispose();// Login riuscito, chiudi la finestra di login
                    } else {
                        // Login non riuscito, reimposta i campi di input
                        userIDField.setText("");
                        passwordField.setText("");
                    }
                    callback.onLoginResult(valLogin);

                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        panel.add(loginButton, gbc);
        add(panel);
    }
    // </editor-fold>

    /**
     * @author Emilio Toli
     * @return Viene ritornato un valore booleano per stabilire se il login ha avuto
     *         esito positivo
     * @throws IOException
     *                     Metodo per effettuare il login chiamando i metodi della
     *                     classe Client
     *                     una volta che viene premuto il pulsante submit
     */
    // <editor-fold desc= "Gestione evento click e Login">
    private boolean performLogin() throws IOException {
        String userID = userIDField.getText();
        String password = new String(passwordField.getPassword());
        return Client.login(userID, password);
    }
    // </editor-fold>
}
