package ClientES;

import ClientES.Client;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author Emilio Toli
 *      Classe per la gestione dell'interfaccia utente relativa alla ricerca
 *      di un brano per autore e per anno
 */

public class RicercaAutoreAnnoUI extends JFrame {
    // <editor-fold desc= "Attributi">
    private JTextField autoreField;
    private JTextField annoField;
    private JButton submitButton;
    // </editor-fold>
    private static final Color textColor = new Color(76, 79, 105);
    private static final Color background = new Color(204, 208, 218);


    /**
     * @author Emilio Toli
     *      Metodo per la creazione della UI e per la chiamata
     *      dei metodi per la ricerca delle canzoni e delle emozioni
     *      tramite l'evento di click del pulsante
     */

    // <editor-fold desc= "Ricerca canzone">

    public void ricercaCanzone() {

        setTitle("Ricerca per Autore e Anno");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(300, 150);
        setLocationRelativeTo(null);
        setVisible(true);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);

        autoreField = new JTextField(15);
        annoField = new JTextField(15);
        submitButton = new JButton("Cerca");

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(new JLabel("Autore:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(autoreField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(new JLabel("Anno:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(annoField, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(submitButton, gbc);

        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    handleSearch();
                } catch (SQLException | IOException remoteException) {
                    remoteException.printStackTrace();
                }
            }
        });

        add(panel);
    }
// </editor-fold>

    /**
     * @author Emilio Toli
     * @throws IOException
     * @throws SQLException
     *          Metodo chiamato al click del pulsante per la ricerca che
     *          mostra le canzoni una volta inseriti autore ed anno
     */
    // <editor-fold desc= "Pulsante Cerca">
    private void handleSearch() throws IOException, SQLException {
        String autore = autoreField.getText();
        int anno = Integer.parseInt(annoField.getText()); // Assumendo che l'anno sia un intero

        ArrayList<Canzone> canzoni = Client.RicercaCanzoniAutoreAnno(autore, anno);
        if (!canzoni.isEmpty()) {
            StringBuilder message = new StringBuilder("Canzoni corrispondenti:\n");
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
            JButton insertEmotionsButton = new JButton("Inserisci Emozioni");
            visualizzaEmozioniButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    try {
                        handleVisualizzaEmozioni();
                    } catch (RemoteException | SQLException remoteException) {
                        remoteException.printStackTrace();
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
    // </editor-fold>

    /**
     * @author Emilio Toli
     * @throws RemoteException
     * @throws SQLException
     *
     *      Metodo che permette di visualizzare le emozioni associate
     *      ad una canzone
     */


    // <editor-fold desc= "Visualizza le emozioni">
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
    // </editor-fold>
    private void handleInsertEmotions() throws IOException, SQLException {
        JTextField titoloField = new JTextField(20);
        JTextField autoreField = new JTextField(20);
        JTextField notaEmozione = new JTextField(50);
        JTextField spiegazioneEmozione = new JTextField(50);
        JTextField intensitàEmozione = new JTextField(1);

        JPanel insertEmotion = new JPanel();
        insertEmotion.setLayout(new GridLayout(6, 2));
        insertEmotion.setSize(500, 500);
        insertEmotion.setLocation(430, 100);

        String[] emotions = { "Amazement", "Solemnity", "Tenderness", "Nostalgia", "Calmness", "Power", "Joy",
                "Tension", "Sadness" };
        final JComboBox<String> emotionComboBox = new JComboBox<>(emotions);

        insertEmotion.add(new JLabel("Inserisci titolo: "));
        insertEmotion.add(titoloField);
        insertEmotion.add(new JLabel("Autore: "));
        insertEmotion.add(autoreField);
        insertEmotion.add(new JLabel("Seleziona Emozione:"));
        insertEmotion.add(emotionComboBox);
        insertEmotion.add(new JLabel("Intensità emozione:"));
        insertEmotion.add(intensitàEmozione);
        insertEmotion.add(new JLabel("nota emozione"));
        insertEmotion.add(notaEmozione);
        insertEmotion.add(new JLabel("spiegazione emozione"));
        insertEmotion.add(spiegazioneEmozione);

        int result = JOptionPane.showConfirmDialog(this, insertEmotion, "Inserisci Emozioni",JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            String selectedEmotion = (String) emotionComboBox.getSelectedItem();
            String titolo = titoloField.getText();
            String autore = autoreField.getText();
            String nota = notaEmozione.getText();
            String spiegazione = spiegazioneEmozione.getText();
            String intensitàStr = intensitàEmozione.getText();

            if (titolo.isEmpty() || autore.isEmpty() || nota.isEmpty() || spiegazione.isEmpty() || intensitàStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Tutti i campi devono essere completati.", "Errore", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!isValidInt(intensitàStr)) {
                JOptionPane.showMessageDialog(this, "Intensità emozione deve essere un numero intero.", "Errore", JOptionPane.ERROR_MESSAGE);
                return;
            }
            int intensitaEmozione = Integer.parseInt(intensitàStr);

            int inserimentoResult = Client.inserisciNuovaEmozione(Client.idGlobale, selectedEmotion, titolo, autore, nota, spiegazione, intensitaEmozione);

            switch (inserimentoResult) {
                case 0:
                    JOptionPane.showMessageDialog(this, "Inserimento nuova emozione effettuato", "Inserimento riuscito", JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                    break;
                case -1:
                    JOptionPane.showMessageDialog(this, "Intensità emozione deve essere compresa tra 1 e 5.", "Errore", JOptionPane.ERROR_MESSAGE);
                    break;
                case -2:
                    JOptionPane.showMessageDialog(this, "Spiegazione troppo lunga. Non deve superare i 250 caratteri.", "Errore", JOptionPane.ERROR_MESSAGE);
                    break;
                case -3:
                    JOptionPane.showMessageDialog(this, "Nota emozione troppo lunga. Non deve superare i 50 caratteri.", "Errore", JOptionPane.ERROR_MESSAGE);
                    break;
                case -4:
                    JOptionPane.showMessageDialog(this, "Nota emozione o Spiegazione emozione non sono stati inseriti.", "Errore", JOptionPane.ERROR_MESSAGE);
                    break;
                case -5:
                    JOptionPane.showMessageDialog(this, "Canzone o Autore non corrispondono.", "Errore", JOptionPane.ERROR_MESSAGE);
                    break;
                case -6:
                    JOptionPane.showMessageDialog(this, "Accesso al server non riuscito.", "Errore", JOptionPane.ERROR_MESSAGE);
                    break;
            }
        }
    }
    private boolean isValidLength(String text, int minLength, int maxLength) {
        int length = text.length();
        return length >= minLength && length <= maxLength;
    }

    public static void createButtons(JButton bt) {
        Color borderColor = new Color(4, 165, 229);
        int fontTextSize = 16;

        bt.setPreferredSize(new Dimension(250, 50));
        bt.setBorder(new LineBorder(borderColor, 1));
        bt.setFont(new Font("Arial", Font.BOLD, fontTextSize));
        bt.setForeground(textColor);
    };

    private boolean isValidInt(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
