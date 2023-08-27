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

        JButton aggiungiCanzonePlaylistButton = new JButton("Aggiungi canzone dalla playlist");
        setButtonSize(aggiungiCanzonePlaylistButton);
        buttonPanel.add(aggiungiCanzonePlaylistButton , gbc);
        gbc.gridy++;

        JButton rimuoviCanzonePlaylist = new JButton("Rimuovi canzone dalla playlist");
        setButtonSize(rimuoviCanzonePlaylist);
        buttonPanel.add(rimuoviCanzonePlaylist , gbc);
        gbc.gridy++;

        JButton eliminaPlaylist = new JButton("Elimina Playlist");
        setButtonSize(eliminaPlaylist);
        buttonPanel.add(eliminaPlaylist , gbc);
        gbc.gridy++;

        add(buttonPanel , BorderLayout.CENTER);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    }
    private void setButtonSize(JButton button)
    {
        button.setPreferredSize(new Dimension(250 , 50));
    }

    private void creaPlaylist(){
        NuovaPlaylistUI nuovaPlaylist = new NuovaPlaylistUI();
        nuovaPlaylist.nuovaPlaylist();
    }

}
