/**
 * Il pacchetto 'serverES' contiene interfacce e classi necessarie per gestire le operazioni utente
 * quando l'utente ha effettuato il login nell'applicazione.
 */

package serverES;

import ClientES.Canzone;
import ClientES.PlayList;
import DataBase.ConnessioneDBImpl;

import java.rmi.RemoteException;
import java.rmi.Remote;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * L'interfaccia 'ServerInterfaceLoggato' definisce i metodi per gestire le
 * operazioni consentite
 * solo agli utenti che hanno effettuato il login. Queste operazioni includono
 * la gestione delle playlist,
 * l'aggiunta o l'eliminazione di canzoni dalle playlist, la visualizzazione
 * delle canzoni e delle playlist,
 * l'inserimento di emozioni per le canzoni e altre operazioni.
 *
 * @author Stefano Farina
 */
public interface ServerInterfaceLoggato extends Remote {
    /** operazioni solo utente loggato **/

    /**
     * @author Stefano Farina
     *         Metodo che crea una nuova playlist per un utente specifico.
     *
     * @param userID       Identificativo dell'utente proprietario della playlist.
     * @param nomePlaylist Nome della nuova playlist da creare.
     *
     * @return True se la creazione della playlist ha avuto successo, false
     *         altrimenti.
     *
     * @throws RemoteException Lanciato in caso di errore durante l'invocazione
     *                         remota.
     * @throws SQLException    Lanciato in caso di errore nell'accesso al database.
     */
    public boolean creaPlaylist(String userID, String nomePlaylist) throws RemoteException, SQLException;

    /**
     * @author Stefano Farina
     *         Metodo che verifica se un nome della playlist è già in uso per
     *         quell'utente.
     *
     * @param nomePlaylist Nome della playlist da verificare.
     *
     * @return True se il nome della playlist è disponibile per l'utente, false se è
     *         già in uso.
     *
     * @throws RemoteException Lanciato in caso di errore durante l'invocazione
     *                         remota.
     * @throws SQLException    Lanciato in caso di errore nell'accesso al database.
     */
    public boolean checkNomePlaylist(String nomePlaylist) throws RemoteException, SQLException;

    /**
     * @author Stefano Farina
     *         Metodo che elimina una playlist esistente per un utente specifico.
     *
     * @param userID       Identificativo dell'utente proprietario della playlist.
     * @param nomePalylist Nome della playlist da eliminare.
     *
     * @return True se l'eliminazione della playlist ha avuto successo, false
     *         altrimenti.
     *
     * @throws RemoteException Lanciato in caso di errore durante l'invocazione
     *                         remota.
     * @throws SQLException    Lanciato in caso di errore nell'accesso al database.
     */
    public boolean eliminaPlaylist(String userID, String nomePalylist) throws RemoteException, SQLException;

    /**
     * @author Stefano Farina
     *         Metodo che aggiunge una canzone a una playlist specifica di un
     *         utente.
     *
     * @param nomePlaylist  Nome della playlist in cui aggiungere la canzone.
     * @param idUtente      Identificativo dell'utente proprietario della playlist.
     * @param titoloCanzone Titolo della canzone da aggiungere.
     * @param autoreCanzone Autore della canzone da aggiungere.
     *
     * @return True se l'aggiunta della canzone alla playlist ha avuto successo,
     *         false altrimenti.
     *
     * @throws RemoteException Lanciato in caso di errore durante l'invocazione
     *                         remota.
     * @throws SQLException    Lanciato in caso di errore nell'accesso al database.
     */
    public boolean aggiuntaCanzoniPlaylist(String nomePlaylist, String idUtente, String titoloCanzone,
            String autoreCanzone) throws RemoteException, SQLException;

    /**
     * @author Stefano Farina
     *         Metoodo che rimuove una canzone da una playlist specifica di un
     *         utente.
     *
     * @param nomePlaylist  Nome della playlist da cui rimuovere la canzone.
     * @param idUtente      Identificativo dell'utente proprietario della playlist.
     * @param titoloCanzone Titolo della canzone da rimuovere.
     * @param autoreCanzone Autore della canzone da rimuovere.
     *
     * @return True se la rimozione della canzone dalla playlist ha avuto successo,
     *         false altrimenti.
     *
     * @throws RemoteException Lanciato in caso di errore durante l'invocazione
     *                         remota.
     * @throws SQLException    Lanciato in caso di errore nell'accesso al database.
     */
    public boolean eliminaCanzoniPlaylist(String nomePlaylist, String idUtente, String titoloCanzone,
            String autoreCanzone) throws RemoteException, SQLException;

