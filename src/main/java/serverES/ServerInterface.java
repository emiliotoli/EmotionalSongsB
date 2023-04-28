package serverES;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServerInterface extends Remote {

    public void registrazione() throws RemoteException;
    public void login(String user, String pwd) throws RemoteException;
    public void ricercaCanzoneTitolo() throws RemoteException;
    public void ricercaCanzoneAutoreAnno() throws RemoteException;

}
