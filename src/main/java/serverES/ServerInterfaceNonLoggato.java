package serverES;

import java.rmi.Remote;
import java.rmi.RemoteException;


/**
 * questa interfaccia serve per gestire le operazioni che l'utente può fare per loggarsi sull'applicazione
 */
public interface ServerInterfaceNonLoggato extends Remote {

    /** operazioni utente non loggato**/
    public void registrazione() throws RemoteException;
    public void login(String user, String pwd) throws RemoteException;

}