    /**
     * @author Stefano Farina
     *         Metodo che visualizza le canzoni presenti in una specifica playlist
     *         di un utente.
     *
     * @param nomePlaylist Nome della playlist da cui visualizzare le canzoni.
     * @param userID       Identificativo dell'utente proprietario della playlist.
     *
     * @return Una lista di oggetti 'Canzone' presenti nella playlist specificata.
     *
     * @throws RemoteException Lanciato in caso di errore durante l'invocazione
     *                         remota.
     * @throws SQLException    Lanciato in caso di errore nell'accesso al database.
     */
    public List<Canzone> VisualizzaCanzoniPlaylist(String nomePlaylist, String userID)
            throws RemoteException, SQLException;

    /**
     * @author Stefano Farina
     *         Metodo che visualizza tutte le playlist di un utente.
     *
     * @param userID Identificativo dell'utente di cui visualizzare le playlist.
     *
     * @return Una lista di oggetti 'PlayList' dell'utente specificato.
     *
     * @throws RemoteException Lanciato in caso di errore durante l'invocazione
     *                         remota.
     * @throws SQLException    Lanciato in caso di errore nell'accesso al database.
     */
    public List<PlayList> VisualizzaPlaylist(String userID) throws RemoteException, SQLException;

    /**
     * @author Stefano Farina
     *         Metodo che inserisce un'emozione associata a una specifica canzone.
     *
     * @param userID              Identificativo dell'utente che inserisce
     *                            l'emozione.
     * @param emozioneScelta      Tipo di emozione scelta dall'utente.
     * @param titoloCanzone       Titolo della canzone a cui associare l'emozione.
     * @param autoreCanzone       Autore della canzone a cui associare l'emozione.
     * @param notaEmozione        Nota o commento aggiuntivo sull'emozione.
     * @param spiegazioneEmozione Spiegazione dettagliata dell'emozione.
     * @param punteggioEmozione   Punteggio dell'emozione (ad esempio, su una scala
     *                            da 1 a 5).
     *
     * @return True se l'inserimento dell'emozione ha avuto successo, false
     *         altrimenti.
     *
     * @throws RemoteException Lanciato in caso di errore durante l'invocazione
     *                         remota.
     * @throws SQLException    Lanciato in caso di errore nell'accesso al database.
     */
    public boolean inserisciEmozione(String userID, String emozioneScelta, String titoloCanzone, String autoreCanzone,
            String notaEmozione, String spiegazioneEmozione, int punteggioEmozione)
            throws RemoteException, SQLException;

    /**
     * @author Stefano Farina
     *         Metodo che verifica se le informazioni di una canzone (titolo e
     *         autore) esistono nel database.
     *
     * @param titolo Titolo della canzone da verificare.
     * @param autore Autore della canzone da verificare.
     *
     * @return True se le informazioni della canzone esistono nel database, false
     *         altrimenti.
     *
     * @throws RemoteException Lanciato in caso di errore durante l'invocazione
     *                         remota.
     * @throws SQLException    Lanciato in caso di errore nell'accesso al database.
     */
    public boolean checkInfoCanzone(String titolo, String autore) throws RemoteException, SQLException;

    /**
     * @author Stefano Farina
     *         Metodo che ricerca le canzoni per titolo all'interno della tabella
     *         composta che sono legate all'utente specificato.
     *
     * @param idUtente Identificativo dell'utente proprietario della playlist.
     * @param titolo   Titolo della canzone da cercare.
     * @param autore   Autore della canzone da cercare.
     * @return Una lista di oggetti 'Canzone' che corrispondono al titolo e
     *         all'autore specificati nella playlist dell'utente.
     * @throws RemoteException Lanciato in caso di errore durante l'invocazione
     *                         remota.
     * @throws SQLException    Lanciato in caso di errore nell'accesso al database.
     */
    public List<Canzone> ricercaCanzoneTitoloInPlaylist(String idUtente, String titolo, String autore)
            throws RemoteException, SQLException;

}
