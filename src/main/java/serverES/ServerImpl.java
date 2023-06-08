package serverES;

import ClientES.Utente;
import DataBase.ConnessioneDB;
import DataBase.ConnessioneDBImpl;


import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.PreparedStatement;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ServerImpl extends UnicastRemoteObject implements ServerInterfaceNonLoggato, ServerInterfaceLoggato, Remote {

    private static final long serialVersionUid = 1L;
    protected ServerImpl() throws RemoteException {
        super();
    }

    /**
     * operazioni utente non loggato
     **/
    @Override
    public synchronized void registrazione(Utente utente) throws RemoteException, SQLException {
        try{
            Connection conn=new ConnessioneDB().getConnectionIstance();
        }catch (Exception e){
            e.getMessage();
        }
    }

    public synchronized boolean login(String userId, String password) throws RemoteException{
        try{
            //apro la connessione con il DB
            //Connection con=ConnessioneDB.istance.getConnectionIstance();
            Connection connCheckLogin = ConnessioneDBImpl.getInstance().getConnection();
            PreparedStatement preparedStatement=null;

            String queryLogin="SELECT COUNT(*) FROM utentiregistrati WHERE  userid= ? AND password = ?";

            preparedStatement=connCheckLogin.prepareStatement(queryLogin);
            preparedStatement.setString(1,userId);
            preparedStatement.setString(2,password);

            //eseguo la query
            ResultSet resultSet=preparedStatement.executeQuery();

            //risposta query
            resultSet.next();
            int count = resultSet.getInt(1);
            boolean esistonoDati = (count > 0);
            return esistonoDati;

        }catch ( Exception e){
            e.getMessage().toString();
            return false;
        }
    }
    public boolean checkUserID(String userID) throws RemoteException {
        try {

            // Ottieni l'istanza di connessione al database
            Connection connCheckID = ConnessioneDBImpl.getInstance().getConnection();
            PreparedStatement preparedStatement=null;
            String querycheck = "SELECT COUNT(*) FROM utentiregistrati WHERE  userid= ? ";
             preparedStatement = connCheckID.prepareStatement(querycheck);
            preparedStatement.setString(1, userID);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            int count = resultSet.getInt(1);
            boolean exists = count > 0;
            return exists;
        } catch (Exception e) {
            e.getMessage().toString();
            return false;
        }
    }

    /**
     * operazioni  utente loggato e non loggato
     **/
    public synchronized void ricercaCanzoneTitolo(String titolo) { }
    public  synchronized void ricercaCanzoneAutoreAnno(String autore, String anno) {

    }
    public  synchronized void visualizzaEmozioni() {

    }

    /**
     * operazioni solo utente loggato
     **/
    public synchronized void logOut(String userName, String pwd) {}
    public synchronized void creaPlaylist() {}
    public synchronized void eliminaPlaylist() {

    }
    public synchronized void aggiuntaCanzoniPlaylist() {

    }
    public synchronized void eliminaCanzoniPlaylist() {

    }
    public synchronized void inserisciEmozione() {

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
        /** connessione DB parte quando patrte il server**/
        //ConnessioneDB connection = new ConnessioneDB();
        //connection.getConnectionIstance();

        /** connessione DB parte quando patrte il server**/

        ConnessioneDBImpl connection = new ConnessioneDBImpl();
        Connection dbConnection= connection.getConnection();
        if (dbConnection != null) {
            System.out.println("Connessione al database riuscita");
            // Fai altre operazioni necessarie con la connessione al database
        } else {
            System.out.println("Connessione al database fallita");
            // Gestisci il fallimento della connessione al database
        }

    }
}
