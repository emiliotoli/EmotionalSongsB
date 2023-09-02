package ClientES;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;

public class RicercaTitoloUI extends JFrame {
    private JTextField songNameField;
    private static final Color textColor = new Color(76, 79, 105);
    private static final Color background = new Color(204, 208, 218);

    public void ricercaTitolo() {
        setTitle("Cerca Canzone");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(300, 150);
        setLocationRelativeTo(null);
        setVisible(true);

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(background);

        songNameField = new JTextField(20);
        songNameField.setColumns(10);
        songNameField.setPreferredSize(new Dimension(100, 5));
        songNameField.setFont(new Font("Arial", Font.BOLD, 17));
        songNameField.setForeground(textColor);

        JButton submitButton = new JButton("Ricerca");
        createButtons(submitButton);

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
        ArrayList<Canzone> canzoni = (ArrayList<Canzone>) Client.RicercaCanzoniTitolo(songName);

        if (!canzoni.isEmpty()) {
            StringBuilder message = new StringBuilder(" Canzoni corrispondenti:\n");
            for (Canzone c : canzoni) {
                message.append(c.getTitoloCanzone()).append(" | ").append(c.getAutoreCanzone()).append(" | ")
                        .append(c.getAnnoCanzone()).append("\n");
            }

            JTextArea textArea = new JTextArea(message.toString(), 10, 30);
            textArea.setWrapStyleWord(true);
            textArea.setLineWrap(true);
            textArea.setEditable(false);

            JScrollPane scrollPane = new JScrollPane(textArea);
            JButton visualizzaEmozioniButton = new JButton("Visualizza Emozioni");
            createButtons(visualizzaEmozioniButton);
            JButton insertEmotionsButton = new JButton("Inserisci Emozioni");
            createButtons(insertEmotionsButton);
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

            insertEmotionsButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    try {
                        handleInsertEmotions();
                    } catch (RemoteException remoteException) {
                        remoteException.printStackTrace();
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }
            });

            JPanel buttonPanel = new JPanel();
            buttonPanel.add(visualizzaEmozioniButton);
            buttonPanel.add(insertEmotionsButton);
            JPanel mainPanel = new JPanel(new BorderLayout());
            mainPanel.add(scrollPane, BorderLayout.CENTER);
            mainPanel.add(buttonPanel, BorderLayout.SOUTH);

            JOptionPane.showMessageDialog(this, mainPanel, "Risultati della ricerca", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Nessuna canzone corrispondente trovata.", "Risultati della ricerca",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void handleInsertEmotions() throws RemoteException, SQLException {
        JPanel insertEmotion = new JPanel();
        insertEmotion.setLayout(new GridLayout(3, 2));
        JTextField emotionIntensityField = new JTextField(20);

        insertEmotion.setSize(500, 500);
        insertEmotion.setLocation(430, 100);

        String[] intensities = { "1", "2", "3", "4", "5" };
        String[] emotions = { "Rabbia", "Felicità", "Tristezza", "Paura", "Sorpresa" };
        final JComboBox<String> intensityComboBox = new JComboBox<>(intensities);
        final JComboBox<String> emotionComboBox = new JComboBox<>(emotions);

        insertEmotion.add(new JLabel("Emotion Intensity:"));
        insertEmotion.add(intensityComboBox);
        insertEmotion.add(new JLabel("Seleziona Emozione:"));
        insertEmotion.add(emotionComboBox);

        int result = JOptionPane.showConfirmDialog(this, insertEmotion, "Insert Emotions",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {

            // here there are the results of the dropdown menu
            String selectedIntensity = (String) emotionComboBox.getSelectedItem();
            String selectedEmotion = (String) emotionComboBox.getSelectedItem();


        }
    }

    private void handleVisualizzaEmozioni() throws RemoteException, SQLException {
        JTextField titoloField = new JTextField(20);
        JTextField autoreField = new JTextField(20);

        JPanel inputPanel = new JPanel();
        inputPanel.setBackground(background);

        inputPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
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
                    emozioniMessage.append(e.getNomeEmozione()).append("\n").append(e.getPercentualeEmozione())
                            .append("\n");
                }

                JOptionPane.showMessageDialog(this, emozioniMessage.toString(), "Emozioni",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Nessuna emozione corrispondente trovata.", "Emozioni",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        }

    }

    public static void createButtons(JButton bt) {
        Color borderColor = new Color(4, 165, 229);
        int fontTextSize = 16;

        bt.setPreferredSize(new Dimension(250, 50));
        bt.setBorder(new LineBorder(borderColor, 1));
        bt.setFont(new Font("Arial", Font.BOLD, fontTextSize));
        bt.setForeground(textColor);
    };
}
