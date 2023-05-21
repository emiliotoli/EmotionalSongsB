package ClientES;



import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.SQLException;
import java.util.Scanner;

import serverES.ServerInterfaceNonLoggato;



public class Client implements MetodiControlli_Client {

    /** ATTRIBUTI **/
    Scanner scanner =new Scanner(System.in);
    int sceltaUtente=-1;
    private String nome, cognome, codiceFiscale, via, numeroCivico, comune, provincia, email, userID, password;
    private int cap;
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
            serInterfaccia=(ServerInterfaceNonLoggato)registroNonLoggato.lookup("Server");
        }catch (Exception e){
            System.out.println("collegamento fallito");
            e.getMessage().toString();
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
                System.out.println("\nOperazione scelta");

                boolean controlloScelta=false;
                do {
                    sceltaUtente = scanner.nextInt();
                    if(sceltaUtente>=1  || sceltaUtente<=6){
                        controlloScelta=true;
                    }else {
                        System.out.println("inserimento scelta non valido.");
                        System.out.println("Reinserire la scelta.");
                    }
                }while(!controlloScelta);

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
            scanner.close();




        /** una volta scelto l'operazione richiamo il metodo che si trova nell'interccia controllando che i dati inseriti siano corretti **/

        /** se i dati sono corretti li restituisco al client **/

        /** quei dati vengono serializzati e inviati al server **/

        /** il client attenderà la risposta del server **/

    }

    public void registrazione() throws NotBoundException, IOException, SQLException {


        System.out.println("Inizio procedura di registrazione utente\n");

        System.out.print("Inserisci nome: ");
        nome = scanner.nextLine().toLowerCase();
        nome=MetodiControlli_Client.lunghezzaNominativo(nome);

        System.out.print("Inserisci cognome: ");
        cognome = scanner.nextLine();
        cognome = MetodiControlli_Client.lunghezzaNominativo(cognome);

        System.out.print("Inserisci codice fiscale: ");
        codiceFiscale = scanner.nextLine().toLowerCase();
        codiceFiscale = MetodiControlli_Client.formatoCF(codiceFiscale);

        System.out.print("Inserisci indirizzo: ");
         via= scanner.nextLine();

        System.out.print("Inserisci numero civico: ");
        numeroCivico = scanner.nextLine();
        numeroCivico = MetodiControlli_Client.formatoNumeroCivico(numeroCivico);

        System.out.print("Inserisci CAP: ");
        cap = scanner.nextInt();
        cap = Integer.parseInt(MetodiControlli_Client.formatoCAP(cap));

        System.out.print("Inserisci il comune: ");
        comune = scanner.nextLine();
        comune =MetodiControlli_Client.isNotNULL(comune);

        System.out.print("Inserisci la provincia: ");
        provincia = scanner.nextLine();
        provincia = MetodiControlli_Client.isNotNULL(provincia);

        System.out.print("Inserisci e-mail: ");
        email = scanner.nextLine();
        email=MetodiControlli_Client.formatoMail(email);

        System.out.print("Inserisci un nome utente per il login: ");
        userID=scanner.nextLine();
        boolean esiste=false;
        do{

            //richiamo il medodo che ho nel servere per vedere se esiste l'utente  e aspetto la risposta del server

            //se la risposta e negativa esco dal while; altrimenti
            if(!esiste){
                break;
            }
            else{
                System.out.println("nome utente appena inserito esiste gia'. ");
                System.out.println("reinserire il nome utente");
                userID=scanner.nextLine();
            }

            Utente utente =new Utente(nome, cognome,codiceFiscale,via,numeroCivico,comune,provincia,cap,email,userID,password);
            serInterfaccia.registrazione(utente);


        }while(esiste);


        //System.out.print("Inserisci password: ");
        //password = br.readLine();
        //password = Utenti.controlloPassword(password);

        //Utenti.Registrazione( nome,cognome,codfisc,indirizzo, numerocivico,cap,comune,provincia,email,username,password);


    }

    public static void main(String[] args) throws ClassNotFoundException, IOException, NotBoundException, SQLException {
        String identifier = null;
        new Client().exec();
    }
}
