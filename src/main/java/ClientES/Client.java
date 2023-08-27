package ClientES;



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import serverES.ServerInterfaceNonLoggato;
import serverES.ServerInterfaceLoggato;


public class Client implements MetodiControlli_Client {

    /** ATTRIBUTI **/
    InputStreamReader isr = new InputStreamReader(System.in);
    BufferedReader br = new BufferedReader(isr);
    int sceltaUtente=-1;
    int sceltaUtenteAreaPersonale=-1;
    private static String nome, cognome, codiceFiscale, via, numeroCivico, cap, comune, provincia, email, userID, password, p2;
    private static String titoloCanzone;
    private static String autoreCanzone;
    private static String annoCanzoneAutoreAnno;
    private static int annoCanzone;
    private static String emozioneScelta="";
    private static String spiegazioneEmozione;
    private static String notaEmozione;
    private static String nomePlaylist;
    private static int punteggioEmozione = 0;
    private static boolean controlloPunteggio;
    private static ArrayList<Canzone> informazioniCanzoneTitolo;
    private static ArrayList<Canzone> informazioniCanzoneAuoreAnno;
    private static ArrayList<Emozione> emozioniCanzone;
    private static ArrayList<PlayList> playlistUtente;
    private  static  ArrayList<List<Emozione>> emozioniPerCanzoni = new ArrayList<>();
    private static ArrayList<Emozione> emozioniDellaCanzone = new ArrayList<>();
    private static boolean inserimentoEmozione;
    private static boolean creazionePlaylist;
    private boolean inserimentoCanzonePlaylist;
    public static boolean isLoggato = true;
    static ServerInterfaceNonLoggato interfaceNonLoggato;
    static ServerInterfaceLoggato interfaceLoggato;

