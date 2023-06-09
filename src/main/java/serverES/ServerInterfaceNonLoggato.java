package serverES;
import ClientES.Utente;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.SQLException;


/**
 * questa interfaccia serve per gestire le operazioni che l'utente può fare per loggarsi sull'applicazione
 */
public interface ServerInterfaceNonLoggato extends Remote {

    /** operazioni utente non loggato**/
    public boolean registrazione(Utente utente) throws RemoteException, SQLException;
    public boolean registrazione(String nome, String cognome, String codiceFiscale, String via, String numeroCivico,String cap,String comune, String provincia, String email, String userID, String password) throws RemoteException, SQLException;
    public boolean login(String userId, String password) throws RemoteException;
    public boolean checkUserID(String userID) throws RemoteException, SQLException ;

    /** operazioni che un utente può fare da loggato e non **/
    public void ricercaCanzoneTitolo(String titolo) throws RemoteException, SQLException;
    public void ricercaCanzoneAutoreAnno(String autore, String anno) throws RemoteException, SQLException;
    public void visualizzaEmozioni()throws RemoteException,SQLException;


}
