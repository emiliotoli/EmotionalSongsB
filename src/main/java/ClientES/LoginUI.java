package ClientES;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginUI extends JFrame {

    private JTextField userIDField;
    private JPasswordField passwordField;

    public void LoginGUI() {
        setTitle("Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 150);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));

        JLabel userIDLabel = new JLabel("User ID:");
        userIDField = new JTextField();
        panel.add(userIDLabel);
        panel.add(userIDField);

        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField();
        panel.add(passwordLabel);
        panel.add(passwordField);

        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                performLogin();
            }
        });
        panel.add(new JLabel());
        panel.add(loginButton);

        add(panel);
    }

    private void performLogin() {
        String userID = userIDField.getText();
        String password = new String(passwordField.getPassword());

        // Esegui il processo di login, ad esempio chiamando i metodi del Client

        // Dopo il login avvenuto con successo, puoi chiudere la finestra di login o fare altre azioni
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                LoginUI loginGUI = new LoginUI();
                loginGUI.setVisible(true);
            }
        });
    }
}

