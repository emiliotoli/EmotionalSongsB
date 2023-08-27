package ClientES;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.rmi.RemoteException;
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
        songNameField.setColumns(10);
        songNameField.setPreferredSize(new Dimension(100, 5));

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
        ArrayList<Canzone> canzoni = (ArrayList<Canzone>) ClientBridge.getInterfaceNonLoggato().ricercaCanzoneTitolo(songName);

        if (!canzoni.isEmpty()) {
            StringBuilder message = new StringBuilder("Canzoni corrispondenti:\n");
            for (Canzone c : canzoni) {
                message.append(c.getTitoloCanzone()).append(" | ").append(c.getAutoreCanzone()).append(" | ").append(c.getAnnoCanzone()).append("\n");
            }

            JTextArea textArea = new JTextArea(message.toString(), 10, 30);
            textArea.setWrapStyleWord(true);
            textArea.setLineWrap(true);
            textArea.setEditable(false);


            JScrollPane scrollPane = new JScrollPane(textArea);
            JButton visualizzaEmozioniButton = new JButton("Visualizza Emozioni");

            visualizzaEmozioniButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    try {
                        handleVisualizzaEmozioni();
                    } catch (RemoteException remoteException) {
                        remoteException.printStackTrace();
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }
            });

            JPanel buttonPanel = new JPanel();
            buttonPanel.add(visualizzaEmozioniButton);

            JPanel mainPanel = new JPanel(new BorderLayout());
            mainPanel.add(scrollPane, BorderLayout.CENTER);
            mainPanel.add(buttonPanel, BorderLayout.SOUTH);

            JOptionPane.showMessageDialog(this, mainPanel, "Risultati della ricerca", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Nessuna canzone corrispondente trovata.", "Risultati della ricerca", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void handleVisualizzaEmozioni() throws RemoteException, SQLException {
        JTextField titoloField = new JTextField(20);
        JTextField autoreField = new JTextField(20);

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(2, 2));
        inputPanel.add(new JLabel("Titolo:"));
        inputPanel.add(titoloField);
        inputPanel.add(new JLabel("Autore:"));
        inputPanel.add(autoreField);

        int result = JOptionPane.showConfirmDialog(this, inputPanel, "Inserisci Titolo e Autore",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            String titolo = titoloField.getText();
            String autore = autoreField.getText();

            ArrayList<Emozione> emozioni = Client.visualizzaEmozioniCanzone(titolo, autore);

            if (!emozioni.isEmpty()) {
                StringBuilder emozioniMessage = new StringBuilder("Emozioni corrispondenti:\n");
                for (Emozione e : emozioni) {
                    emozioniMessage.append(e.getNomeEmozione()).append("\n").append(e.getPercentualeEmozione()).append("\n");
                }

                JOptionPane.showMessageDialog(this, emozioniMessage.toString(), "Emozioni", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Nessuna emozione corrispondente trovata.", "Emozioni", JOptionPane.INFORMATION_MESSAGE);
            }
        }

    }
}
