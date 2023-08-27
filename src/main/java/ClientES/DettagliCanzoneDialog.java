package ClientES;

import javax.swing.*;
import java.awt.*;

public class DettagliCanzoneDialog extends JDialog {
    public DettagliCanzoneDialog(Frame owner, Canzone canzone) {
        super(owner, "Dettagli Canzone", true);
        setSize(300, 150);
        setLocationRelativeTo(owner);

        String titolo = canzone.getTitoloCanzone();
        String autore = canzone.getAutoreCanzone();
        int anno = canzone.getAnnoCanzone();

        String message = "Titolo: " + titolo + "\n"
                + "Autore: " + autore + "\n"
                + "Anno: " + anno;

        JTextArea textArea = new JTextArea(message);
        textArea.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(textArea);
        add(scrollPane);

        JButton mostraEmozioniButton = new JButton("Mostra Emozioni");
        mostraEmozioniButton.addActionListener(e -> {
            mostraEmozioni(canzone);
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(mostraEmozioniButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void mostraEmozioni(Canzone canzone) {
        // Implementa la logica per mostrare le emozioni della canzone
    }
}