package ClientES;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuAreaPersonaleUI extends JFrame {

    public void areaPersonale()
    {
        setTitle("Emotional Songs - Area Personale");
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        JLabel label = new JLabel("Area Personale");
        label.setFont(new Font("Arial", Font.BOLD, 24)); // Imposta il font e la dimensione
        label.setHorizontalAlignment(JLabel.CENTER);
        add(label , BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets.top = 10;
        gbc.insets.bottom = 10;

        JButton creaPlaylistButton = new JButton("Crea una nuova playlist");
        setButtonSize(creaPlaylistButton);
        buttonPanel.add(creaPlaylistButton , gbc);
        gbc.gridy++;
        creaPlaylistButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                creaPlaylist();
            }
        });

        JButton visualizzaPlaylistButton = new JButton("Visualizza le tue playlist");
        setButtonSize(visualizzaPlaylistButton);
        buttonPanel.add(visualizzaPlaylistButton , gbc);
        gbc.gridy++;
        visualizzaPlaylistButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                visualizzaPlaylist();
            }
        });

        JButton aggiungiCanzonePlaylistButton = new JButton("Aggiungi canzone alla playlist");
        setButtonSize(aggiungiCanzonePlaylistButton);
        buttonPanel.add(aggiungiCanzonePlaylistButton , gbc);
        gbc.gridy++;
        aggiungiCanzonePlaylistButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                aggiungiAllaPlaylist();
            }
        });

        JButton rimuoviCanzonePlaylist = new JButton("Rimuovi canzone dalla playlist");
        setButtonSize(rimuoviCanzonePlaylist);
        buttonPanel.add(rimuoviCanzonePlaylist , gbc);
        gbc.gridy++;

        JButton eliminaPlaylist = new JButton("Elimina Playlist");
        setButtonSize(eliminaPlaylist);
        buttonPanel.add(eliminaPlaylist , gbc);
        gbc.gridy++;
        eliminaPlaylist.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminaPlaylist();
            }
        });

        add(buttonPanel , BorderLayout.CENTER);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    }
    private void setButtonSize(JButton button)
    {
        button.setPreferredSize(new Dimension(250 , 50));
    }

    private void creaPlaylist(){
        NuovaPlaylistUI nuovaPlaylist = new NuovaPlaylistUI();
        nuovaPlaylist.nuovaPlaylistUI(new PlaylistCreationCallback() {

            public void onPlaylistCreationResult(int result)  {
                if (result == 0) {
                    // La playlist è stata creata con successo
                    // Puoi gestire il successo qui, ad esempio aggiornando l'interfaccia utente
                    JOptionPane.showMessageDialog(MenuAreaPersonaleUI.this, "Playlist creata con successo", "Successo", JOptionPane.INFORMATION_MESSAGE);
                } else if (result == 1) {
                    // Playlist già esistente, mostra un messaggio di errore
                    JOptionPane.showMessageDialog(MenuAreaPersonaleUI.this, "Playlist già esistente", "Errore", JOptionPane.ERROR_MESSAGE);
                } else {
                    // Altri casi di errore, gestisci secondo necessità
                    JOptionPane.showMessageDialog(MenuAreaPersonaleUI.this, "Errore durante la creazione della playlist", "Errore", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
    private void aggiungiAllaPlaylist(){
        AggiungiCanzonePlaylist acp = new AggiungiCanzonePlaylist();
        acp.aggiungiCanzoniAPlaylist();
    }
    private void visualizzaPlaylist(){
        VisualizzaPlaylistUI vp = new VisualizzaPlaylistUI();
        vp.visualizzaPlaylist();
    }

    private void eliminaPlaylist() {
        EliminaPlaylistUI ep = new EliminaPlaylistUI();
        ep.eliminaPlaylistUI(new PlaylistDeletionCallback() {
            @Override
            public void onPlaylistDeletionResult(int result) {
                switch (result) {
                    case 0:
                        JOptionPane.showMessageDialog(MenuAreaPersonaleUI.this, "Playlist eliminata con successo", "Successo", JOptionPane.INFORMATION_MESSAGE);
                        break;
                    case 1:
                        JOptionPane.showMessageDialog(MenuAreaPersonaleUI.this, "Errore nella cancellazione della PlayList", "Errore", JOptionPane.ERROR_MESSAGE);
                        break;
                    case -1:
                        JOptionPane.showMessageDialog(MenuAreaPersonaleUI.this, "Errore! Nome Plalist non trovato", "Errore", JOptionPane.ERROR_MESSAGE);
                        break;
                    case -2:
                        JOptionPane.showMessageDialog(MenuAreaPersonaleUI.this, "Errore durante l'inizializzazione dell'interfaccia", "Errore", JOptionPane.ERROR_MESSAGE);
                        break;
                }
            }
        });
    }

}
