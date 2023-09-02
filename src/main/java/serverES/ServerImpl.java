package serverES;

import ClientES.Emozione;
import ClientES.PlayList;
import ClientES.Utente;
import ClientES.Canzone;
import DataBase.ConnessioneDBImpl;


import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * La classe `ServerImpl` rappresenta un'implementazione remota di un server
 * che fornisce diverse operazioni sia per utenti loggati che non loggati.
 * Implementa le interfacce `ServerInterfaceNonLoggato` e `ServerInterfaceLoggato`
 * per le chiamate remote di metodi (RMI).
 * Questa classe estende anche `UnicastRemoteObject` ed implementa `Remote`.
 *
 * @author Stefano Farina
 */

public class ServerImpl extends UnicastRemoteObject implements ServerInterfaceNonLoggato, ServerInterfaceLoggato, Remote {

    private static final long serialVersionUid = 1L;
    private boolean dbExists=false;

    /**@author Stefano Farina
     * Costruisce un oggetto `ServerImpl`.
     *
     * @throws RemoteException Se si verifica un errore di comunicazione remota.
     */
    protected ServerImpl() throws RemoteException {
        super();
    }

    /**@author Stefano Farina
     * etodo che registra un nuovo utente nell'applicazione.
     *
     * @param utente Contiene le ie informazioni dell'utente da registrare.
     *
     * @return `true` se la registrazione ha successo, `false` altrimenti.
     *
     * @throws RemoteException Se si verifica un errore di comunicazione remota.
     * @throws SQLException Se si verifica un errore nel database SQL.
     */
    @Override
    public synchronized boolean registrazione(Utente utente) throws RemoteException, SQLException {

        Connection connInsertUtente = null;
        PreparedStatement preparedStatement = null;
        int capValue;
        try{

            // Connection con=ConnessioneDB.istance.getConnectionIstance();
            connInsertUtente=new ConnessioneDBImpl().getConnection();

            String queryInsert = "INSERT INTO utentiregistrati (nome, cognome, codicefiscale, via, numerocivico, comune, provincia, cap, userid, email, password) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
            preparedStatement=connInsertUtente.prepareStatement(queryInsert);

            preparedStatement.setString(1, utente.getNome());
            preparedStatement.setString(2, utente.getCognome());
            preparedStatement.setString(3, utente.getCodiceFiscale());
            preparedStatement.setString(4, utente.getVia());
            preparedStatement.setString(5, utente.getNumeroCivico());
            preparedStatement.setString(6, utente.getComune());
            preparedStatement.setString(7, utente.getProvincia());

            try {
                capValue = Integer.parseInt(utente.getCap());
                preparedStatement.setInt(8, capValue);
            } catch (NumberFormatException e) {
                // Gestisci l'eccezione, ad esempio fornendo un valore di default o segnalando un errore
                System.out.println("Il valore di 'cap' non è un numero intero valido: " + utente.getCap());
                return false; // o l'azione appropriata per la gestione degli errori
            }
            //preparedStatement.setString(8, utente.getCap());
            preparedStatement.setString(9, utente.getUserID());
            preparedStatement.setString(10, utente.getEmail());
            preparedStatement.setString(11, utente.getPassword());

            System.out.println("query contenete: " +  queryInsert);
            preparedStatement.executeUpdate();

            preparedStatement.close();
            connInsertUtente.close();

            return true;
        }catch (Exception e) {
            System.out.println("errore durante l'inserimento");
            e.getMessage();
            return false;
        }
        finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connInsertUtente != null) {
                connInsertUtente.close();
            }
        }
    }
    /*public synchronized boolean registrazione(String nome, String cognome, String codiceFiscale, String via, String numeroCivico, String cap , String comune, String provincia, String email, String userID, String password) throws RemoteException, SQLException {
        Connection connInsertUtente = null;
        PreparedStatement preparedStatement = null;
        int capValue;

        try {
            connInsertUtente = new ConnessioneDBImpl().getConnection();

            String queryInsert = "INSERT INTO utentiregistrati (nome, cognome, codicefiscale, via, numerocivico, comune, provincia, cap, userid, email, password) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
            preparedStatement = connInsertUtente.prepareStatement(queryInsert);

            preparedStatement.setString(1, nome);
            preparedStatement.setString(2, cognome);
            preparedStatement.setString(3, codiceFiscale);
            preparedStatement.setString(4, via);
            preparedStatement.setString(5, numeroCivico);
            preparedStatement.setString(6, comune);
            preparedStatement.setString(7, provincia);
            try {
                capValue = Integer.parseInt(cap);
                preparedStatement.setInt(8, capValue);
            } catch (NumberFormatException e) {
                // Gestisci l'eccezione, ad esempio fornendo un valore di default o segnalando un errore
                System.out.println("Il valore di 'cap' non è un numero intero valido: " + cap);
                return false; // o l'azione appropriata per la gestione degli errori
            }
            //preparedStatement.setString(8, cap );
            preparedStatement.setString(9, userID);
            preparedStatement.setString(10, email);
            preparedStatement.setString(11, password);

            System.out.println("Query contenente: " + queryInsert);
            int rowsInserted = preparedStatement.executeUpdate();

            return rowsInserted > 0;
        } catch (Exception e) {
            System.out.println("Errore durante l'inserimento");
            e.printStackTrace();
            return false;
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connInsertUtente != null) {
                connInsertUtente.close();
            }
        }
    }*/

    /**@author Stefano Farina
     * Metodo che effettua l'accesso di un utente con l'ID utente e la password forniti.
     *
     * @param userId   L'ID dell'utente.
     * @param password La password dell'utente.
     *
     * @return `true` se l'accesso ha successo, `false` altrimenti.
     *
     * @throws RemoteException Se si verifica un errore di comunicazione remota.
     */
    public synchronized boolean login(String userId, String password) throws RemoteException{
        Connection connCheckLogin = null;
        PreparedStatement preparedStatement = null;
        try{
            //apro la connessione con il DB

            connCheckLogin = new ConnessioneDBImpl().getConnection();

            String queryLogin="SELECT COUNT(*) FROM utentiregistrati WHERE  userid= ? AND password = ?";
            preparedStatement=connCheckLogin.prepareStatement(queryLogin);

            preparedStatement.setString(1,userId);
            preparedStatement.setString(2,password);

            //eseguo la query
            ResultSet resultSet=preparedStatement.executeQuery();

            //risposta query
            resultSet.next();
            int count = resultSet.getInt(1);
            boolean esistonoDati = (count > 0);
            return esistonoDati;

        }catch ( Exception e){
            e.getMessage().toString();
            return false;
        }
    }

    /**@author Stefano Farina
     * Metodo che verifica se un identificativo utente è già in uso.
     *
     * @param userID Identificativo dell'utente da verificare.
     *
     * @return True se l'identificativo è disponibile, false se è già in uso.
     *
     * @throws RemoteException Lanciato in caso di errore durante l'invocazione remota.
     * @throws SQLException Lanciato in caso di errore nell'accesso al database.
     */
    public boolean checkUserID(String userID) throws RemoteException {
        try {

            // Ottieni l'istanza di connessione al database
            Connection connCheckID = ConnessioneDBImpl.getInstance().getConnection();
            PreparedStatement preparedStatement=null;
            String querycheck = "SELECT COUNT(*) FROM utentiregistrati WHERE  userid= ? ";
            preparedStatement = connCheckID.prepareStatement(querycheck);
            preparedStatement.setString(1, userID);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            int count = resultSet.getInt(1);
            boolean exists = count > 0;
            return exists;
        } catch (Exception e) {
            e.getMessage().toString();
            return false;
        }
    }

    /**@author Emilio Daverio
     * Metodo che ricerca le canzoni per titolo.
     *
     * @param titolo Titolo della canzone da cercare.
     *
     * @return Una lista di oggetti 'Canzone' che corrispondono al titolo specificato e che contiene tutte le informazioni associate alla canzone.
     *
     * @throws RemoteException Lanciato in caso di errore durante l'invocazione remota.
     * @throws SQLException Lanciato in caso di errore nell'accesso al database.
     */
    public synchronized List<Canzone> ricercaCanzoneTitolo(String titolo) throws RemoteException, SQLException {
        Connection searchByTitle = null;
        PreparedStatement preparedStatement = null;

        List<Canzone> infoCanzone = new ArrayList<>();
        try{
            searchByTitle= new ConnessioneDBImpl().getConnection();
            String query = "SELECT * FROM canzone WHERE titolo LIKE ?";
            preparedStatement = searchByTitle.prepareStatement(query);
            //preparedStatement.setString(1, titolo);
            preparedStatement.setString(1, "%" + titolo + "%");

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet != null ) {
                while (resultSet.next()) {
                    String titoloCanzone = resultSet.getString("titolo");
                    String autoreCanzone = resultSet.getString("autore");
                    int annoCanzone = resultSet.getInt("anno");

                    Canzone canzone = new Canzone(titoloCanzone, autoreCanzone, annoCanzone);
                    infoCanzone.add(canzone);

                    //stampo i valori presi
                    System.out.println("Titolo: " + titoloCanzone);
                    System.out.println("Autore: " + autoreCanzone);
                    System.out.println("Anno: " + annoCanzone);
                    System.out.println();
                }
            }
            else {
                infoCanzone = null; // La canzone non è stata trovata, impostiamo l'array a null
            }


            return infoCanzone;
        }catch(Exception e){
            System.out.println("Errore durante la ricerca della canzone per titolo: " + e.getMessage());
            throw new RemoteException("Canzone --> NON Trovata", e);
        }
        finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (searchByTitle != null) {
                searchByTitle.close();
            }
        }
    }

    /**@author Emilio Daverio
     * Metodo che ricerca le canzoni per autore e anno.
     *
     * @param autore Autore della canzone da cercare.
     * @param anno   Anno di pubblicazione della canzone da cercare.
     *
     * @return Una lista di oggetti 'Canzone' che corrispondono all'autore e all'anno specificati e che contiene tutte le informazioni associate alla canzone.
     *
     * @throws RemoteException Lanciato in caso di errore durante l'invocazione remota.
     * @throws SQLException Lanciato in caso di errore nell'accesso al database.
     */
    public  synchronized List<Canzone> ricercaCanzoneAutoreAnno(String autore, int anno) throws RemoteException, SQLException{
        Connection searchByTitle = null;
        PreparedStatement preparedStatement = null;

        List<Canzone> infoCanzone = new ArrayList<>();
        try{
            searchByTitle= new ConnessioneDBImpl().getConnection();
            String query = "select * from canzone where autore= ? and anno= ? ";

            preparedStatement = searchByTitle.prepareStatement(query);
            preparedStatement.setString(1, autore);
            preparedStatement.setInt(2, anno);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet != null ) {
                while (resultSet.next()) {
                    String titoloCanzone = resultSet.getString("titolo");
                    String autoreCanzone = resultSet.getString("autore");
                    int annoCanzone = resultSet.getInt("anno");

                    Canzone canzone = new Canzone(titoloCanzone, autoreCanzone, annoCanzone);
                    infoCanzone.add(canzone);

                    //stampo i valori presi
                    System.out.println("Titolo: " + titoloCanzone);
                    System.out.println("Autore: " + autoreCanzone);
                    System.out.println("Anno: " + annoCanzone);
                    System.out.println();
                }
            }
            else {
                infoCanzone = null; // La canzone non è stata trovata, impostiamo l'array a null
            }

            return infoCanzone;
        }catch(Exception e){
            System.out.println("Errore durante la ricerca della canzone per titolo: " + e.getMessage());
            throw new RemoteException("Canzone --> NON Trovata", e);
        }
        finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (searchByTitle != null) {
                searchByTitle.close();
            }
        }
    }

    /**@author Emilio Daverio
     * Metodo che visualizza le emozioni associate a una canzone specifica.
     *
     * @param titoloCanzone Titolo della canzone per cui visualizzare le emozioni.
     * @param autoreCanzone Autore della canzone per cui visualizzare le emozioni.
     *
     * @return Una lista di oggetti 'Emozione' associate alla canzone specificata.
     *
     * @throws RemoteException Lanciato in caso di errore durante l'invocazione remota.
     * @throws SQLException Lanciato in caso di errore nell'accesso al database.
     */
    public  synchronized List<Emozione> visualizzaEmozioni(String titoloCanzone, String autoreCanzone) throws SQLException, RemoteException {
        Connection searchEmozione = null;
        PreparedStatement preparedStatement = null;
        List<Emozione> infoEmozione = new ArrayList<>();
        try {
            searchEmozione= new ConnessioneDBImpl().getConnection();
            String query = "SELECT Associa.titolo, Associa.autore, Associa.nome, COUNT(Associa.nome) AS num_emozioni, totali.numero_emozioni_totali, ROUND(((COUNT(Associa.nome)*1.0 /totali.numero_emozioni_totali * 1.0)*100),2) as percentuale\n" +
                    "FROM Associa \n" +
                    "JOIN (\n" +
                    "  SELECT titolo, autore, COUNT(nome) AS numero_emozioni_totali\n" +
                    "  FROM Associa \n" +
                    "  WHERE titolo = ? AND autore = ? \n" +
                    "  GROUP BY titolo, autore\n" +
                    ") AS totali ON Associa.titolo = totali.titolo AND Associa.autore = totali.autore\n" +
                    "WHERE Associa.titolo = ? AND Associa.autore = ? \n" +
                    "GROUP BY Associa.titolo, Associa.autore, Associa.nome, totali.numero_emozioni_totali;";

            preparedStatement = searchEmozione.prepareStatement(query);
            preparedStatement.setString(1, titoloCanzone);
            preparedStatement.setString(2, autoreCanzone);
            preparedStatement.setString(3,titoloCanzone);
            preparedStatement.setString(4,autoreCanzone);

            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet!=null){
                while(resultSet.next()){
                    String nomeEmozione=resultSet.getString("nome");
                    //String tipoEmozione=resultSet.getString("tipo");
                    //String spiegazioneEmozione=resultSet.getString("spiegazione");
                    //int punteggioEmozione=resultSet.getInt("punteggio");
                    double percentuale= resultSet.getDouble("percentuale");

                    Emozione emozione = new Emozione (nomeEmozione, percentuale);
                    infoEmozione.add(emozione);
                }
            }
            else {
                infoEmozione = null; // L'emozione non è stata trovata, impostiamo l'array a null
            }
            return infoEmozione;
        }catch(Exception e){
            System.out.println("Errore durante la ricerca della canzone per titolo: " + e.getMessage());
            throw new RemoteException("Canzone --> NON Trovata", e);
        }
        finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (searchEmozione != null) {
                searchEmozione.close();
            }
        }
    }

    /**@author Stefano Farina
     * Metodo che crea una nuova playlist per un utente specifico.
     *
     * @param userID      Identificativo dell'utente proprietario della playlist.
     * @param nomePlaylist Nome della nuova playlist da creare.
     *
     * @return True se la creazione della playlist ha avuto successo, false altrimenti.
     *
     * @throws RemoteException Lanciato in caso di errore durante l'invocazione remota.
     * @throws SQLException Lanciato in caso di errore nell'accesso al database.
     */
    public synchronized boolean creaPlaylist( String nomePlaylist, String userID) throws SQLException {
        Connection insertPlaylist = null;
        PreparedStatement preparedStatement = null;
        try{
            //apro la connessione con il DB

            insertPlaylist = new ConnessioneDBImpl().getConnection();

            String queryLogin= "INSERT INTO playlist (nome, idutente ) VALUES (?, ?)";
            preparedStatement=insertPlaylist.prepareStatement(queryLogin);

            preparedStatement.setString(1,nomePlaylist);
            preparedStatement.setString(2,userID );

            //eseguo la query
            preparedStatement.executeUpdate();

            preparedStatement.close();
            insertPlaylist.close();

            return true;
        }catch (Exception e) {
            System.out.println("errore durante l'inserimento");
            e.getMessage();
            return false;
        }
        finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (insertPlaylist != null) {
                insertPlaylist.close();
            }
        }
    }

    /**@author Stefano Farina
     * Metodo che verifica se un nome della playlist è già in uso per quell'utente.
     *
     * @param nomePlaylist Nome della playlist da verificare.
     *
     * @return True se il nome della playlist è disponibile per l'utente, false se è già in uso.
     *
     * @throws RemoteException Lanciato in caso di errore durante l'invocazione remota.
     * @throws SQLException Lanciato in caso di errore nell'accesso al database.
     */
    public synchronized boolean checkNomePlaylist(String nomePlaylist) throws SQLException{
        try {
            // Ottieni l'istanza di connessione al database
            Connection connCheckID = ConnessioneDBImpl.getInstance().getConnection();
            PreparedStatement preparedStatement=null;

            String querycheck = " SELECT COUNT(*) FROM playlist where nome=? ";
            preparedStatement = connCheckID.prepareStatement(querycheck);

            preparedStatement.setString(1, nomePlaylist);

            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            int count = resultSet.getInt(1);
            boolean exists = count > 0;

            return exists;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**@author Stefano Farina
     * Metodo che elimina una playlist esistente per un utente specifico.
     *
     * @param userID Identificativo dell'utente proprietario della playlist.
     * @param nomePalylist Nome della playlist da eliminare.
     *
     * @return True se l'eliminazione della playlist ha avuto successo, false altrimenti.
     *
     * @throws RemoteException Lanciato in caso di errore durante l'invocazione remota.
     * @throws SQLException Lanciato in caso di errore nell'accesso al database.
     */
    public synchronized boolean eliminaPlaylist(String nomePalylist, String userID )  throws RemoteException, SQLException {
        Connection deletePlaylist = null;
        PreparedStatement preparedStatement = null;
        try{
            //apro la connessione con il DB

            deletePlaylist = new ConnessioneDBImpl().getConnection();

            String queryDelete= "DELETE FROM  playlist  WHERE nome=? and idutente=?";
            preparedStatement=deletePlaylist.prepareStatement(queryDelete);

            preparedStatement.setString(1,nomePalylist);
            preparedStatement.setString(2,userID );

            //eseguo la query
            preparedStatement.executeUpdate();

            preparedStatement.close();
            deletePlaylist.close();

            return true;
        }catch (Exception e) {
            System.out.println("errore durante l'eliminazione della Playlist");
            e.getMessage();
            return false;
        }
        finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (deletePlaylist != null) {
                deletePlaylist.close();
            }
        }
    }

    /**@author Stefano Farina
     * Metodo che aggiunge una canzone a una playlist specifica di un utente.
     *
     * @param nomePlaylist Nome della playlist in cui aggiungere la canzone.
     * @param idUtente Identificativo dell'utente proprietario della playlist.
     * @param titoloCanzone Titolo della canzone da aggiungere.
     * @param autoreCanzone Autore della canzone da aggiungere.
     *
     * @return True se l'aggiunta della canzone alla playlist ha avuto successo, false altrimenti.
     *
     * @throws RemoteException Lanciato in caso di errore durante l'invocazione remota.
     * @throws SQLException Lanciato in caso di errore nell'accesso al database.
     */
    public synchronized boolean aggiuntaCanzoniPlaylist(String nomePlaylist, String idUtente, String titoloCanzone, String autoreCanzone) throws RemoteException, SQLException {
        Connection aggiungiCanzone = null;
        PreparedStatement preparedStatement = null;
        try {
            aggiungiCanzone = new ConnessioneDBImpl().getConnection();
            String query = "INSERT INTO Composta (nome, idUtente, titolo, autore) VALUES (?, ?, ?, ?)";

            preparedStatement = aggiungiCanzone.prepareStatement(query);
            preparedStatement.setString(1, nomePlaylist);
            preparedStatement.setString(2, idUtente);
            preparedStatement.setString(3, titoloCanzone);
            preparedStatement.setString(4, autoreCanzone);

            preparedStatement.executeUpdate(); // Esegui l'inserimento e ottieni il numero di righe interessate
            System.out.println("inserimento canzone nella playlist" + nomePlaylist + " andato a buon fine");
            return true;
        } catch (Exception e) {
            System.out.println("Errore durante l'inserimento della canzone nella playlist: " + e.getMessage());
            return false;
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (aggiungiCanzone != null) {
                aggiungiCanzone.close();
            }
        }
    }

    /**@author Stefano Farina
     * Metoodo che rimuove una canzone da una playlist specifica di un utente.
     *
     * @param nomePlaylist Nome della playlist da cui rimuovere la canzone.
     * @param idUtente Identificativo dell'utente proprietario della playlist.
     * @param titoloCanzone Titolo della canzone da rimuovere.
     * @param autoreCanzone Autore della canzone da rimuovere.
     *
     * @return True se la rimozione della canzone dalla playlist ha avuto successo, false altrimenti.
     *
     * @throws RemoteException Lanciato in caso di errore durante l'invocazione remota.
     * @throws SQLException Lanciato in caso di errore nell'accesso al database.
     */
    public synchronized boolean eliminaCanzoniPlaylist(String nomePlaylist, String idUtente, String titoloCanzone, String autoreCanzone) throws SQLException {

        Connection eliminaCanzone = null;
        PreparedStatement preparedStatement = null;
        try {
            eliminaCanzone= new ConnessioneDBImpl().getConnection();
            String query = "DELETE FROM Composta WHERE nome=?  AND idUtente=? AND titolo=? AND autore=? ";

            preparedStatement = eliminaCanzone.prepareStatement(query);
            preparedStatement.setString(1, nomePlaylist );
            preparedStatement.setString(2, idUtente );
            preparedStatement.setString(3,titoloCanzone);
            preparedStatement.setString(4,autoreCanzone);

            preparedStatement.executeUpdate();

            preparedStatement.close();
            eliminaCanzone.close();
            System.out.println("cancellazione della canzone dalla playlist effettuata: ");
            return true;
        }catch(Exception e){
            System.out.println("Errore durante la cancellazione della canzone dalla playlist: " + e.getMessage());
            return false;
        }
        finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (eliminaCanzone != null) {
                eliminaCanzone.close();
            }
        }
    }

    /**@author Stefano Farina
     * Metodo che visualizza le canzoni presenti in una specifica playlist di un utente.
     *
     * @param nomePlaylist Nome della playlist da cui visualizzare le canzoni.
     * @param userID Identificativo dell'utente proprietario della playlist.
     *
     * @return Una lista di oggetti 'Canzone' presenti nella playlist specificata.
     *
     * @throws RemoteException Lanciato in caso di errore durante l'invocazione remota.
     * @throws SQLException Lanciato in caso di errore nell'accesso al database.
     */
    public synchronized List<Canzone> VisualizzaCanzoniPlaylist(String nomePlaylist, String userID) throws RemoteException, SQLException {
        Connection searchSong = null;
        PreparedStatement preparedStatement = null;
        List<Canzone> infoSongInPlaylist = new ArrayList<>();
        try {
            searchSong= new ConnessioneDBImpl().getConnection();
            String query = "SELECT titolo, autore FROM composta WHERE nome = ? AND idUtente = ?";

            preparedStatement = searchSong.prepareStatement(query);
            preparedStatement.setString(1, nomePlaylist);
            preparedStatement.setString(2, userID);

            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet!=null){
                while(resultSet.next()){
                    String titoloCanzone = resultSet.getString("titolo");
                    String autore = resultSet.getString("autore");

                    Canzone canzone = new Canzone (titoloCanzone,autore);
                    infoSongInPlaylist.add(canzone);
                }
            }
            else {
                infoSongInPlaylist = null;
            }
            return infoSongInPlaylist;
        }catch(Exception e){
            System.out.println("Errore durante la ricerca della PlayList: " + e.getMessage());
            throw new RemoteException(" PlayList--> NON Trovata", e);
        }
        finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (searchSong != null) {
                searchSong.close();
            }
        }
    }

    /**@author Stefano Farina
     * Metodo che visualizza tutte le playlist di un utente.
     *
     * @param userID Identificativo dell'utente di cui visualizzare le playlist.
     *
     * @return Una lista di oggetti 'PlayList' dell'utente specificato.
     *
     * @throws RemoteException Lanciato in caso di errore durante l'invocazione remota.
     * @throws SQLException Lanciato in caso di errore nell'accesso al database.
     */
    public synchronized List<PlayList> VisualizzaPlaylist(String userID) throws RemoteException, SQLException {
        Connection searchPlaylist = null;
        PreparedStatement preparedStatement = null;
        List<PlayList> infoPlalist = new ArrayList<>();
        try {
            searchPlaylist= new ConnessioneDBImpl().getConnection();
            String query = "SELECT * FROM playlist WHERE idutente=?";

            preparedStatement = searchPlaylist.prepareStatement(query);
            preparedStatement.setString(1, userID);

            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet!=null){
                while(resultSet.next()){
                    String nomePlalist=resultSet.getString("nome");
                    String nomeUtente=resultSet.getString("idutente");

                    PlayList playList = new PlayList (nomeUtente,nomePlalist);
                    infoPlalist.add(playList);
                }
            }
            else {
                infoPlalist = null; // L'emozione non è stata trovata, impostiamo l'array a null
            }
            return infoPlalist;
        }catch(Exception e){
            System.out.println("Errore durante la ricerca della PlayList: " + e.getMessage());
            throw new RemoteException(" PlayList--> NON Trovata", e);
        }
        finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (searchPlaylist != null) {
                searchPlaylist.close();
            }
        }
    }

    /**@author Stefano Farina
     * Metodo che inserisce un'emozione associata a una specifica canzone.
     *
     * @param userID Identificativo dell'utente che inserisce l'emozione.
     * @param emozioneScelta Tipo di emozione scelta dall'utente.
     * @param titoloCanzone Titolo della canzone a cui associare l'emozione.
     * @param autoreCanzone Autore della canzone a cui associare l'emozione.
     * @param notaEmozione Nota o commento aggiuntivo sull'emozione.
     * @param spiegazioneEmozione Spiegazione dettagliata dell'emozione.
     * @param punteggioEmozione Punteggio dell'emozione (ad esempio, su una scala da 1 a 5).
     *
     * @return True se l'inserimento dell'emozione ha avuto successo, false altrimenti.
     *
     * @throws RemoteException Lanciato in caso di errore durante l'invocazione remota.
     * @throws SQLException Lanciato in caso di errore nell'accesso al database.
     */
    public synchronized boolean inserisciEmozione(String userID, String emozioneScelta, String titoloCanzone, String autoreCanzone, String notaEmozione, String spiegazioneEmozione,  int punteggioEmozione) throws SQLException {
        Connection insertEmozione = null;
        PreparedStatement preparedStatement = null;
        try{
            //apro la connessione con il DB

            insertEmozione = new ConnessioneDBImpl().getConnection();

            String queryLogin= "INSERT INTO associa (idutente, nome, titolo, autore, nota, spiegazione, punteggio ) VALUES (?, ?, ?, ?, ?, ?, ?)";
            preparedStatement=insertEmozione.prepareStatement(queryLogin);

            preparedStatement.setString(1,userID);
            preparedStatement.setString(2,emozioneScelta);
            preparedStatement.setString(3,titoloCanzone);
            preparedStatement.setString(4,autoreCanzone);
            preparedStatement.setString(5,notaEmozione);
            preparedStatement.setString(6,spiegazioneEmozione);
            preparedStatement.setInt(7,punteggioEmozione);

            //eseguo la query
            preparedStatement.executeUpdate();

            preparedStatement.close();
            insertEmozione.close();

            return true;
        }catch (Exception e) {
            System.out.println("errore durante l'inserimento");
            e.getMessage();
            return false;
        }
        finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (insertEmozione != null) {
                insertEmozione.close();
            }
        }

    }

    /**@author Stefano Farina
     * Metodo che verifica se le informazioni di una canzone (titolo e autore) esistono nel database.
     *
     * @param titolo Titolo della canzone da verificare.
     * @param autore Autore della canzone da verificare.
     *
     * @return True se le informazioni della canzone esistono nel database, false altrimenti.
     *
     * @throws RemoteException Lanciato in caso di errore durante l'invocazione remota.
     * @throws SQLException Lanciato in caso di errore nell'accesso al database.
     */
    public synchronized boolean checkInfoCanzone(String titolo, String autore) throws SQLException {
        Connection checkInfoCanzone = null;
        PreparedStatement preparedStatement = null;

        try {
            // Ottieni l'istanza di connessione al database
            checkInfoCanzone = ConnessioneDBImpl.getInstance().getConnection();
            preparedStatement=null;


            String querycheck = " SELECT COUNT(*) FROM canzone where titolo=? and autore=? ";
            preparedStatement = checkInfoCanzone.prepareStatement(querycheck);

            preparedStatement.setString(1, titolo);
            preparedStatement.setString(2,autore);

            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            int count = resultSet.getInt(1);
            boolean exists = count > 0;
            preparedStatement.close();
            checkInfoCanzone.close();
            return exists;
        } catch (Exception e) {
            e.printStackTrace();
            preparedStatement.close();
            checkInfoCanzone.close();

            return false;
        }
    }

    /**@author Stefano Farina
     * Metodo che ricerca le canzoni per titolo all'interno della tabella composta che sono legate all'utente specificato.
     *
     * @param idUtente Identificativo dell'utente proprietario della playlist.
     * @param titolo   Titolo della canzone da cercare.
     * @param autore   Autore della canzone da cercare.
     * @return Una lista di oggetti 'Canzone' che corrispondono al titolo e all'autore specificati nella playlist dell'utente.
     * @throws RemoteException Lanciato in caso di errore durante l'invocazione remota.
     * @throws SQLException Lanciato in caso di errore nell'accesso al database.
     */
    public synchronized List<Canzone> ricercaCanzoneTitoloInPlaylist(String idUtente, String titolo, String autore) throws RemoteException, SQLException {
        Connection searchByTitle = null;
        PreparedStatement preparedStatement = null;

        List<Canzone> infoCanzone = new ArrayList<>();
        try{
            searchByTitle= new ConnessioneDBImpl().getConnection();
            String query = "select * from composta where idutente=? AND  titolo =? AND autore=?";
            preparedStatement = searchByTitle.prepareStatement(query);
            preparedStatement.setString(1,idUtente);
            preparedStatement.setString(2, titolo);
            preparedStatement.setString(3,autore);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet != null ) {
                while (resultSet.next()) {
                    String titoloCanzone = resultSet.getString("titolo");
                    String autoreCanzone = resultSet.getString("autore");

                    Canzone canzone = new Canzone(titoloCanzone, autoreCanzone);
                    infoCanzone.add(canzone);

                    //stampo i valori presi
                    System.out.println("Titolo: " + titoloCanzone);
                    System.out.println("Autore: " + autoreCanzone);
                    System.out.println();
                }
            }
            else {
                infoCanzone = null; // La canzone non è stata trovata, impostiamo l'array a null
            }

            return infoCanzone;
        }catch(Exception e){
            System.out.println("Errore durante la ricerca della canzone per titolo: " + e.getMessage());
            throw new RemoteException("Canzone --> NON Trovata", e);
        }
        finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (searchByTitle != null) {
                searchByTitle.close();
            }
        }
    }


    /**@author Emilio Daverio
     * Questo metodo è l'entry point principale per avviare il server dell'applicazione.
     * Serve per inizializzare e avviare il server RMI per la gestione delle chiamate remote.
     * Inoltre, stabilisce una connessione al database.
     *
     * @param args Argomenti da riga di comando (non utilizzati in questo caso).
     * @throws RemoteException Se si verifica un errore di comunicazione remota durante l'avvio del server RMI.
     */
    public static void main(String[] args) throws RemoteException {

        /*faccio partire il server*/

        System.out.println("Server in preparazione: ");
        ServerImpl sevimpl = new ServerImpl();
        Registry registro;
        try {
            registro = LocateRegistry.createRegistry(1099);
            registro.rebind("ServerEmotionalSongs", sevimpl);
            System.out.println("Server avviato");
        } catch (Exception e) {
            System.out.println("ERRORE!!! Server non Avviato");
            System.out.println(e.getMessage().toString());
        }

        /* connessione DB parte quando patrte il server*/

        ConnessioneDBImpl connection = new ConnessioneDBImpl();
        Connection dbConnection= connection.getConnection();
        if (dbConnection != null) {
            System.out.println("Connessione al database effettuata con successo");

            // Fai altre operazioni necessarie con la connessione al database
        } else {
            System.out.println("Connessione al database NON effettuata");
            // Gestisci il fallimento della connessione al database
        }

    }
}