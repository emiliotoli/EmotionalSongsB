/*
package ClientES;

import serverES.ServerInterfaceLoggato;
import serverES.ServerInterfaceNonLoggato;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class MenuPrincipaleUI extends JFrame {
    private static ServerInterfaceNonLoggato interfaceNonLoggato;
    private static ServerInterfaceLoggato interfaceLoggato;

    private static final Color textColor = new Color(76, 79, 105);
    public void mainMenu() throws RemoteException {
        Color background = new Color(204, 208, 218);


        setTitle("Emotional Songs - Main Menu");
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setSize(420, 420);
        //colore background label menu
        getContentPane().setBackground(new Color(204, 208, 218));

        JLabel label = new JLabel("Menu Principale");
        label.setForeground(textColor);
        label.setFont(new Font("Arial", Font.BOLD, 25)); // Imposta il font e la dimensione
        label.setBackground(background);
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setBackground(background);
        add(label, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setBackground(new Color(204, 208, 218));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets.top = 10;
        gbc.insets.bottom = 10;

        JButton ricercaTitoloButton = new JButton("Ricerca per titolo");
        createButtons(ricercaTitoloButton);
        buttonPanel.add(ricercaTitoloButton, gbc);
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

        JButton ricercaAutoreAnnoButton = new JButton("Ricerca per autore e anno");
        createButtons(ricercaAutoreAnnoButton);
        buttonPanel.add(ricercaAutoreAnnoButton, gbc);
        gbc.gridy++;
        ricercaAutoreAnnoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ricercaPerAutoreAnno();
            }
        });

        JButton registrazioneButton = new JButton("Registrati");
        createButtons(registrazioneButton);
        buttonPanel.add(registrazioneButton, gbc);
        gbc.gridy++;
        registrazioneButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registrazione();
            }
        });

        JButton loginButton = new JButton("Effettua il Login");
        createButtons(loginButton);
        buttonPanel.add(loginButton, gbc);
        gbc.gridy++;
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eseguiLogin();
            }
        });

        JButton areaPersonaleButton = new JButton("Area Personale");
        createButtons(areaPersonaleButton);
        buttonPanel.add(areaPersonaleButton, gbc);
        gbc.gridy++;
        areaPersonaleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controlloLogin(Client.isLoggato);
            }
        });

        JButton logoutButton = new JButton("Logout");
        createButtons(logoutButton);
        buttonPanel.add(logoutButton, gbc);
        gbc.gridy++;
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                logout();
            }
        });


        add(buttonPanel, BorderLayout.CENTER);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    }

    private void apriAreaPersonale() {
        MenuAreaPersonaleUI areaPersonale = new MenuAreaPersonaleUI();
        areaPersonale.areaPersonale();
    }

    private void eseguiLogin() {
        if (!Client.isLoggato) {
            LoginUI interfacciaLogin = new LoginUI();
            interfacciaLogin.LoginGUI(new LoginCallback() {
                @Override
                public void onLoginResult(boolean success) {
                    if (success) {
                        JOptionPane.showMessageDialog(MenuPrincipaleUI.this, "Login andato a buon fine", "Successo", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(MenuPrincipaleUI.this, "Credenziali errate", "Errore", JOptionPane.ERROR_MESSAGE);
                    }
                }
            });
        } else {
            JOptionPane.showMessageDialog(this, "Sei già loggato", "Errore", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void controlloLogin(boolean controllo) {
        if (controllo) {
            apriAreaPersonale();
        } else {
            JOptionPane.showMessageDialog(this, "Impossibile eseguire l'azione. \n Effettua prima il login.", "Errore", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void registrazione() {
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

    private void ricercaPerTitolo() {
        RicercaTitoloUI ricercaTitolo = new RicercaTitoloUI();
        ricercaTitolo.ricercaTitolo();
    }

    private void ricercaPerAutoreAnno() {
        RicercaAutoreAnnoUI ricercaAutoreAnno = new RicercaAutoreAnnoUI();
        ricercaAutoreAnno.ricercaCanzone();
    }

    public void logout() {

        if (Client.isLoggato) {
            Client.isLoggato = false;
            JOptionPane.showMessageDialog(MenuPrincipaleUI.this, "Logout effettuato con successo!", "LOGOUT ", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(MenuPrincipaleUI.this, "Impossibile effettuare l'operazione \n" +
                    "Non sei loggato", "LOGOUT ", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    // createButtons: method that creates button, it's important to have the same for all the buttons
    // author @cristiangrossano
    public static void createButtons (JButton bt){
        Color textColor = new Color(76, 79, 105);
        Color borderColor = new Color(4, 165, 229);
        int fontTextSize = 16;

        bt.setPreferredSize(new Dimension(250, 50));
        bt.setBorder(new LineBorder(borderColor, 1));
        bt.setFont(new Font("Arial", Font.BOLD, fontTextSize));
        bt.setForeground(textColor);
    };
    public static void main(String[] args) throws RemoteException {
        MenuPrincipaleUI m = new MenuPrincipaleUI();
        m.mainMenu();
    }
}
*/