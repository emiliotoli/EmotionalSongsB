package serverES;

import ClientES.Utente;
import DataBase.ConnessioneDBImpl;


import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.sql.*;

public class ServerImpl extends UnicastRemoteObject implements ServerInterfaceNonLoggato, ServerInterfaceLoggato, Remote {

    private static final long serialVersionUid = 1L;
    protected ServerImpl() throws RemoteException {
        super();
    }

    /**
     * operazioni utente non loggato
     **/
    @Override
    public synchronized boolean registrazione(Utente utente) throws RemoteException, SQLException {

        Connection connInsertUtente = null;
        PreparedStatement preparedStatement = null;
        int capValue;
        try{

           // Connection con=ConnessioneDB.istance.getConnectionIstance();
            connInsertUtente=new ConnessioneDBImpl().getConnection();

            String queryInsert = "INSERT INTO utentiregistrati (nome, cognome, codicefiscale, via, numerocivico, comune, provincia, cap, userid, email, password) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
            preparedStatement=connInsertUtente.prepareStatement(queryInsert);

            preparedStatement.setString(1, utente.getNome());
            preparedStatement.setString(2, utente.getCognome());
            preparedStatement.setString(3, utente.getCodiceFiscale());
            preparedStatement.setString(4, utente.getVia());
            preparedStatement.setString(5, utente.getNumeroCivico());
            preparedStatement.setString(6, utente.getComune());
            preparedStatement.setString(7, utente.getProvincia());

            try {
                capValue = Integer.parseInt(utente.getCap());
                preparedStatement.setInt(8, capValue);
            } catch (NumberFormatException e) {
                // Gestisci l'eccezione, ad esempio fornendo un valore di default o segnalando un errore
                System.out.println("Il valore di 'cap' non è un numero intero valido: " + utente.getCap());
                return false; // o l'azione appropriata per la gestione degli errori
            }
            //preparedStatement.setString(8, utente.getCap());
            preparedStatement.setString(9, utente.getUserID());
            preparedStatement.setString(10, utente.getEmail());
            preparedStatement.setString(11, utente.getPassword());

            System.out.println("query contenete: " +  queryInsert);
            preparedStatement.executeUpdate();

            preparedStatement.close();
            connInsertUtente.close();

            return true;
        }catch (Exception e) {
            System.out.println("errore durante l'inserimento");
            e.getMessage();
            return false;
        }
        finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connInsertUtente != null) {
                connInsertUtente.close();
            }
        }
    }

    public synchronized boolean registrazione(String nome, String cognome, String codiceFiscale, String via, String numeroCivico, String cap , String comune, String provincia, String email, String userID, String password) throws RemoteException, SQLException {
        Connection connInsertUtente = null;
        PreparedStatement preparedStatement = null;
        int capValue;

        try {
            connInsertUtente = new ConnessioneDBImpl().getConnection();

            String queryInsert = "INSERT INTO utentiregistrati (nome, cognome, codicefiscale, via, numerocivico, comune, provincia, cap, userid, email, password) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
            preparedStatement = connInsertUtente.prepareStatement(queryInsert);

            preparedStatement.setString(1, nome);
            preparedStatement.setString(2, cognome);
            preparedStatement.setString(3, codiceFiscale);
            preparedStatement.setString(4, via);
            preparedStatement.setString(5, numeroCivico);
            preparedStatement.setString(6, comune);
            preparedStatement.setString(7, provincia);
            try {
                capValue = Integer.parseInt(cap);
                preparedStatement.setInt(8, capValue);
            } catch (NumberFormatException e) {
                // Gestisci l'eccezione, ad esempio fornendo un valore di default o segnalando un errore
                System.out.println("Il valore di 'cap' non è un numero intero valido: " + cap);
                return false; // o l'azione appropriata per la gestione degli errori
            }
            //preparedStatement.setString(8, cap );
            preparedStatement.setString(9, userID);
            preparedStatement.setString(10, email);
            preparedStatement.setString(11, password);

            System.out.println("Query contenente: " + queryInsert);
            int rowsInserted = preparedStatement.executeUpdate();

            return rowsInserted > 0;
        } catch (Exception e) {
            System.out.println("Errore durante l'inserimento");
            e.printStackTrace();
            return false;
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connInsertUtente != null) {
                connInsertUtente.close();
            }
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
