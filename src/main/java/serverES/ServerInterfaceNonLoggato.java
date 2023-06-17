package serverES;
import ClientES.Canzone;
import ClientES.Emozione;
import ClientES.Utente;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.List;


/**
 * questa interfaccia serve per gestire le operazioni che l'utente può fare per loggarsi sull'applicazione
 */
public interface  ServerInterfaceNonLoggato extends Remote {

    /** operazioni utente non loggato**/
    public boolean registrazione(Utente utente) throws RemoteException, SQLException;
    //public boolean registrazione(String nome, String cognome, String codiceFiscale, String via, String numeroCivico,String cap,String comune, String provincia, String email, String userID, String password) throws RemoteException, SQLException;
    public boolean login(String userId, String password) throws RemoteException;
    public boolean checkUserID(String userID) throws RemoteException, SQLException ;

    /** operazioni che un utente può fare da loggato e non **/
    public List<Canzone> ricercaCanzoneTitolo(String titolo) throws RemoteException, SQLException;
    public List<Canzone> ricercaCanzoneAutoreAnno(String autore, int anno) throws RemoteException, SQLException;
    public List<Emozione> visualizzaEmozioni(String titoloCanzone, String autoreCanzone)throws RemoteException,SQLException;


}
