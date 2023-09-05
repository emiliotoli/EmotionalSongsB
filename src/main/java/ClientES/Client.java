package ClientES;

/**
 * Questa classe rappresenta un utente per l'applicazione di Emotional Songs.
 * Fornisce metodi che l'utente puù otilizzare  per l'interazione con il server, inclusa la ricerca di canzoni,
 * la gestione delle emozioni, la creazione di playlist e altro.
 *
 * @author Emilio Daverio - Stefano Farina
 *
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.SQLException;
import java.util.ArrayList;

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
    private static ArrayList<Canzone> informazioniCanzoneTitoloinPlaylist;
    private static ArrayList<Canzone> informazioniCanzoneAuoreAnno;
    private static ArrayList<Canzone> infoCanzoniPlaylist;
    private static ArrayList<Emozione> emozioniCanzone;
    private static ArrayList<PlayList> playlistUtente;
    private  static boolean emozioniPerCanzone;
    private static ArrayList<Emozione> emozioniDellaCanzone = new ArrayList<>();
    private static boolean inserimentoEmozione;
    private static boolean creazionePlaylist;
    private static boolean inserimentoCanzonePlaylist;
    private static boolean cancellazioneCanzonePlaylist;
    private static boolean cancellaPlaylist;
    public static boolean isLoggato = false;
    public static String idGlobale;
    static ServerInterfaceNonLoggato interfaceNonLoggato;
    static ServerInterfaceLoggato interfaceLoggato;

    /**@author Emilio Daverio - Stefano Farina
     * Crea una nuova istanza del client. Inizializza le interfacce del server a null.
     */
    public Client(){
        interfaceNonLoggato = null; // Inizializza con null
        interfaceLoggato = null; // Inizializza con null
    }

    /**@author Emilio Daverio - Stefano Farina
     * Metodo che serve per avviare il client e stabilire una connessione al server utilizzando RMI.
     *
     * @throws IOException Se si verifica un errore di I/O durante la connessione
     * @throws ClassNotFoundException Se si verifica un errore di classe non trovata
     * @throws NotBoundException   Se si verifica un errore di binding RMI
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
    }

    /**@author Emilio Daverio - Stefano Farina
     * Metodo che ricerca le informazioni sulle canzoni in base al titolo.
     *
     * @param titoloCanzone Titolo della canzone da cercare
     *
     * @return Una lista di oggetti Canzone che corrispondono al titolo specificato
     *
     * @throws IOException Se si verifica un errore di I/O durante la ricerca
     * @throws SQLException Se si verifica un errore di database SQL
     */
    public static ArrayList<Canzone> RicercaCanzoniTitolo(String titoloCanzone) throws IOException, SQLException {
            accessoServerNonLoggato();
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


    /**@author Emilio Daverio - Stefano Farina
     * Metodo che ricerca le informazioni sulle canzoni in base all'autore e all'anno.
     *
     * @param autoreCanzone Autore della canzone da cercare
     * @param annoCanzone   Anno della canzone da cercare
     *
     * @return Una lista di oggetti Canzone che corrispondono all'autore e all'anno specificati
     *
     * @throws IOException Se si verifica un errore di I/O durante la ricerca
     * @throws SQLException Se si verifica un errore di database SQL
     */
    public static  ArrayList<Canzone> RicercaCanzoniAutoreAnno(String autoreCanzone, int annoCanzone) throws IOException, SQLException {

        accessoServerNonLoggato();
        if (interfaceNonLoggato == null) {
            System.out.println("L'interfaccia non è stata inizializzata correttamente");
            return new ArrayList<>(); // o gestisci l'errore come meglio credi
        }
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


    /**@author Emilio Daverio - Stefano Farina
     * Metodo che visualizza le emozioni associate a una specifica canzone identificata dal titolo e dall'autore.
     *
     * @param titoloCanzone  Titolo della canzone da cercare
     * @param autoreCanzone  Autore della canzone da cercare
     *
     * @return Una lista di oggetti Emozione associate alla canzone specificata
     *
     * @throws SQLException Se si verifica un errore di SQL durante la query al database
     * @throws RemoteException Se si verifica un errore di comunicazione remota
     */
    public static ArrayList<Emozione> visualizzaEmozioniCanzone(String titoloCanzone, String autoreCanzone ) throws SQLException, RemoteException {

        accessoServerNonLoggato();
        if (interfaceNonLoggato == null) {
            System.out.println("L'interfaccia non è stata inizializzata correttamente");
            return new ArrayList<>(); // o gestisci l'errore come meglio credi
        }

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


    /**@author Emilio Daverio - Stefano Farina
     * Metodo che inserisce una nuova emozione associata a una canzone nel sistema.
     *
     * @param idUtente Identificativo dell'utente che inserisce l'emozione
     * @param nomeEmozione Nome dell'emozione da inserire
     * @param titoloCanzone Titolo della canzone a cui è associata l'emozione
     * @param autoreCanzone Autore della canzone a cui è associata l'emozione
     * @param notaEmozione Nota aggiuntiva sull'emozione
     * @param spiegazioneEmozione Spiegazione dettagliata dell'emozione
     * @param punteggioEmozione Punteggio associato all'emozione (deve essere compreso tra 1 e 5)
     *
     * @return 0 se l'inserimento è riuscito con successo, altrimenti un codice di errore
     *
     * @throws IOException Se si verifica un errore di I/O durante l'inserimento
     * @throws SQLException Se si verifica un errore di SQL durante l'inserimento nel database
     */
    public static int inserisciNuovaEmozione(String idUtente, String nomeEmozione,String titoloCanzone, String autoreCanzone, String notaEmozione, String spiegazioneEmozione, int punteggioEmozione  ) throws IOException, SQLException {

        accessoServerLoggato();
        if (interfaceNonLoggato == null) {
            System.out.println("L'interfaccia non è stata inizializzata correttamente");
            return -6;
        }
        boolean checkinfo;
        checkinfo=interfaceLoggato.checkInfoCanzone(titoloCanzone,autoreCanzone);
        if(checkinfo){
            if(notaEmozione != null && spiegazioneEmozione != null){
                if(notaEmozione.length()<=50){
                    if(spiegazioneEmozione.length()<=250){
                        if (punteggioEmozione >= 1 && punteggioEmozione <= 5){
                            emozioniPerCanzone= interfaceLoggato.inserisciEmozione(idUtente, nomeEmozione, titoloCanzone, autoreCanzone, notaEmozione, spiegazioneEmozione, punteggioEmozione);
                            return 0;
                        }
                        else{
                            return -1; //punteggio non compreso tra 1 e 5
                        }
                    }
                    else{
                        return -2; //Spiegazione troppo lunga. Non deve superare i 250 caratteri
                    }
                }
                else{
                    return -3; // nota troppo lunga. Non deve essere maggiore di 50 caratteri
                }
            }else{
                System.out.println("nota o punteggio nullo");
                return -4; //punteggio e nota non sono stati inseriti
            }
        }
        else{
            System.out.println("Effetta prima il login");
            return -5; //canzone e autore non corrispondono
        }
    }


    /**@author Emilio Daverio - Stefano Farina
     * Metodo che registra un nuovo utente nel sistema.
     *
     * @param nome Nome dell'utente
     * @param cognome Cognome dell'utente
     * @param codiceFiscale Codice fiscale dell'utente
     * @param via Via dell'utente
     * @param numeroCivico Numero civico dell'utente
     * @param cap CAP dell'utente
     * @param comune Comune dell'utente
     * @param provincia Provincia dell'utente
     * @param email Indirizzo email dell'utente
     * @param userID Nome utente (ID) scelto dall'utente
     * @param password Password dell'utente
     * @param p2 Conferma della password dell'utente
     *
     * @return 0 se la registrazione è riuscita con successo, altrimenti un codice di errore
     *
     * @throws NotBoundException Se si verifica un errore durante il binding RMI
     * @throws IOException Se si verifica un errore di I/O durante la registrazione
     * @throws SQLException Se si verifica un errore di SQL durante la registrazione nel database
     */
    public static int registrazione(String nome, String cognome, String codiceFiscale, String via, String  numeroCivico, String cap, String comune, String provincia, String email, String userID, String password, String p2) throws NotBoundException, IOException, SQLException {
        accessoServerNonLoggato();
        if (interfaceNonLoggato == null) {
            System.out.println("L'interfaccia non è stata inizializzata correttamente");
            return -1;
        }
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

        if(inserimentoRiuscito){
            System.out.println("Inserimento dati su DB --> Riuscito");
        }
        else{
            System.out.println("Inserimento dati su DB --> Fallito");
        }

        return 0;
    }


    /**@author Emilio Daverio - Stefano Farina
     * Metodo che esegue il login di un utente nel sistema.
     *
     * @param userID Nome utente (ID) dell'utente
     * @param password Password dell'utente
     *
     * @return True se il login è riuscito, altrimenti False
     *
     * @throws IOException Se si verifica un errore di I/O durante il login
     */
    public static boolean login(String userID, String password) throws IOException {

        accessoServerNonLoggato();
        if (interfaceNonLoggato == null) {
            System.out.println("L'interfaccia non è stata inizializzata correttamente");
            return false; // o gestisci l'errore come meglio credi
        }
        isLoggato = interfaceNonLoggato.login(userID, password);

        if (isLoggato) {
            System.out.println("Sei Loggato");
            isLoggato = true;
            idGlobale=userID;
            return true;
        } else {

            System.out.println("Login non riuscito");
            return false;
        }
    }


    /**@author Emilio Daverio - Stefano Farina
     * Metodo che crea una nuova playlist con il nome specificato per un utente identificato da userID.
     *
     * @param nomePlaylist Nome della playlist da creare
     * @param userID Identificativo dell'utente
     *
     * @return 0 se la creazione è riuscita con successo, 1 se il nome della playlist esiste già, -1 se la creazione non è riuscita
     *
     * @throws IOException Se si verifica un errore di I/O durante l'operazione
     * @throws SQLException Se si verifica un errore di SQL durante l'operazione
     */
    public static int creaPlayList(String nomePlaylist, String userID) throws IOException, SQLException {
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
            return 1; //nome già esistente
        }
        creazionePlaylist=interfaceLoggato.creaPlaylist(nomePlaylist, userID);

        if (creazionePlaylist) {
            return 0; //playlist ok
        } else {
            return -1; //creazione non riuscita
        }
    }


    /**@author Emilio Daverio - Stefano Farina
     * Metodo che visualizza le playlist associate a un utente identificato da userID.
     *
     * @param userID Identificativo dell'utente
     *
     * @return Una lista di oggetti PlayList associate all'utente
     *
     * @throws RemoteException Se si verifica un errore di comunicazione remota
     * @throws SQLException Se si verifica un errore di SQL durante l'operazione
     */
    public static ArrayList<PlayList> visualizzaPlaylist(String userID) throws RemoteException, SQLException {

        accessoServerLoggato();
        if (interfaceNonLoggato == null) {
            System.out.println("L'interfaccia non è stata inizializzata correttamente");
            return new ArrayList<>(); // o gestisci l'errore come meglio credi
        }
        playlistUtente= (ArrayList<PlayList>) interfaceLoggato.VisualizzaPlaylist(userID);

        for (PlayList playList : playlistUtente) {

            String nomePlaylisyUtente = playList.getnomePlalist();
            System.out.println("Nome PlayList: " + nomePlaylisyUtente);

        }
        return playlistUtente;
    }


    /**@author Emilio Daverio - Stefano Farina
     * Metodo che elimina una playlist associata all' utente identificato da userID.
     *
     * @param nomePlaylist Nome della playlist da eliminare
     *
     * @return 0 se l'eliminazione è riuscita con successo, 1 se l'eliminazione non è riuscita, -1 se il nome della playlist non esiste
     *
     * @throws IOException Se si verifica un errore di I/O durante l'operazione
     * @throws SQLException Se si verifica un errore di SQL durante l'operazione
     */
    public static int eliminaPlaylist(String nomePlaylist) throws IOException, SQLException {

        accessoServerLoggato();
        if (interfaceNonLoggato == null) {
            System.out.println("L'interfaccia non è stata inizializzata correttamente");
            return -2; // o gestisci l'errore come meglio credi
        }
        System.out.println("Digita il nome della Playlist da Eliminare: ");
        boolean esisteNomePlaylist;
        esisteNomePlaylist=interfaceLoggato.checkNomePlaylist(nomePlaylist);
        if(esisteNomePlaylist){
            cancellaPlaylist=interfaceLoggato.eliminaPlaylist(nomePlaylist, Client.idGlobale);
            if (cancellaPlaylist) {
                System.out.println("Cancellazione Playlist su db avvenuto con successo");
                return 0;
            } else {
                System.out.println("Cancellazione Playlist su db non avvenuto --> ERRORE ");
                return 1;
            }
        }
        else{
            return -1;
        }

    }


    /**@author Emilio Daverio - Stefano Farina
     * Metodo che aggiunge una canzone in una playlist specificata per un utente identificato da userID.
     *
     * @param nomePlaylist Nome della playlist a cui aggiungere la canzone
     * @param userID Identificativo dell'utente
     * @param titoloCanzone Titolo della canzone da aggiungere
     * @param autoreCanzone Autore della canzone da aggiungere
     *
     * @return 0 se l'aggiunta è riuscita con successo, 1 se l'aggiunta non è riuscita, -1 se il nome della playlist non esiste
     *
     * @throws RemoteException Se si verifica un errore di comunicazione remota
     * @throws SQLException Se si verifica un errore di SQL durante l'operazione
     */
    public static int aggiuntaCanzoniPlaylist (String nomePlaylist, String userID, String titoloCanzone, String autoreCanzone) throws RemoteException, SQLException {
        accessoServerLoggato();
        if (interfaceNonLoggato == null) {
            System.out.println("L'interfaccia non è stata inizializzata correttamente");
            return -2;
        }
        //controllo playlist
        boolean controlloNomePlaylist;
        controlloNomePlaylist= interfaceLoggato.checkNomePlaylist(nomePlaylist);
        if(controlloNomePlaylist){
            //ricerca canzone e aggiungi
            inserimentoCanzonePlaylist = interfaceLoggato.aggiuntaCanzoniPlaylist(nomePlaylist,userID,titoloCanzone,autoreCanzone);
            if(inserimentoCanzonePlaylist){
                return 0;// inserimanto efetuato con successo
            }
            else {
                return 1;//erore durante l'inserimento
            }
        }
        else{
            return -1; //nome playlist inesistente
        }
    }


    /**@author Emilio Daverio - Stefano Farina
     * Metodo che elimina una canzone da una playlist specificata per un utente identificato da userID.
     *
     * @param nomePlaylist Nome della playlist da cui eliminare la canzone
     * @param userID Identificativo dell'utente
     * @param titoloCanzone Titolo della canzone da eliminare
     * @param autoreCanzone Autore della canzone da eliminare
     *
     * @return 0 se l'eliminazione è riuscita con successo, 1 se l'eliminazione non è riuscita, -1 se il nome della playlist non esiste, -3 se le informazioni sulla canzone non sono state trovate
     *
     * @throws RemoteException Se si verifica un errore di comunicazione remota
     * @throws SQLException Se si verifica un errore di SQL durante l'operazione
     */
    public static int eliminaCanzoniPlaylist(String nomePlaylist, String userID, String titoloCanzone, String autoreCanzone) throws RemoteException, SQLException {
        accessoServerLoggato();
        if (interfaceNonLoggato == null) {
            System.out.println("L'interfaccia non è stata inizializzata correttamente");
            return -2;
        }
        boolean controlloNomePlaylist;
        boolean chechinfocanzone;
        controlloNomePlaylist= interfaceLoggato.checkNomePlaylist(nomePlaylist);
        if(controlloNomePlaylist){
            chechinfocanzone= interfaceLoggato.checkInfoCanzone(titoloCanzone,autoreCanzone);
            if(chechinfocanzone){
                cancellazioneCanzonePlaylist= interfaceLoggato.eliminaCanzoniPlaylist(nomePlaylist,userID,titoloCanzone,autoreCanzone);
                if(cancellazioneCanzonePlaylist){
                    return 0; //cancellazione effettuata con successo
                }
                else{
                    return 1;
                }
            }
            else{
                return -3;
            }
        }
        else {
            return -1; // nome playlist inesistente
        }
    }


    /**@author Emilio Daverio - Stefano Farina
     * Metodo che visualizza le canzoni associate a una playlist specificata per un utente identificato da userID.
     *
     * @param idUtente Identificativo dell'utente
     * @param nomePlaylist Nome della playlist da cui visualizzare le canzoni
     *
     * @return Una lista di oggetti Canzone associate alla playlist specificata
     *
     * @throws RemoteException Se si verifica un errore di comunicazione remota
     * @throws SQLException Se si verifica un errore di SQL durante l'operazione
     */
    public static ArrayList<Canzone> visualizzaCanzoniPlaylist(String idUtente, String nomePlaylist) throws RemoteException, SQLException {
        accessoServerLoggato();
        if (interfaceLoggato == null) {
            System.out.println("L'interfaccia non è stata inizializzata correttamente");
            return new ArrayList<>(-1);
        }
        boolean controlloNomePlaylist;
        controlloNomePlaylist= interfaceLoggato.checkNomePlaylist(nomePlaylist);
        if(controlloNomePlaylist){
            infoCanzoniPlaylist= (ArrayList<Canzone>) interfaceLoggato.VisualizzaCanzoniPlaylist(nomePlaylist, idUtente);

            //elaboro la risposta
            if (!infoCanzoniPlaylist.isEmpty()){
                for (Canzone canzone : infoCanzoniPlaylist) {
                    titoloCanzone = canzone.getTitoloCanzone();
                    autoreCanzone = canzone.getAutoreCanzone();

                    System.out.println("Titolo: " + titoloCanzone);
                    System.out.println("Autore: " + autoreCanzone);
                    System.out.println();
                }
            }
            else {
                System.out.println("Canzone non trovata");
            }

            return infoCanzoniPlaylist;
        }
        return null;

    }



    /**@author Emilio Daverio - Stefano Farina
     * Metodo che effettua l'accesso al server quando l'utente non è loggato.
     * Questo metodo stabilisce una connessione al server utilizzando il registro RMI sulla porta 1099
     * e recupera l'interfaccia ServerInterfaceNonLoggato per interagire con il server.
     *
     * @throws RemoteException Se si verifica un errore di comunicazione remota durante la connessione al server
     */
    private static void accessoServerNonLoggato() throws RemoteException {
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
        }
    }

    /**@author Emilio Daverio - Stefano Farina
     * Metodo che effettua l'accesso al server quando l'utente è loggato.
     * Questo metodo stabilisce una connessione al server utilizzando il registro RMI sulla porta 1099
     * e recupera l'interfaccia ServerInterfaceLoggato per interagire con il server.
     *
     * @throws RemoteException Se si verifica un errore di comunicazione remota durante la connessione al server
     */
    private static void accessoServerLoggato() throws RemoteException {
        Registry registroLoggato= LocateRegistry.getRegistry(1099);

        try{
            System.out.println("Procedura di collegamento al Server --> Iniziata");
            //interfaceNonLoggato=(ServerInterfaceNonLoggato)registroNonLoggato.lookup("ServerEmotionalSongs");
            interfaceLoggato = (ServerInterfaceLoggato) registroLoggato.lookup("ServerEmotionalSongs");
            System.out.println("Procedura di collegamento al Server --> Completata");
            System.out.println("Collegameto al Server--> Riuscito" + "\n");
        }catch (Exception e){
            e.getMessage();
            System.out.println("Collegamento al Server--> Fallito" +"\n");
        }
    }


    public static void main(String[] args) throws ClassNotFoundException, IOException, NotBoundException, SQLException {
        String identifier = null;
            new Client().exec();
    }
}
