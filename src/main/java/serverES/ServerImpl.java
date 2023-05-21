package serverES;

import ClientES.Utente;
import DataBase.ConnessioneDB;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ServerImpl extends UnicastRemoteObject implements ServerInterfaceNonLoggato, ServerInterfaceLoggato, Remote {

    private static final long serialVersionUid = 1L;
    protected ServerImpl() throws RemoteException {
        super();
    }


    /**
     * operazioni utente non loggato
     **/

    public void registrazione() {}

    @Override
    public void registrazione(Utente utente) throws RemoteException, SQLException {
        try{
            Connection conn=new ConnessioneDB().DBConnecctoin();
        }

    }

    public void login(String user, String pwd) {

    }
    //public boolean checkUserID(String userID) throws RemoteException{}

    /**
     * operazioni  utente loggato e non loggato
     **/
    public void ricercaCanzoneTitolo(String titolo) { }
    public void ricercaCanzoneAutoreAnno(String autore, String anno) {

    }
    public void visualizzaEmozioni() {

    }

    /**
     * operazioni solo utente loggato
     **/
    public void logOut(String userName, String pwd) {}
    public void creaPlaylist() {}
    public void eliminaPlaylist() {

    }
    public void aggiuntaCanzoniPlaylist() {

    }
    public void eliminaCanzoniPlaylist() {

    }
    public void inserisciEmozione() {

    }


    public static void main(String[] args) throws RemoteException {

        /**faccio partire il server**/

        System.out.println("server in preparazione: ");
        ServerImpl sevimpl = new ServerImpl();
        Registry registro;
        try {
            registro = LocateRegistry.createRegistry(1099);
            registro.rebind("ServerEmotionalSongs", sevimpl);
            System.out.println("server partito");
        } catch (Exception e) {
            System.out.println("ERRORE!!! server non partito");
            System.out.println(e.getMessage().toString());
        }
        ConnessioneDB connection = new ConnessioneDB();
        /** connessione DB parte quando patrte il server**/
        connection.DBConnecctoin();


    }
}
