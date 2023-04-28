package serverES;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class ServerImpl extends UnicastRemoteObject implements ServerInterface{
    private static final long serialVersionUid=1;

    protected ServerImpl() throws RemoteException {
        super();
    }

    public void registrazione() {

    }
    public void login(String user, String pwd) {

    }
    public void ricercaCanzoneTitolo() {
    }
    public void ricercaCanzoneAutoreAnno() {

    }

    public static void main(String[] args) throws RemoteException {

        System.out.println("server in preparazione: ");
        Registry registro=LocateRegistry.createRegistry(1099);
        ServerImpl sevimpl=new ServerImpl();
        registro.rebind("ServerEmotionalSongs", sevimpl);
        System.out.println("server partito");
    }

}
