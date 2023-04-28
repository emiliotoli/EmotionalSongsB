package serverES;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLOutput;

public class ServerImpl extends UnicastRemoteObject implements ServerInterface{
    private static final long serialVersionUid=1;


    protected ServerImpl() throws RemoteException {
        super();
    }



    /** operazioni utente non loggato**/
    public void registrazione() {

    }
    public void login(String user, String pwd) {

    }


    /** operazioni  utente loggato e non loggato**/
    public void ricercaCanzoneTitolo(String titolo) {
    }
    public void ricercaCanzoneAutoreAnno(String autore, String anno) {

    }
    public void visualizzaEmozioni() {

    }

    /** operazioni solo utente loggato**/
    public void logOut(String userName, String pwd) {

    }
    public void creaPlaylist(){

    }
    public void eliminaPlaylist(){

    }
    public void aggiuntaCanzoniPlaylist() {

    }
    public void eliminaCanzoniPlaylist() {

    }
    public void inserisciEmozione() {

    }



    public static void main(String[] args) throws RemoteException {

        System.out.println("server in preparazione: ");

        try{
            Registry registro=LocateRegistry.createRegistry(1099);
            ServerImpl sevimpl=new ServerImpl();
            registro.rebind("ServerEmotionalSongs", sevimpl);
            System.out.println("server partito");
        }catch(Exception e){
            System.out.println("ERRORE!!! server non partito");
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
