package ClientES;

import serverES.ServerInterfaceLoggato;
import serverES.ServerInterfaceNonLoggato;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.rmi.RemoteException;

/**
 * @author Emilio Toli
 *         Classe che gestisce l'interfaccia utente del menu principale
 *
 */

public class MenuPrincipaleUI extends JFrame {

    // <editor-fold desc= "Attributi">
    private static ServerInterfaceNonLoggato interfaceNonLoggato;
    private static ServerInterfaceLoggato interfaceLoggato;
    // </editor-fold>

    /**
     * @author Emilio Toli
     * @throws RemoteException
     *                         Metodo che crea l'interfaccia utente con i pulsanti e
     *                         che chiama i metodi degli eventi
     *                         relativi ad essi
     */

    // <editor-fold desc= "Menu' Principale">
    public void mainMenu() throws RemoteException {

        setTitle("Emotional Songs - Main Menu");
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        JLabel label = new JLabel("Menu Principale Emotional Songs");
        label.setFont(new Font("Arial", Font.BOLD, 30)); // Imposta il font e la dimensione
        label.setHorizontalAlignment(JLabel.CENTER);
        add(label, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets.top = 10;
        gbc.insets.bottom = 10;

        JButton ricercaTitoloButton = GraphicUtils.createButtons("Ricerca per Titolo");
        buttonPanel.add(ricercaTitoloButton, gbc);
        gbc.gridy++;

        ricercaTitoloButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ricercaPerTitolo();
            }
        });

        JButton ricercaAutoreAnnoButton = GraphicUtils.createButtons("Ricerca per autore e anno");
        buttonPanel.add(ricercaAutoreAnnoButton, gbc);
        gbc.gridy++;
        ricercaAutoreAnnoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ricercaPerAutoreAnno();
            }
        });

        JButton registrazioneButton = GraphicUtils.createButtons("Registrati");
        buttonPanel.add(registrazioneButton, gbc);
        gbc.gridy++;

        registrazioneButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registrazione();
            }
        });

        JButton areaPersonaleButton = GraphicUtils.createButtons("Area Personale");
        buttonPanel.add(areaPersonaleButton, gbc);
        gbc.gridy++;
        areaPersonaleButton.setVisible(false);

        areaPersonaleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controlloLogin(Client.isLoggato);
            }
        });

        JButton logoutButton = GraphicUtils.createButtons("Logout");
        buttonPanel.add(logoutButton, gbc);
        gbc.gridy++;
        logoutButton.setVisible(false);

        JButton loginButton = GraphicUtils.createButtons("Login");
        buttonPanel.add(loginButton, gbc);
        gbc.gridy++;

        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                logout(loginButton, registrazioneButton, logoutButton, areaPersonaleButton);
            }
        });

        add(buttonPanel, BorderLayout.CENTER);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eseguiLogin(registrazioneButton, loginButton, logoutButton, areaPersonaleButton);
            }
        });
    }

    /**
     * @author Emilio Toli
     *         Metodo per l'apertura dell'interfaccia dell'area personale
     */
    // <editor-fold desc= "Apertura area personale">
    private void apriAreaPersonale() {
        MenuAreaPersonaleUI areaPersonale = new MenuAreaPersonaleUI();
        areaPersonale.areaPersonale();
    }

    // </editor-fold>

    /**
     * @author Emilio Toli
     *         Metodo che apre l'interfaccia per il login e che gestisce i messaggi
     *         di errore
     */
    // <editor-fold desc= "Apertura login">
    private void eseguiLogin(JButton bt1, JButton bt2, JButton bt3, JButton bt4) {
        if (!Client.isLoggato) {
            LoginUI interfacciaLogin = new LoginUI();
            interfacciaLogin.LoginGUI(new LoginCallback() {
                @Override
                public void onLoginResult(boolean success) {
                    if (success) {
                        JOptionPane.showMessageDialog(MenuPrincipaleUI.this, "Login andato a buon fine", "Successo",
                                JOptionPane.INFORMATION_MESSAGE);
                        bt1.setVisible(false);
                        bt2.setVisible(false);
                        bt3.setVisible(true);
                        bt4.setVisible(true);
                    } else {
                        JOptionPane.showMessageDialog(MenuPrincipaleUI.this, "Credenziali errate", "Errore",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
            });
        } else {
            JOptionPane.showMessageDialog(this, "Sei già loggato", "Errore", JOptionPane.ERROR_MESSAGE);
        }
    }
    // </editor-fold>

    /**
     * @author Emilio Toli
     * @param controllo
     *                  Metodo per il controllo dello stato corrente di login
     *                  affinchè si possa aprire il menu' area personale
     */
    // <editor-fold desc= "Controllo del login">
    private void controlloLogin(boolean controllo) {
        if (controllo) {
            apriAreaPersonale();
        } else {
            JOptionPane.showMessageDialog(this, "Impossibile eseguire l'azione. \n Effettua prima il login.", "Errore",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
    // </editor-fold>

    /**
     * @author Emilio Toli
     *         Metodo che apre l'interfaccia per la registrazione di un nuovo utente
     *         e per la gestione dei vari messaggi di errore
     */
    // <editor-fold desc= "Apertura UI per Registrazione">
    private void registrazione() {
        RegistrazioneUI registraUtente = new RegistrazioneUI();
        registraUtente.registrazioneUI(new RegistrazioneCallback() {
            @Override
            public void registrazioneCompleted(int result) {
                switch (result) {
                    case 0:
                        JOptionPane.showMessageDialog(MenuPrincipaleUI.this, "Registrazione andata a buon fine",
                                "Successo", JOptionPane.INFORMATION_MESSAGE);
                        break;
                    case 1:
                        JOptionPane.showMessageDialog(MenuPrincipaleUI.this, "Errore nel formato del nome", "Errore",
                                JOptionPane.ERROR_MESSAGE);
                        break;
                    case 2:
                        JOptionPane.showMessageDialog(MenuPrincipaleUI.this, "Errore nel formato del cognome", "Errore",
                                JOptionPane.ERROR_MESSAGE);
                        break;
                    case 3:
                        JOptionPane.showMessageDialog(MenuPrincipaleUI.this, "Errore nel formato del Codice Fiscale",
                                "Errore", JOptionPane.ERROR_MESSAGE);
                        break;
                    case 4:
                        JOptionPane.showMessageDialog(MenuPrincipaleUI.this, "Errore nel formato del Numero Civico",
                                "Errore", JOptionPane.ERROR_MESSAGE);
                        break;
                    case 5:
                        JOptionPane.showMessageDialog(MenuPrincipaleUI.this, "Errore nel formato del CAP", "Errore",
                                JOptionPane.ERROR_MESSAGE);
                        break;
                    case 6:
                        JOptionPane.showMessageDialog(MenuPrincipaleUI.this, "Errore nell'inserimento del comune",
                                "Errore", JOptionPane.ERROR_MESSAGE);
                        break;
                    case 7:
                        JOptionPane.showMessageDialog(MenuPrincipaleUI.this, "Errore nell'inserimento della provincia",
                                "Errore", JOptionPane.ERROR_MESSAGE);
                        break;
                    case 8:
                        JOptionPane.showMessageDialog(MenuPrincipaleUI.this, "Errore nel formato della email", "Errore",
                                JOptionPane.ERROR_MESSAGE);
                        break;
                    case 9:
                        JOptionPane.showMessageDialog(MenuPrincipaleUI.this, "Errore nel formato del nome utente",
                                "Errore", JOptionPane.ERROR_MESSAGE);
                        break;
                    case 10:
                        JOptionPane.showMessageDialog(MenuPrincipaleUI.this, "ID utente gia' esistente", "Errore",
                                JOptionPane.ERROR_MESSAGE);
                        break;
                    case 11:
                        JOptionPane.showMessageDialog(MenuPrincipaleUI.this, "Errore nel formato della password",
                                "Errore", JOptionPane.ERROR_MESSAGE);
                    case 12:
                        JOptionPane.showMessageDialog(MenuPrincipaleUI.this,
                                "Le due password non coincidono. Reinserire due password identiche", "Errore",
                                JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
    // </editor-fold>

    /**
     * @author Emilio Toli
     *         Metodo che apre l'interfaccia per la ricerca di una canzone dato il
     *         titolo in input
     */
    // <editor-fold desc= "Apertura UI per la ricerca di una canzone per titolo">
    private void ricercaPerTitolo() {
        RicercaTitoloUI ricercaTitolo = new RicercaTitoloUI();
        ricercaTitolo.ricercaTitolo();
    }
    // </editor-fold>

    /**
     * @author Emilio Toli
     *         Metodo che apre l'interfaccia per la ricerca di una canzone dati
     *         autore ed anno in input
     */
    // <editor-fold desc= "Apertura UI per la ricerca di una canzone per autore ed
    // anno">
    private void ricercaPerAutoreAnno() {
        RicercaAutoreAnnoUI ricercaAutoreAnno = new RicercaAutoreAnnoUI();
        ricercaAutoreAnno.ricercaCanzone();

    }

    /**
     * @author Emilio Toli
     *         Metodo che apre l'interfaccia per effettuare il logout
     */
    // <editor-fold desc= "Apertura UI per effettuare il logout">
    public void logout(JButton bt1, JButton bt2, JButton bt3, JButton bt4) {

        if (Client.isLoggato) {
            Client.isLoggato = false;
            JOptionPane.showMessageDialog(MenuPrincipaleUI.this, "Logout effettuato con successo!", "LOGOUT ",
                    JOptionPane.INFORMATION_MESSAGE);
            bt1.setVisible(true);
            bt2.setVisible(true);
            bt3.setVisible(false);
            bt4.setVisible(false);
        } else {
            JOptionPane.showMessageDialog(MenuPrincipaleUI.this, "Impossibile effettuare l'operazione \n" +
                    "Non sei loggato", "LOGOUT ", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    // </editor-fold>

    // </editor-fold>

    /**
     * @author Emilio Toli
     *         Metodo Main che apre il menu' principale all'avvio dell'applicazione
     */
    // <editor-fold desc= "METODO MAIN PER APERTURA MENU PRINCIPALE">
    public static void main(String[] args) throws RemoteException {
        MenuPrincipaleUI m = new MenuPrincipaleUI();
        m.mainMenu();
    }
    // </editor-fold>
}
