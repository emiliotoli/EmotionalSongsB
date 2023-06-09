package ClientES;



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.SQLException;
import java.sql.SQLOutput;
import java.util.Scanner;

import serverES.ServerInterfaceNonLoggato;
import serverES.ServerInterfaceLoggato;



public class Client implements MetodiControlli_Client {

    /** ATTRIBUTI **/
    InputStreamReader isr = new InputStreamReader(System.in);
    BufferedReader br = new BufferedReader(isr);
    int sceltaUtente=-1;
    private String nome, cognome, codiceFiscale, via, numeroCivico, cap, comune, provincia, email, userID, password ;
    private static int nameClient;
    private boolean utenteLoggato = false;
    ServerInterfaceNonLoggato serInterfaccia;

    /**
     *
     * @param
     * @author
     *
     */
    public Client(){

    }

    /**
     * @author
     * Questo metodo serve per gestire le varie operazioni del client e ricevere le rosposte del server
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws NotBoundException
     */

    public void exec() throws IOException, ClassNotFoundException, NotBoundException, SQLException {

        //creao collegamento server
        Registry registroNonLoggato= LocateRegistry.getRegistry(1099);

        try{
            System.out.println("Procedura di collegamento al Server --> Iniziata");
            serInterfaccia=(ServerInterfaceNonLoggato)registroNonLoggato.lookup("ServerEmotionalSongs");
            System.out.println("Procedura di collegamento al Server --> Completata");
            System.out.println("Collegameto al Server--> Riuscito");
        }catch (Exception e){
            e.getMessage();
            System.out.println("Collegamento al Server-->fallito");

        }


        /** creo un menu dove faccio scegliere le operazioni che un utente può svolgere **/


            do {
                System.out.println("-----------------MENU' APPLICAZIONE-----------------");
                System.out.println("Digitare 1 --> per ricercare una canzone per Titolo.");
                System.out.println("Digitare 2 --> per ricercare una canzone per Autore, Anno.");
                System.out.println("Digitare 3 --> per effettuare la Registrazione.");
                System.out.println("Digitare 4 --> per effetuare il Login.");
                System.out.println("Digitare 5 --> per accedere all'area Riservata.");
                System.out.println("Digitare 6 --> per Terminare l'attivita'.");
                System.out.println("\nOperazione scelta: \n");

                boolean controlloScelta=false;

                    sceltaUtente = Integer.parseInt(br.readLine());
                    switch (sceltaUtente) {
                    case 1:
                        // Gestisci l'operazione per la ricerca per Titolo

                        break;
                    case 2:
                        // Gestisci l'operazione per la ricerca per Autore, Anno
                        break;
                    case 3:
                        // Gestisci l'operazione per la registrazione

                        registrazione();

                        break;
                    case 4:
                        // Gestisci l'operazione per il login
                        login();
                        break;
                    case 5:
                        // Gestisci l'operazione per l'accesso all'area riservata
                        break;
                    case 6:
                        // Termina l'attività
                        break;

                }
            } while (sceltaUtente != 6);

            // Chiudi lo scanner dopo aver terminato
            br.close();




        /** una volta scelto l'operazione richiamo il metodo che si trova nell'interccia controllando che i dati inseriti siano corretti **/

        /** se i dati sono corretti li restituisco al client **/

        /** quei dati vengono serializzati e inviati al server **/

        /** il client attenderà la risposta del server **/

    }

    public void login() throws IOException {
        boolean connesso;
        System.out.print("Inserisci un nome utente per il login: ");
        userID=br.readLine();
        System.out.print("Inserisci la password per il login: ");
        password=br.readLine();
        connesso=serInterfaccia.login(userID,password);
        if(connesso){
            System.out.println("ok");
        }
        else{
            System.out.println("no");
        }
    }
    public void registrazione() throws NotBoundException, IOException, SQLException {

        boolean inserimentoRiuscito=false;

        System.out.println("Inizio procedura di registrazione utente\n");

        System.out.print("Inserisci nome: ");
        nome = br.readLine().toLowerCase();
        nome=MetodiControlli_Client.lunghezzaNominativo(nome);

        System.out.print("Inserisci cognome: ");
        cognome = br.readLine();
        cognome = MetodiControlli_Client.lunghezzaNominativo(cognome);

        System.out.print("Inserisci codice fiscale: ");
        codiceFiscale = br.readLine().toLowerCase();
        codiceFiscale = MetodiControlli_Client.formatoCF(codiceFiscale);

        System.out.print("Inserisci indirizzo: ");
        via= br.readLine();

        System.out.print("Inserisci numero civico: ");
        numeroCivico = br.readLine();
        numeroCivico = MetodiControlli_Client.formatoNumeroCivico(numeroCivico);

        System.out.print("Inserisci CAP: ");
        cap = br.readLine();
        cap = MetodiControlli_Client.formatoNumeroCivico(String.valueOf(Integer.parseInt(cap)));

        System.out.print("Inserisci il comune: ");
        comune = br.readLine();
        comune =MetodiControlli_Client.isNotNULL(comune);

        System.out.print("Inserisci la provincia: ");
        provincia = br.readLine();
        provincia = MetodiControlli_Client.isNotNULL(provincia);

        System.out.print("Inserisci e-mail: ");
        email = br.readLine();
        email=MetodiControlli_Client.formatoMail(email);

        System.out.print("Inserisci un nome utente per il login: ");
        userID=br.readLine();
        userID=MetodiControlli_Client.formatoUser(userID);
        boolean esiste=false;
        do{

            //richiamo il medodo che ho nel servere per vedere se esiste l'utente  e aspetto la risposta del server
            esiste=serInterfaccia.checkUserID(userID);
            //se la risposta e negativa esco dal while; altrimenti
            if(!esiste){
                break;
            }
            else{
                System.out.println("nome utente appena inserito esiste gia'. ");
                System.out.println("reinserire il nome utente");
                userID=br.readLine();
            }
        }while(esiste);

        System.out.println("Inserisci password: ");
        password = br.readLine();
        password = MetodiControlli_Client.FormatoPassword(password);

        System.out.println("Inizio procedura inserimento dati su DB");

        Utente utete=new Utente(nome, cognome,codiceFiscale,via,numeroCivico, cap,comune,provincia,email,userID,password);
        inserimentoRiuscito=serInterfaccia.registrazione(utete);


        //inserimentoRiuscito=serInterfaccia.registrazione(nome, cognome,codiceFiscale,via,numeroCivico, cap,comune,provincia,email,userID,password);

        if(inserimentoRiuscito=true){
            System.out.println("Inserimento dati su DB --> Riuscito");
        }
        else{
            System.out.println("Inserimento dati su DB --> Fallito");
        }

    }

    public static void main(String[] args) throws ClassNotFoundException, IOException, NotBoundException, SQLException {
        String identifier = null;
            new Client().exec();

        //for (int i=0;i<4;i++){
           // new Client_Impl(i);
        //}

    }
}
