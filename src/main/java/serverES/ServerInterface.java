package serverES;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServerInterface extends Remote {

    /** operazioni utente non loggato**/
    public void registrazione() throws RemoteException;
    public void login(String user, String pwd) throws RemoteException;


    /** operazioni  utente loggato e non loggato**/
    public void ricercaCanzoneTitolo(String titolo) throws RemoteException;
    public void ricercaCanzoneAutoreAnno(String autore, String anno) throws RemoteException;
    public void visualizzaEmozioni()throws RemoteException;


    /** operazioni solo utente loggato**/
    public void logOut(String userName, String pwd)throws RemoteException;
    public void creaPlaylist()throws RemoteException;
    public void eliminaPlaylist()throws RemoteException;
    public void aggiuntaCanzoniPlaylist() throws RemoteException;
    public void eliminaCanzoniPlaylist()throws RemoteException;
    public void inserisciEmozione() throws RemoteException;

}
