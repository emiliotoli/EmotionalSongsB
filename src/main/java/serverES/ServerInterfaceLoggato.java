package serverES;

import ClientES.Canzone;
import ClientES.PlayList;

import java.rmi.RemoteException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.*;

/**
 * questa interfaccia serve per gestire le operazioni che l'utente può fare quando ha già effettuato il Login
 *
 */
public interface ServerInterfaceLoggato extends Remote {
    /** operazioni solo utente loggato**/
    public boolean creaPlaylist(String userID, String nomePlaylist)throws RemoteException, SQLException;
    public boolean checkNomePlaylist(String nomePlaylist)throws RemoteException, SQLException;
    public boolean eliminaPlaylist(String userID, String nomePalylist)throws RemoteException, SQLException;
    public boolean aggiuntaCanzoniPlaylist(String nomePlaylist, String idUtente, String titoloCanzone, String autoreCanzone) throws RemoteException, SQLException;
    public boolean eliminaCanzoniPlaylist(String nomePlaylist, String idUtente, String titoloCanzone, String autoreCanzone)throws RemoteException, SQLException;
    public  List<Canzone> VisualizzaCanzoniPlaylist(String nomePlaylist, String userID)throws RemoteException, SQLException;
    public  List<PlayList> VisualizzaPlaylist(String userID)throws RemoteException, SQLException;
    public boolean inserisciEmozione(String userID, String emozioneScelta, String titoloCanzone, String autoreCanzone, String notaEmozione, String spiegazioneEmozione, int punteggioEmozione) throws RemoteException, SQLException;

    public List<Canzone> ricercaCanzoneTitoloInPlaylist(String idUtente, String titolo, String autore) throws RemoteException, SQLException;
}
