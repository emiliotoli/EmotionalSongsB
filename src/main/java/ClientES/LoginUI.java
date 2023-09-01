package ClientES;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class LoginUI extends JFrame {

    private JTextField userIDField;
    private JPasswordField passwordField;
    boolean valLogin;

    public void LoginGUI(LoginCallback callback) {
        setTitle("Login");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(300, 150);
        setLocationRelativeTo(null);
        setVisible(true);
        setSize(400 , 200);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets= new Insets(5,10,5,10);

        JLabel userIDLabel = new JLabel("User ID:");
        userIDField = new JTextField(20);
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(userIDLabel, gbc);
        gbc.gridx = 1;
        panel.add(userIDField, gbc);

        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField(20);
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(passwordLabel, gbc);
        gbc.gridx = 1;
        panel.add(passwordField, gbc);

        JButton loginButton = new JButton("Login");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER; // Ancoraggio al centro

        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    boolean valLogin = performLogin();
                    if(valLogin){
                        dispose();// Login riuscito, chiudi la finestra di login
                    }
                     else {
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

    private boolean performLogin() throws IOException {
        String userID = userIDField.getText();
        String password = new String(passwordField.getPassword());
        // Esegui il processo di login, ad esempio chiamando i metodi del Client
        return Client.login(userID, password);
    }
}

