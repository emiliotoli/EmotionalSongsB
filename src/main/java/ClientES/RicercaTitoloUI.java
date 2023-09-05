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

    public void ricercaTitolo() {
        setTitle("Cerca Canzone");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(300, 150);
        setLocationRelativeTo(null);
        setVisible(true);

        JPanel panel = new JPanel(new BorderLayout());

        songNameField = GraphicUtils.createTextFields(20);

        JButton submitButton = GraphicUtils.createButtons("Ricerca");

        panel.add(songNameField, BorderLayout.CENTER);
        panel.add(submitButton, BorderLayout.SOUTH);

        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    UIManager.put("OptionPane.buttonFont", new Font("Arial", Font.BOLD, 16)); // Imposta il tipo di
                                                                                              // carattere e la
                                                                                              // dimensione del pulsante
                                                                                              // "OK"
                    UIManager.put("OptionPane.buttonSize", new Font("Arial", Font.BOLD, 16));
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

    /**
     * @author Emilio Toli
     * @throws IOException
     * @throws SQLException
     * 
     *                      metodo che gestisce il pulsante di inserimento
     */
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
            textArea.setFont(new Font("Arial", Font.BOLD, 16));

            JScrollPane scrollPane = new JScrollPane(textArea);
            JButton visualizzaEmozioniButton = GraphicUtils.createButtons("Visualizza Emozioni");
            JButton insertEmotionsButton = GraphicUtils.createButtons("Inserisci Emozioni");
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
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            });

            JPanel buttonPanel = new JPanel();
            buttonPanel.add(visualizzaEmozioniButton);
            if (Client.isLoggato) {
                buttonPanel.add(insertEmotionsButton);
            }

            JPanel mainPanel = new JPanel(new BorderLayout());
            mainPanel.add(scrollPane, BorderLayout.CENTER);
            mainPanel.add(buttonPanel, BorderLayout.SOUTH);

            JOptionPane.showMessageDialog(this, mainPanel, "Risultati della ricerca", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Nessuna canzone corrispondente trovata.", "Risultati della ricerca",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**
     * @author Crisian Stinga
     * @throws IOException
     * @throws SQLException
     * 
     *                      metodo che gestisce il pulsante di inserimento delle
     *                      emozione
     */

    private void handleInsertEmotions() throws IOException, SQLException {

        boolean datiIncompleti = true;

        while (datiIncompleti) {
            JTextField titoloField = GraphicUtils.createTextFields(20);
            JTextField autoreField = GraphicUtils.createTextFields(20);
            JTextField notaEmozione = GraphicUtils.createTextFields(50);
            JTextField spiegazioneEmozione = GraphicUtils.createTextFields(50);
            JTextField intensitaEmozione = GraphicUtils.createTextFields(20);

            JPanel insertEmotion = new JPanel();
            insertEmotion.setLayout(new GridLayout(6, 2));
            insertEmotion.setSize(500, 500);
            insertEmotion.setLocation(430, 100);

            String[] emotions = { "Amazement", "Solemnity", "Tenderness", "Nostalgia", "Calmness", "Power", "Joy",
                    "Tension", "Sadness" };
            final JComboBox<String> emotionComboBox = new JComboBox<>(emotions);
            emotionComboBox.setForeground(new Color(76, 79, 105));
            emotionComboBox.setFont(new Font("Arial", Font.BOLD, 16));

            insertEmotion.add(GraphicUtils.createLabels("Inserisci titolo: "));
            insertEmotion.add(titoloField);
            insertEmotion.add(GraphicUtils.createLabels("Autore: "));
            insertEmotion.add(autoreField);
            insertEmotion.add(GraphicUtils.createLabels("Seleziona Emozione: "));
            insertEmotion.add(emotionComboBox);
            insertEmotion.add(GraphicUtils.createLabels("Intensità Emozione: "));
            insertEmotion.add(intensitaEmozione);
            insertEmotion.add(GraphicUtils.createLabels("Nota Emozione: "));
            insertEmotion.add(notaEmozione);
            insertEmotion.add(GraphicUtils.createLabels("Spiegazione Emozione: "));
            insertEmotion.add(spiegazioneEmozione);

            int result = JOptionPane.showConfirmDialog(this, insertEmotion, "Inserisci Emozioni",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            if (result == JOptionPane.OK_OPTION) {
                String selectedEmotion = (String) emotionComboBox.getSelectedItem();
                String titolo = titoloField.getText();
                String autore = autoreField.getText();
                String nota = notaEmozione.getText();
                String spiegazione = spiegazioneEmozione.getText();
                String intensitaStr = intensitaEmozione.getText();

                if (titolo.isEmpty() || autore.isEmpty() || nota.isEmpty() || spiegazione.isEmpty()
                        || intensitaStr.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Tutti i campi devono essere completati.", "Errore",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (!isValidInt(intensitaStr)) {
                    JOptionPane.showMessageDialog(this, "Intensità emozione deve essere un numero intero.", "Errore",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                int intensitaEmozionee = Integer.parseInt(intensitaStr);

                int inserimentoResult = Client.inserisciNuovaEmozione(Client.idGlobale, selectedEmotion, titolo, autore,
                        nota, spiegazione, intensitaEmozionee);

                switch (inserimentoResult) {
                    case 0 -> {
                        JOptionPane.showMessageDialog(this, "Inserimento nuova emozione effettuato",
                                "Inserimento riuscito", JOptionPane.INFORMATION_MESSAGE);
                        dispose();
                        datiIncompleti = false;
                    }
                    case -1 -> JOptionPane.showMessageDialog(this, "Intensità emozione deve essere compresa tra 1 e 5.",
                            "Errore", JOptionPane.ERROR_MESSAGE);
                    case -2 -> JOptionPane.showMessageDialog(this,
                            "Spiegazione troppo lunga. Non deve superare i 250 caratteri.", "Errore",
                            JOptionPane.ERROR_MESSAGE);
                    case -3 -> JOptionPane.showMessageDialog(this,
                            "Nota emozione troppo lunga. Non deve superare i 50 caratteri.", "Errore",
                            JOptionPane.ERROR_MESSAGE);
                    case -4 -> JOptionPane.showMessageDialog(this,
                            "Nota emozione o Spiegazione emozione non sono stati inseriti.", "Errore",
                            JOptionPane.ERROR_MESSAGE);
                    case -5 -> JOptionPane.showMessageDialog(this, "Canzone o Autore non corrispondono.", "Errore",
                            JOptionPane.ERROR_MESSAGE);
                    case -6 -> JOptionPane.showMessageDialog(this, "Accesso al server non riuscito.", "Errore",
                            JOptionPane.ERROR_MESSAGE);
                }
            } else {
                datiIncompleti = false; // Esci dal ciclo se l'utente ha annullato l'inserimento
            }
        }

    }

    /**
     * @author Cristian Stinga
     * @param str
     * @return boolean
     * 
     *         metodo che verifica la validità di un int
     */
    private boolean isValidInt(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * @author Cristian Stinga
     * @throws RemoteException
     * @throws SQLException
     *                         metodo che gestisce che gestisce la visualizzazione
     *                         delle emozioni
     */

    private void handleVisualizzaEmozioni() throws RemoteException, SQLException {
        JTextField titoloField = GraphicUtils.createTextFields(20);
        JTextField autoreField = GraphicUtils.createTextFields(20);

        JPanel inputPanel = new JPanel();

        inputPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        inputPanel.add(GraphicUtils.createLabels("Titolo:"));
        inputPanel.add(titoloField);
        inputPanel.add(GraphicUtils.createLabels("Autore:"));
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
            emozioni.clear();
        }

    }
}
