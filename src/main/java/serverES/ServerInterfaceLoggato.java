package serverES;

import java.rmi.RemoteException;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * questa interfaccia serve per gestire le operazioni che l'utente può fare quando ha già effettuato il Login
 */
public interface ServerInterfaceLoggato extends Remote {
    /** operazioni solo utente loggato**/
    public void logOut(String userName, String pwd)throws RemoteException;
    public void creaPlaylist()throws RemoteException;
    public void eliminaPlaylist()throws RemoteException;
    public void aggiuntaCanzoniPlaylist() throws RemoteException;
    public void eliminaCanzoniPlaylist()throws RemoteException;
    public void inserisciEmozione() throws RemoteException;
}