    /**
     *
     * @param
     * @author
     *
     */
    public Client(){
        interfaceNonLoggato = null; // Inizializza con null
        interfaceLoggato = null; // Inizializza con null
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
            //interfaceNonLoggato=(ServerInterfaceNonLoggato)registroNonLoggato.lookup("ServerEmotionalSongs");
            interfaceNonLoggato = (ServerInterfaceNonLoggato) registroNonLoggato.lookup("ServerEmotionalSongs");
            System.out.println("Procedura di collegamento al Server --> Completata");
            System.out.println("Collegameto al Server--> Riuscito" + "\n");
        }catch (Exception e){
            e.getMessage();
            System.out.println("Collegamento al Server--> Fallito" +"\n");
            return;

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
                System.out.println("\nOperazione scelta: ");

                boolean controlloScelta=false;

                    sceltaUtente = Integer.parseInt(br.readLine());
                    switch (sceltaUtente) {
                    case 1:
                        // Gestisci l'operazione per la ricerca per Titolo
                        RicercaCanzoniTitolo(titoloCanzone);
                        break;

                    case 2:
                        // Gestisci l'operazione per la ricerca per Autore, Anno
                        RicercaCanzoniAutoreAnno(autoreCanzone, annoCanzone);
                        break;

                    case 3:
                        // Gestisci l'operazione per la registrazione
                        registrazione(nome, cognome, codiceFiscale, via, numeroCivico, cap, comune, provincia, email, userID, password, p2);
                        break;

                    case 4:
                        // Gestisci l'operazione per il login
                        if(!isLoggato){
                            login(userID, password);
                        }
                        else{
                            System.out.println("Hai già effettuato l'accesso");
                        }

                        break;

                    case 5:
                        // Gestisci l'operazione per l'accesso all'area riservata

                        if(!isLoggato) {
                            System.out.println("Devi essere loggato prima di accedere all'area personale");
                            System.out.println("Inserisci le credenziali di accesso: ");
                            login(userID, password);
                            if(!isLoggato){
                                System.out.println("Non puoi accedere al menù principale");
                                break;
                            }
                            else{
                                System.out.println("puoi accedere all'area personale");

                                 do{
                                     System.out.println("----------------- MENU' AREA PERSONALE-----------------");
                                     System.out.println("Digitare 1 --> per creare una playlist.");
                                     System.out.println("Digitare 2 --> per visualizzare lista delle playlist.");
                                     System.out.println("Digitare 3 --> per visualizzare le canzoni di  una playlist.");
                                     System.out.println("Digitare 4 --> per aggiungere una canzone ad una playlist.");
                                     System.out.println("Digitare 5 --> per rimuovere una canzone da una playlist.");
                                     System.out.println("Digitare 6 --> per eliminare una playlist.");
                                     System.out.println("Digitare 7 --> tornare al menù principale.");
                                     System.out.println("Digitare 8 --> per eseguire il logout.");

                                     System.out.println("\nOperazione scelta: ");
                                     sceltaUtenteAreaPersonale = Integer.parseInt(br.readLine());

                                     switch (sceltaUtenteAreaPersonale) {
                                         case 1:
                                             //Gestisci l'operazione per creare una Playlist
                                             creaPlayList(nomePlaylist);
                                             break;

                                         case 2:
                                             //Gestisci l'operazione per visualizzare la lista delle Playlist
                                             visualizzaPlaylist(nomePlaylist);
                                             break;

                                         case 3:
                                             //Gestisci l'operazione per visualizzare le canzoni di  una playlist
                                             break;

                                         case 4:
                                             //Gestisci l'operazione per aggiungere una canzone ad una playlist
                                             break;

                                         case 5:
                                             //Gestisci l'operazione per rimuovere una canzone da una playlist
                                             break;

                                         case 6:
                                             //Gestisci l'operazione per per eliminare una playlist
                                             eliminaPlalist(nomePlaylist);
                                             break;

                                         case 7:
                                             //Gestisci l'operazione per tornare al menù principale
                                             sceltaUtenteAreaPersonale=0;
                                             break;

                                         case 8:
                                             //Gestisci l'operazione per per eseguire il logout
                                             isLoggato=false;
                                             sceltaUtenteAreaPersonale=0;
                                             break;

                                     }
                                 }while(sceltaUtenteAreaPersonale!=0);

                            }
                        }
                        else{
                            System.out.println("-------------ok----------------");

                            do{
                                System.out.println("----------------- MENU' AREA PERSONALE-----------------");
                                System.out.println("Digitare 1 --> per creare una playlist.");
                                System.out.println("Digitare 2 --> per visualizzare lista delle playlist.");
                                System.out.println("Digitare 3 --> per visualizzare le canzoni di  una playlist.");
                                System.out.println("Digitare 4 --> per aggiungere una canzone ad una playlist.");
                                System.out.println("Digitare 5 --> per rimuovere una canzone da una playlist.");
                                System.out.println("Digitare 6 --> per eliminare una playlist.");
                                System.out.println("Digitare 7 --> tornare al menù principale.");
                                System.out.println("Digitare 8 --> per eseguire il logout.");

                                System.out.println("\nOperazione scelta: ");
                                sceltaUtenteAreaPersonale = Integer.parseInt(br.readLine());

                                switch (sceltaUtenteAreaPersonale) {
                                    case 1:
                                        //Gestisci l'operazione per creare una Playlist
                                        creaPlayList(nomePlaylist);
                                        break;

                                    case 2:
                                        //Gestisci l'operazione per visualizzare la lista delle Playlist
                                        visualizzaPlaylist(nomePlaylist);
                                        break;

                                    case 3:
                                        //Gestisci l'operazione per visualizzare le canzoni di  una playlist
                                        break;

                                    case 4:
                                        //Gestisci l'operazione per aggiungere una canzone ad una playlist
                                        break;

                                    case 5:
                                        //Gestisci l'operazione per rimuovere una canzone da una playlist
                                        break;

                                    case 6:
                                        //Gestisci l'operazione per per eliminare una playlist
                                        eliminaPlalist(nomePlaylist);
                                        break;

                                    case 7:
                                        //Gestisci l'operazione per tornare al menù principale
                                        sceltaUtenteAreaPersonale=0;
                                        break;

                                    case 8:
                                        //Gestisci l'operazione per per eseguire il logout
                                        isLoggato=false;
                                        sceltaUtenteAreaPersonale=0;
                                        break;

                                }
                            }while(sceltaUtenteAreaPersonale!=0);
                        }
                    case 6:
                        // Termina l'attività
                        break;
                }
            } while (sceltaUtente != 6);

