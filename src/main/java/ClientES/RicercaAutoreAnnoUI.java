package ClientES;

import ClientES.Client;

import javax.swing.*;
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

            visualizzaEmozioniButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    try {
                        handleVisualizzaEmozioni();
                    } catch (RemoteException | SQLException remoteException) {
                        remoteException.printStackTrace();
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

}
