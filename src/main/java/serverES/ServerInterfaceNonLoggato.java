/**
 * Il pacchetto 'serverES' contiene interfacce e classi necessarie per la gestione delle operazioni
 * che un utente può svolgere.
 */

package serverES;

import ClientES.Canzone;
import ClientES.Emozione;
import ClientES.Utente;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.List;

/**
 * L'interfaccia 'ServerInterfaceNonLoggato' definisce i metodi per gestire le operazioni che un utente può eseguire
 * senza essere autenticato. Questi metodi includono registrazione, accesso, ricerca di canzoni e visualizzazione
 * delle emozioni associate a una canzone.
 *
 * @author Emilio Daverio
 */
public interface ServerInterfaceNonLoggato extends Remote {

    /** operazioni utente non loggato **/

    /**@author Emilio Daverio
     * Metodo che serve per registrare un nuovo utente nell'applicazione. Prende come argomento un oggetto di tipo Utente
     *
     * @param utente Oggetto 'Utente' contenente le informazioni dell'utente da registrare.
     *
     * @return True se la registrazione è avvenuta con successo, false altrimenti.
     *
     * @throws RemoteException Lanciato in caso di errore durante l'invocazione remota.
     * @throws SQLException Lanciato in caso di errore nell'accesso al database.
     */
    public boolean registrazione(Utente utente) throws RemoteException, SQLException;

    /**@author Emilio Daverio
     * Metodo che effettua l'accesso di un utente con il suo identificativo e la password.
     *
     * @param userId   Identificativo dell'utente.
     * @param password Password dell'utente.
     *
     * @return True se l'accesso è avvenuto con successo, false altrimenti.
     * @throws RemoteException Lanciato in caso di errore durante l'invocazio
     * ne remota.
     */
    public boolean login(String userId, String password) throws RemoteException;

    /**@author Emilio Daverio
     * Metodo che verifica se un identificativo utente è già in uso.
     *
     * @param userID Identificativo dell'utente da verificare.
     *
     * @return True se l'identificativo è disponibile, false se è già in uso.
     *
     * @throws RemoteException Lanciato in caso di errore durante l'invocazione remota.
     * @throws SQLException Lanciato in caso di errore nell'accesso al database.
     */
    public boolean checkUserID(String userID) throws RemoteException, SQLException;


    /** operazioni che un utente può fare da loggato e non **/


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
    public List<Canzone> ricercaCanzoneTitolo(String titolo) throws RemoteException, SQLException;

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
    public List<Canzone> ricercaCanzoneAutoreAnno(String autore, int anno) throws RemoteException, SQLException;

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
    public List<Emozione> visualizzaEmozioni(String titoloCanzone, String autoreCanzone)
            throws RemoteException, SQLException;
}