            // Chiudi lo scanner dopo aver terminato
            br.close();
    }
        public static ArrayList<Canzone> RicercaCanzoniTitolo(String titoloCanzone) throws IOException, SQLException {

            if (interfaceNonLoggato == null) {
                System.out.println("L'interfaccia non è stata inizializzata correttamente");
                return new ArrayList<>(); // o gestisci l'errore come meglio credi
            }
        //passo il titolo della canzone da ricercare
        informazioniCanzoneTitolo = (ArrayList<Canzone>) interfaceNonLoggato.ricercaCanzoneTitolo(titoloCanzone);

        //elaboro la risposta
        if (!informazioniCanzoneTitolo.isEmpty()){
            for (Canzone canzone : informazioniCanzoneTitolo) {
                titoloCanzone = canzone.getTitoloCanzone();
                autoreCanzone = canzone.getAutoreCanzone();
                annoCanzone = canzone.getAnnoCanzone();

                System.out.println("Titolo: " + titoloCanzone);
                System.out.println("Autore: " + autoreCanzone);
                System.out.println("Anno: " + annoCanzone);
                System.out.println();
            }
        }
        else {
            System.out.println("Canzone non trovata");
        }
        return informazioniCanzoneTitolo;
    }
    public static  ArrayList<Canzone> RicercaCanzoniAutoreAnno(String autoreCanzone, int annoCanzone) throws IOException, SQLException {

        /*System.out.println("Inserisci il nome dell'Autore da cercare: ");
        autoreCanzone = br.readLine();

        System.out.println("Inserisci l'Anno della canzone: ");
        annoCanzoneAutoreAnno= br.readLine();
        annoCanzone=Integer.parseInt(annoCanzoneAutoreAnno);*/

        informazioniCanzoneAuoreAnno= (ArrayList<Canzone>) interfaceNonLoggato.ricercaCanzoneAutoreAnno(autoreCanzone,annoCanzone);

        //elaboro la risposta
        if (!informazioniCanzoneAuoreAnno.isEmpty()){
            for (Canzone canzone : informazioniCanzoneAuoreAnno) {
                titoloCanzone = canzone.getTitoloCanzone();
                autoreCanzone = canzone.getAutoreCanzone();
                annoCanzone = canzone.getAnnoCanzone();

                System.out.println("Titolo: " + titoloCanzone);
                System.out.println("Autore: " + autoreCanzone);
                System.out.println("Anno: " + annoCanzone);
                System.out.println();
            }
        }
        else {
            System.out.println("Canzone non trovata");
        }

        return informazioniCanzoneAuoreAnno;
    }
    public static ArrayList<Emozione> visualizzaEmozioniCanzone(String titoloCanzone, String autoreCanzone ) throws SQLException, RemoteException {
        if(titoloCanzone != null && autoreCanzone != null){
            emozioniCanzone = (ArrayList<Emozione>) interfaceNonLoggato.visualizzaEmozioni(titoloCanzone, autoreCanzone);

            if (!emozioniCanzone.isEmpty()) {

                int emozioniTotali=emozioniCanzone.size();
                int i=1;
                System.out.println("Emozione associata alla canzone: " + titoloCanzone + " sono: " + "\n");

                for (Emozione emozione : emozioniCanzone) {

                    System.out.println(i + " di " + emozioniTotali);
                    String nomeEmozione = emozione.getNomeEmozione();
                    //String tipoEmozione =emozione.getTipoEmozione();
                    //String spiegazioneEmozione= emozione.getSpiegazioneEmozione();
                    //int punteggioEmozione= emozione.getPunteggioEmozione();
                    double percentualeEmozione=emozione.getPercentualeEmozione();

                    System.out.println("la percentuale dell' emozione " +  "'" +nomeEmozione+ "'" + " è --> " + percentualeEmozione  );
                    System.out.println("Emozione: " + nomeEmozione );
                    //System.out.println("tipo: " + tipoEmozione);
                    //System.out.println("Spiegazione: " + spiegazioneEmozione);
                    //System.out.println("punteggio: " + punteggioEmozione);
                    System.out.println("percentuale: " + percentualeEmozione +"\n");
                    emozioniDellaCanzone.add(emozione);
                    i++;

                }
            } else {
                System.out.println("Nessuna emozione trovata per questa canzone.");
            }

        }
        else{
            System.out.println("Effettua prima una ricerca di una canzone.");
        }
        return emozioniDellaCanzone;
    }
    /*public static void inserisciNuovaEmozione() throws IOException, SQLException {

        Registry registroLoggato= LocateRegistry.getRegistry(1099);

        try{
            System.out.println("Procedura di collegamento al Server --> Iniziata");
            interfaceLoggato=(ServerInterfaceLoggato)registroLoggato.lookup("ServerEmotionalSongs");
            System.out.println("Procedura di collegamento al Server --> Completata");
            System.out.println("Collegameto al Server--> Riuscito" + "\n");
        }catch (Exception e){
            e.getMessage();
            System.out.println("Collegamento al Server--> Fallito" +"\n");

        }

        if(titoloCanzone != null && autoreCanzone != null){

            //Passo 1: inserre il nome dell'emozione da inserire (nome su DB)
            System.out.println("Scegli l'emozione da inserire:\n");
            String[] emozioni = {
                    "Amazement",
                    "Solemnity",
                    "Tenderness",
                    "Nostalgia",
                    "Calmness",
                    "Power",
                    "Joy",
                    "Tension",
                    "Sadness"
            };

            for (int i = 0; i < emozioni.length; i++) {
                System.out.println("Digitare " + (i + 1) + " per --> " + emozioni[i]);
            }

            System.out.print("Scelta: ");
            int scelta = 0;

            do {
                try {
                    scelta = Integer.parseInt(br.readLine());
                } catch (Exception e) {
                    System.out.println("Puoi inserire solo numeri compresi tra 1 e " + emozioni.length);
                    System.out.print("Reinserisci la scelta: ");
                    continue;
                }

                if (scelta < 1 || scelta > emozioni.length) {
                    System.out.println("Scelta non valida, inserisci un numero compreso tra 1 e " + emozioni.length);
                    System.out.print("Scelta: ");
                }
            } while (scelta < 1 || scelta > emozioni.length);

            emozioneScelta = emozioni[scelta - 1];


            // Passo 2: inserire una nota (nota su DB)
            do {
                System.out.println("Inserisci una breve nota (massimo 50 caratteri): ");
                notaEmozione = br.readLine().toLowerCase();

                if (notaEmozione.length() == 0) {
                    //System.out.println("Devi inserire almeno una spiegazione.");
                    System.out.println("Valore inserito--> NULL.");
                    notaEmozione=null;
                    break;

                } else if (notaEmozione.length() > 50) {
                    System.out.println("La spiegazione è troppo lunga. Inserisci al massimo 50 caratteri.");
                } else {
                    break;
                }
            } while (true);

            // Passo 3: inserire una spiegazione (spiegazione su DB)
            do {

                System.out.println("Inserisci una spiegazione (massimo 250 caratteri): ");
                spiegazioneEmozione = br.readLine().toLowerCase();

                if (spiegazioneEmozione.length() == 0) {
                    //System.out.println("Devi inserire almeno una spiegazione.");
                    System.out.println("Valore inserito--> NULL.");
                    spiegazioneEmozione=null;
                    break;
                }
                if (spiegazioneEmozione.length() > 250) {
                    System.out.println("La spiegazione è troppo lunga. Inserisci al massimo 50 caratteri.");
                } else {
                    break;
                }
            } while (true);

            // passo 4: Inserire un punteggio associato alla canzone (punteggio su DB)
            System.out.println("inserisci punteggio emopzione da 1 a 5: ");
            do {
                do {
                    try {
                        punteggioEmozione = Integer.parseInt(br.readLine());
                        controlloPunteggio = true;
                    } catch (Exception e) {
                        System.out.println("Puoi inserire solo un numero da 1 a 5 ");
                        System.out.println("reinserisci il punteggio: ");
                        controlloPunteggio = false;
                    }
                } while (!controlloPunteggio);

                // punteggio = br.read();
                if (punteggioEmozione < 1 || punteggioEmozione > 5) {
                    System.out.println("punteggio non valido, inserire da 1 a 5: ");
                }
            } while (punteggioEmozione < 1 || punteggioEmozione > 5);

            inserimentoEmozione= interfaceLoggato.inserisciEmozione(userID,emozioneScelta,titoloCanzone,autoreCanzone,notaEmozione,spiegazioneEmozione,punteggioEmozione);
            if (inserimentoEmozione) {
                System.out.println("Inserimento dell' Emozione su db avvenuto con successo");
            } else {
                System.out.println("Inserimento dell' Emozione su db non avvenuto --> ERRORE ");
            }
        }

        else{
            System.out.println("Effettua prima una ricerca di una canzone.");
        }
    }*/

    public static int registrazione(String nome, String cognome, String codiceFiscale, String via, String  numeroCivico, String cap, String comune, String provincia, String email, String userID, String password, String p2) throws NotBoundException, IOException, SQLException {

        boolean inserimentoRiuscito=false;

        System.out.println("Inizio procedura di registrazione utente\n");

        if(!MetodiControlli_Client.lunghezzaNominativo(nome)) {
            return 1;
        }

        if(!MetodiControlli_Client.lunghezzaNominativo(cognome)) {
             return 2;
        }

        if(!MetodiControlli_Client.formatoCF(codiceFiscale)){
            return 3;
        }

        if(!MetodiControlli_Client.formatoNumeroCivico(numeroCivico)){
            return 4;
        }
        if(!MetodiControlli_Client.formatoCAP(Integer.parseInt(cap))){
            return 5;
        }
        if(!MetodiControlli_Client.isNotNULL(comune)){
            return 6;
        }

        if(!MetodiControlli_Client.isNotNULL(provincia)){
            return 7;
        }
         if(!MetodiControlli_Client.formatoMail(email)){
             return 8;
         }

         if(!MetodiControlli_Client.formatoUser(userID)){
             return 9;
         }
        boolean esiste;

        //richiamo il medodo che ho nel servere per vedere se esiste l'utente  e aspetto la risposta del server
        esiste=interfaceNonLoggato.checkUserID(userID);
        //se la risposta e negativa esco dal while; altrimenti
        if(esiste){
            return 10;
        }

        if(!MetodiControlli_Client.FormatoPassword(password)){
            return 11;
        }

        if(!MetodiControlli_Client.checkPassordUguale(password,p2)){
            return 12;
        }

        System.out.println("Inizio procedura inserimento dati su DB");

        Utente utete=new Utente(nome, cognome,codiceFiscale,via,numeroCivico, cap,comune,provincia,email,userID,password);
        inserimentoRiuscito=interfaceNonLoggato.registrazione(utete);


        //inserimentoRiuscito=serInterfaccia.registrazione(nome, cognome,codiceFiscale,via,numeroCivico, cap,comune,provincia,email,userID,password);

        if(inserimentoRiuscito=true){
            System.out.println("Inserimento dati su DB --> Riuscito");
        }
        else{
            System.out.println("Inserimento dati su DB --> Fallito");
        }

        return 0;
    }
    public static void login(String userID, String password) throws IOException {

            isLoggato = interfaceNonLoggato.login(userID, password);

            if (isLoggato) {
                System.out.println("Sei Loggato");
                isLoggato = true;
            } else {

                System.out.println("Login non riuscito");
            }
        if (!isLoggato) {
            System.out.println("---------------Hai raggiunto il limite massimo di tentativi. Ritorno al menu principale.---------------" + "\n");
        }
    }
    public static int creaPlayList(String nomePlaylist) throws IOException, SQLException {
        Registry registroLoggato= LocateRegistry.getRegistry(1099);

        try{
            System.out.println("Procedura di collegamento al Server --> Iniziata");
            interfaceLoggato=(ServerInterfaceLoggato)registroLoggato.lookup("ServerEmotionalSongs");
            System.out.println("Procedura di collegamento al Server --> Completata");
            System.out.println("Collegameto al Server--> Riuscito" + "\n");
        }catch (Exception e){
            e.getMessage();
            System.out.println("Collegamento al Server--> Fallito" +"\n");

        }
        System.out.println("Digita il nome della Playlist da  creare: ");
        boolean esisteNomePlaylist;

        //richiamo il medodo che ho nel servere per vedere se esiste il nome della playlist e aspetto la risposta del server
        if(esisteNomePlaylist=interfaceLoggato.checkNomePlaylist(nomePlaylist)){
            return 1;
        }
        creazionePlaylist=interfaceLoggato.creaPlaylist(userID,nomePlaylist);

        if (creazionePlaylist) {
            return 0;
        } else {
            return -1;
        }
    }
    public static void visualizzaPlaylist(String userID) throws RemoteException, SQLException {

        Registry registroLoggato= LocateRegistry.getRegistry(1099);

        try{
            System.out.println("Procedura di collegamento al Server --> Iniziata");
            interfaceLoggato=(ServerInterfaceLoggato)registroLoggato.lookup("ServerEmotionalSongs");
            System.out.println("Procedura di collegamento al Server --> Completata");
            System.out.println("Collegameto al Server--> Riuscito" + "\n");
        }catch (Exception e){
            e.getMessage();
            System.out.println("Collegamento al Server--> Fallito" +"\n");

        }
        playlistUtente= (ArrayList<PlayList>) interfaceLoggato.VisualizzaPlaylist(userID);

        for (PlayList playList : playlistUtente) {

            String nomePlaylisyUtente = playList.getnomePlalist();
            System.out.println("Nome PlayList: " + nomePlaylisyUtente);

        }
    }
    public  static void eliminaPlalist(String nomePalylist) throws IOException, SQLException {
        Registry registroLoggato= LocateRegistry.getRegistry(1099);

        try{
            System.out.println("Procedura di collegamento al Server --> Iniziata");
            interfaceLoggato=(ServerInterfaceLoggato)registroLoggato.lookup("ServerEmotionalSongs");
            System.out.println("Procedura di collegamento al Server --> Completata");
            System.out.println("Collegameto al Server--> Riuscito" + "\n");
        }catch (Exception e){
            e.getMessage();
            System.out.println("Collegamento al Server--> Fallito" +"\n");

        }

        System.out.println("Digita il nome della Playlist da Eliminare: ");
        boolean esisteNomePlaylist=false;
        creazionePlaylist=interfaceLoggato.eliminaPlaylist(userID,nomePalylist);
        if (creazionePlaylist) {
            System.out.println("Creazione Playlist su db avvenuto con successo");
        } else {
            System.out.println("Creazione Playlist su db non avvenuto --> ERRORE ");
        }
    }

    public static void main(String[] args) throws ClassNotFoundException, IOException, NotBoundException, SQLException {
        String identifier = null;
            new Client().exec();
    }
}
