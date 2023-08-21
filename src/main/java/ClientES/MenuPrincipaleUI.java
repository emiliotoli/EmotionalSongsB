package ClientES;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuPrincipaleUI extends JFrame{

    public void mainMenu(){
        setTitle("Emotional Songs - Main Menu'");
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        JLabel label = new JLabel("Menu' Principale");
        label.setFont(new Font("Arial", Font.BOLD, 24)); // Imposta il font e la dimensione
        label.setHorizontalAlignment(JLabel.CENTER);
        add(label , BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets.top = 10;
        gbc.insets.bottom = 10;

        JButton ricercaTitoloButton = new JButton("Ricerca canzone per titolo");
        setButtonSize(ricercaTitoloButton);
        buttonPanel.add(ricercaTitoloButton , gbc);
        gbc.gridy++;

        JButton ricercaAutoreAnnoButton = new JButton("Ricerca canzone per autore e anno");
        setButtonSize(ricercaAutoreAnnoButton);
        buttonPanel.add(ricercaAutoreAnnoButton , gbc);
        gbc.gridy++;

        JButton registrazioneButton = new JButton("Registrati");
        setButtonSize(registrazioneButton);
        buttonPanel.add(registrazioneButton , gbc);
        gbc.gridy++;

        JButton loginButton = new JButton("Effettua il Login");
        setButtonSize(loginButton);
        buttonPanel.add(loginButton , gbc);
        gbc.gridy++;
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eseguiLogin();
            }
        });

        JButton areaPersonaleButton = new JButton("Area Personale");
        setButtonSize(areaPersonaleButton);
        buttonPanel.add(areaPersonaleButton , gbc);
        gbc.gridy++;
        areaPersonaleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controlloLogin(Client.isLoggato);
            }
        });


        add(buttonPanel , BorderLayout.CENTER);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    }

    private void setButtonSize(JButton button)
    {
        button.setPreferredSize(new Dimension(250 , 50));
    }
    private void apriAreaPersonale(){
        MenuAreaPersonaleUI areaPersonale = new MenuAreaPersonaleUI();
        areaPersonale.areaPersonale();
    }

    private void eseguiLogin(){
        LoginUI interfacciaLogin = new LoginUI();
        interfacciaLogin.LoginGUI();
    }

    private void controlloLogin(boolean controllo){
        if(controllo) {
            apriAreaPersonale();
        }
        else {
            JOptionPane.showMessageDialog(this, "Impossibile eseguire l'azione. \n Effettua prima il login.", "Errore", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args){
        MenuPrincipaleUI m = new MenuPrincipaleUI();
        m.mainMenu();
    }
}
