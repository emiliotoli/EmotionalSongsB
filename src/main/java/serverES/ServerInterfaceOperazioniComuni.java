package serverES;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * questa interfaccia serve per gestire le operazioni che l'utente può fare per quando è Loggato e non
 */
public interface ServerInterfaceOperazioniComuni extends Remote{

    /** operazioni che un utente può fare da loggato e non **/
    public void ricercaCanzoneTitolo(String titolo) throws RemoteException;
    public void ricercaCanzoneAutoreAnno(String autore, String anno) throws RemoteException;
    public void visualizzaEmozioni()throws RemoteException;
}
