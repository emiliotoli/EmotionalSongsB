package serverES;

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
    public void logOut(String userName, String pwd)throws RemoteException, SQLException;
    public void creaPlaylist()throws RemoteException, SQLException;
    public void eliminaPlaylist()throws RemoteException, SQLException;
    public void aggiuntaCanzoniPlaylist() throws RemoteException, SQLException;
    public void eliminaCanzoniPlaylist()throws RemoteException, SQLException;
    public void inserisciEmozione() throws RemoteException, SQLException;
}
