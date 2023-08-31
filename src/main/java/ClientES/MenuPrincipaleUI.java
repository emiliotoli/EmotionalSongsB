package ClientES;

import serverES.ServerInterfaceLoggato;
import serverES.ServerInterfaceNonLoggato;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class MenuPrincipaleUI extends JFrame{
    private static ServerInterfaceNonLoggato interfaceNonLoggato;
    private static ServerInterfaceLoggato interfaceLoggato;


    public void mainMenu() throws RemoteException {

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

        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);
            interfaceNonLoggato = (ServerInterfaceNonLoggato) registry.lookup("ServerEmotionalSongs");
            // Inizializza l'interfaccia nel ClientBridge
            ClientBridge.setInterfaceNonLoggato(interfaceNonLoggato);
        } catch (Exception e) {
            e.printStackTrace();
        }

        ricercaTitoloButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ricercaPerTitolo();
            }
        });

        JButton ricercaAutoreAnnoButton = new JButton("Ricerca canzone per autore e anno");
        setButtonSize(ricercaAutoreAnnoButton);
        buttonPanel.add(ricercaAutoreAnnoButton , gbc);
        gbc.gridy++;
        ricercaAutoreAnnoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ricercaPerAutoreAnno();
            }
        });

        JButton registrazioneButton = new JButton("Registrati");
        setButtonSize(registrazioneButton);
        buttonPanel.add(registrazioneButton , gbc);
        gbc.gridy++;
        registrazioneButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registrazione();
            }
        });

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
        if(!Client.isLoggato) {
            LoginUI interfacciaLogin = new LoginUI();
            interfacciaLogin.LoginGUI();
        }
        else{
            JOptionPane.showMessageDialog(this, "Sei già loggato", "Errore", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void controlloLogin(boolean controllo){
        if(controllo) {
            apriAreaPersonale();
        }
        else {
            JOptionPane.showMessageDialog(this, "Impossibile eseguire l'azione. \n Effettua prima il login.", "Errore", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void registrazione()
    {
        RegistrazioneUI registraUtente = new RegistrazioneUI();
        registraUtente.registrazioneUI(new RegistrazioneCallback() {
            @Override
            public void registrazioneCompleted(int result) {
                switch (result) {
                    case 0:
                        JOptionPane.showMessageDialog(MenuPrincipaleUI.this, "Registrazione andata a buon fine", "Successo", JOptionPane.INFORMATION_MESSAGE);
                        break;
                    case 1:
                        JOptionPane.showMessageDialog(MenuPrincipaleUI.this, "Errore nel formato del nome", "Errore", JOptionPane.ERROR_MESSAGE);
                        break;
                    case 2:
                        JOptionPane.showMessageDialog(MenuPrincipaleUI.this, "Errore nel formato del cognome", "Errore", JOptionPane.ERROR_MESSAGE);
                        break;
                    case 3:
                        JOptionPane.showMessageDialog(MenuPrincipaleUI.this, "Errore nel formato del Codice Fiscale", "Errore", JOptionPane.ERROR_MESSAGE);
                        break;
                    case 4:
                        JOptionPane.showMessageDialog(MenuPrincipaleUI.this, "Errore nel formato del Numero Civico", "Errore", JOptionPane.ERROR_MESSAGE);
                        break;
                    case 5:
                        JOptionPane.showMessageDialog(MenuPrincipaleUI.this, "Errore nel formato del CAP", "Errore", JOptionPane.ERROR_MESSAGE);
                        break;
                    case 6:
                        JOptionPane.showMessageDialog(MenuPrincipaleUI.this, "Errore nell'inserimento del comune", "Errore", JOptionPane.ERROR_MESSAGE);
                        break;
                    case 7:
                        JOptionPane.showMessageDialog(MenuPrincipaleUI.this, "Errore nell'inserimento della provincia", "Errore", JOptionPane.ERROR_MESSAGE);
                        break;
                    case 8:
                        JOptionPane.showMessageDialog(MenuPrincipaleUI.this, "Errore nel formato della email", "Errore", JOptionPane.ERROR_MESSAGE);
                        break;
                    case 9:
                        JOptionPane.showMessageDialog(MenuPrincipaleUI.this, "Errore nel formato del nome utente", "Errore", JOptionPane.ERROR_MESSAGE);
                        break;
                    case 10:
                        JOptionPane.showMessageDialog(MenuPrincipaleUI.this, "ID utente gia' esistente", "Errore", JOptionPane.ERROR_MESSAGE);
                        break;
                    case 11:
                        JOptionPane.showMessageDialog(MenuPrincipaleUI.this, "Errore nel formato della password", "Errore", JOptionPane.ERROR_MESSAGE);
                    case 12:
                        JOptionPane.showMessageDialog(MenuPrincipaleUI.this, "Le due password non coincidono. Reinserire due password identiche", "Errore", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
    public void ricercaPerTitolo()
    {
        RicercaTitoloUI ricercaTitolo = new RicercaTitoloUI();
        ricercaTitolo.ricercaTitolo();
    }
    public void ricercaPerAutoreAnno(){
        RicercaAutoreAnnoUI ricercaAutoreAnno = new RicercaAutoreAnnoUI();
        ricercaAutoreAnno.ricercaCanzone();

    }

    public static void main(String[] args) throws RemoteException {
        MenuPrincipaleUI m = new MenuPrincipaleUI();
        m.mainMenu();
    }
}
