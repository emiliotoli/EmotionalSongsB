package serverES;

import ClientES.Emozione;
import ClientES.PlayList;
import ClientES.Utente;
import ClientES.Canzone;
import DataBase.ConnessioneDBImpl;


import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServerImpl extends UnicastRemoteObject implements ServerInterfaceNonLoggato, ServerInterfaceLoggato, Remote {

    private static final long serialVersionUid = 1L;
    private boolean dbExists=false;
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
    /*public synchronized boolean registrazione(String nome, String cognome, String codiceFiscale, String via, String numeroCivico, String cap , String comune, String provincia, String email, String userID, String password) throws RemoteException, SQLException {
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
    }*/
    public synchronized boolean login(String userId, String password) throws RemoteException{
        Connection connCheckLogin = null;
        PreparedStatement preparedStatement = null;
        try{
            //apro la connessione con il DB

            connCheckLogin = new ConnessioneDBImpl().getConnection();

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
    public synchronized List<Canzone> ricercaCanzoneTitolo(String titolo) throws RemoteException, SQLException {
        Connection searchByTitle = null;
        PreparedStatement preparedStatement = null;

        List<Canzone> infoCanzone = new ArrayList<>();
        try{
            searchByTitle= new ConnessioneDBImpl().getConnection();
            String query = "SELECT * FROM canzone WHERE titolo LIKE ?";
            preparedStatement = searchByTitle.prepareStatement(query);
            //preparedStatement.setString(1, titolo);
            preparedStatement.setString(1, "%" + titolo + "%");

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet != null ) {
                while (resultSet.next()) {
                    String titoloCanzone = resultSet.getString("titolo");
                    String autoreCanzone = resultSet.getString("autore");
                    int annoCanzone = resultSet.getInt("anno");

                    Canzone canzone = new Canzone(titoloCanzone, autoreCanzone, annoCanzone);
                    infoCanzone.add(canzone);

                    //stampo i valori presi
                    System.out.println("Titolo: " + titoloCanzone);
                    System.out.println("Autore: " + autoreCanzone);
                    System.out.println("Anno: " + annoCanzone);
                    System.out.println();
                }
            }
            else {
                infoCanzone = null; // La canzone non è stata trovata, impostiamo l'array a null
            }


            return infoCanzone;
        }catch(Exception e){
            System.out.println("Errore durante la ricerca della canzone per titolo: " + e.getMessage());
            throw new RemoteException("Canzone --> NON Trovata", e);
        }
        finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (searchByTitle != null) {
                searchByTitle.close();
            }
        }
    }
    public  synchronized List<Canzone> ricercaCanzoneAutoreAnno(String autore, int anno) throws RemoteException, SQLException{
        Connection searchByTitle = null;
        PreparedStatement preparedStatement = null;

        List<Canzone> infoCanzone = new ArrayList<>();
        try{
            searchByTitle= new ConnessioneDBImpl().getConnection();
            String query = "select * from canzone where autore= ? and anno= ? ";

            preparedStatement = searchByTitle.prepareStatement(query);
            preparedStatement.setString(1, autore);
            preparedStatement.setInt(2, anno);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet != null ) {
                while (resultSet.next()) {
                    String titoloCanzone = resultSet.getString("titolo");
                    String autoreCanzone = resultSet.getString("autore");
                    int annoCanzone = resultSet.getInt("anno");

                    Canzone canzone = new Canzone(titoloCanzone, autoreCanzone, annoCanzone);
                    infoCanzone.add(canzone);

                    //stampo i valori presi
                    System.out.println("Titolo: " + titoloCanzone);
                    System.out.println("Autore: " + autoreCanzone);
                    System.out.println("Anno: " + annoCanzone);
                    System.out.println();
                }
            }
            else {
                infoCanzone = null; // La canzone non è stata trovata, impostiamo l'array a null
            }

            return infoCanzone;
        }catch(Exception e){
            System.out.println("Errore durante la ricerca della canzone per titolo: " + e.getMessage());
            throw new RemoteException("Canzone --> NON Trovata", e);
        }
        finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (searchByTitle != null) {
                searchByTitle.close();
            }
        }
    }
    public  synchronized List<Emozione> visualizzaEmozioni(String titoloCanzone, String autoreCanzone) throws SQLException, RemoteException {
        Connection searchEmozione = null;
        PreparedStatement preparedStatement = null;
        List<Emozione> infoEmozione = new ArrayList<>();
        try {
            searchEmozione= new ConnessioneDBImpl().getConnection();
            String query = "SELECT Associa.titolo, Associa.autore, Associa.nome, COUNT(Associa.nome) AS num_emozioni, totali.numero_emozioni_totali, ROUND(((COUNT(Associa.nome)*1.0 /totali.numero_emozioni_totali * 1.0)*100),2) as percentuale\n" +
                    "FROM Associa \n" +
                    "JOIN (\n" +
                    "  SELECT titolo, autore, COUNT(nome) AS numero_emozioni_totali\n" +
                    "  FROM Associa \n" +
                    "  WHERE titolo = ? AND autore = ? \n" +
                    "  GROUP BY titolo, autore\n" +
                    ") AS totali ON Associa.titolo = totali.titolo AND Associa.autore = totali.autore\n" +
                    "WHERE Associa.titolo = ? AND Associa.autore = ? \n" +
                    "GROUP BY Associa.titolo, Associa.autore, Associa.nome, totali.numero_emozioni_totali;";

            preparedStatement = searchEmozione.prepareStatement(query);
            preparedStatement.setString(1, titoloCanzone);
            preparedStatement.setString(2, autoreCanzone);
            preparedStatement.setString(3,titoloCanzone);
            preparedStatement.setString(4,autoreCanzone);

            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet!=null){
                while(resultSet.next()){
                     String nomeEmozione=resultSet.getString("nome");
                     //String tipoEmozione=resultSet.getString("tipo");
                     //String spiegazioneEmozione=resultSet.getString("spiegazione");
                     //int punteggioEmozione=resultSet.getInt("punteggio");
                     double percentuale= resultSet.getDouble("percentuale");

                     Emozione emozione = new Emozione (nomeEmozione, percentuale);
                    infoEmozione.add(emozione);
                }
            }
            else {
                infoEmozione = null; // L'emozione non è stata trovata, impostiamo l'array a null
            }
            return infoEmozione;
        }catch(Exception e){
            System.out.println("Errore durante la ricerca della canzone per titolo: " + e.getMessage());
            throw new RemoteException("Canzone --> NON Trovata", e);
        }
        finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (searchEmozione != null) {
                searchEmozione.close();
            }
        }
    }

    /**
     * operazioni solo utente loggato
     **/
    public synchronized boolean creaPlaylist( String nomePlaylist, String userID) throws SQLException {
        Connection insertPlaylist = null;
        PreparedStatement preparedStatement = null;
        try{
            //apro la connessione con il DB

            insertPlaylist = new ConnessioneDBImpl().getConnection();

            String queryLogin= "INSERT INTO playlist (nome, idutente ) VALUES (?, ?)";
            preparedStatement=insertPlaylist.prepareStatement(queryLogin);

            preparedStatement.setString(1,nomePlaylist);
            preparedStatement.setString(2,userID );

            //eseguo la query
            preparedStatement.executeUpdate();

            preparedStatement.close();
            insertPlaylist.close();

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
            if (insertPlaylist != null) {
                insertPlaylist.close();
            }
        }
    }
    public synchronized boolean checkNomePlaylist(String nomePlaylist) throws SQLException{
        try {
            // Ottieni l'istanza di connessione al database
            Connection connCheckID = ConnessioneDBImpl.getInstance().getConnection();
            PreparedStatement preparedStatement=null;

            String querycheck = " SELECT COUNT(*) FROM playlist where nome=? ";
            preparedStatement = connCheckID.prepareStatement(querycheck);

            preparedStatement.setString(1, nomePlaylist);

            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            int count = resultSet.getInt(1);
            boolean exists = count > 0;

            return exists;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public synchronized List<PlayList> VisualizzaPlaylist(String userID) throws RemoteException, SQLException {
        Connection searchPlaylist = null;
        PreparedStatement preparedStatement = null;
        List<PlayList> infoPlalist = new ArrayList<>();
        try {
            searchPlaylist= new ConnessioneDBImpl().getConnection();
            String query = "SELECT * FROM playlist WHERE idutente=?";

            preparedStatement = searchPlaylist.prepareStatement(query);
            preparedStatement.setString(1, userID);

            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet!=null){
                while(resultSet.next()){
                    String nomePlalist=resultSet.getString("nome");
                    String nomeUtente=resultSet.getString("idutente");

                    PlayList playList = new PlayList (nomeUtente,nomePlalist);
                    infoPlalist.add(playList);
                }
            }
            else {
                infoPlalist = null; // L'emozione non è stata trovata, impostiamo l'array a null
            }
            return infoPlalist;
        }catch(Exception e){
            System.out.println("Errore durante la ricerca della PlayList: " + e.getMessage());
            throw new RemoteException(" PlayList--> NON Trovata", e);
        }
        finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (searchPlaylist != null) {
                searchPlaylist.close();
            }
        }
    }
    public synchronized void VisualizzaCanzoniPlaylist(){} //tabella playlist
    public synchronized boolean eliminaPlaylist(String nomePalylist, String userID )  throws RemoteException, SQLException {
        Connection deletePlaylist = null;
        PreparedStatement preparedStatement = null;
        try{
            //apro la connessione con il DB

            deletePlaylist = new ConnessioneDBImpl().getConnection();

            String queryDelete= "DELETE FROM  playlist  WHERE nome=? and idutente=?";
            preparedStatement=deletePlaylist.prepareStatement(queryDelete);

            preparedStatement.setString(1,nomePalylist);
            preparedStatement.setString(2,userID );

            //eseguo la query
            preparedStatement.executeUpdate();

            preparedStatement.close();
            deletePlaylist.close();

            return true;
        }catch (Exception e) {
            System.out.println("errore durante l'eliminazione della Playlist");
            e.getMessage();
            return false;
        }
        finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (deletePlaylist != null) {
                deletePlaylist.close();
            }
        }
    }
    public synchronized boolean aggiuntaCanzoniPlaylist(String nomePlaylist, String idUtente, String titoloCanzone, String autoreCanzone) throws RemoteException, SQLException {
        Connection aggiungiCanzone = null;
        PreparedStatement preparedStatement = null;
        try {
            aggiungiCanzone = new ConnessioneDBImpl().getConnection();
            String query = "INSERT INTO Composta (nome, idUtente, titolo, autore) VALUES (?, ?, ?, ?)";

            preparedStatement = aggiungiCanzone.prepareStatement(query);
            preparedStatement.setString(1, nomePlaylist);
            preparedStatement.setString(2, idUtente);
            preparedStatement.setString(3, titoloCanzone);
            preparedStatement.setString(4, autoreCanzone);

           preparedStatement.executeUpdate(); // Esegui l'inserimento e ottieni il numero di righe interessate
            System.out.println("inserimento canzone nella playlist" + nomePlaylist + " andato a buon fine");
            return true;
        } catch (Exception e) {
            System.out.println("Errore durante l'inserimento della canzone nella playlist: " + e.getMessage());
            return false;
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (aggiungiCanzone != null) {
                aggiungiCanzone.close();
            }
        }
    }
    public synchronized boolean inserisciEmozione(String userID, String emozioneScelta, String titoloCanzone, String autoreCanzone, String notaEmozione, String spiegazioneEmozione,  int punteggioEmozione) throws SQLException {
        Connection insertEmozione = null;
        PreparedStatement preparedStatement = null;
        try{
            //apro la connessione con il DB

            insertEmozione = new ConnessioneDBImpl().getConnection();

            String queryLogin= "INSERT INTO associa (idutente, nome, titolo, autore, nota, spiegazione, punteggio ) VALUES (?, ?, ?, ?, ?, ?, ?)";
            preparedStatement=insertEmozione.prepareStatement(queryLogin);

            preparedStatement.setString(1,userID);
            preparedStatement.setString(2,emozioneScelta);
            preparedStatement.setString(3,titoloCanzone);
            preparedStatement.setString(4,autoreCanzone);
            preparedStatement.setString(5,notaEmozione);
            preparedStatement.setString(6,spiegazioneEmozione);
            preparedStatement.setInt(7,punteggioEmozione);

            //eseguo la query
            preparedStatement.executeUpdate();

            preparedStatement.close();
            insertEmozione.close();

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
            if (insertEmozione != null) {
                insertEmozione.close();
            }
        }

    }
    public synchronized boolean eliminaCanzoniPlaylist(String nomePlaylist, String idUtente, String titoloCanzone, String autoreCanzone) throws SQLException {

        Connection eliminaCanzone = null;
        PreparedStatement preparedStatement = null;
        try {
            eliminaCanzone= new ConnessioneDBImpl().getConnection();
            String query = "DELETE FROM Composta WHERE nome=?  AND idUtente=? AND titolo=? AND autore=? ";

            preparedStatement = eliminaCanzone.prepareStatement(query);
            preparedStatement.setString(1, nomePlaylist );
            preparedStatement.setString(2, idUtente );
            preparedStatement.setString(3,titoloCanzone);
            preparedStatement.setString(4,autoreCanzone);

            preparedStatement.executeQuery();
            System.out.println("cancellazione della canzone dalla playlist effettuata: ");
            return true;
        }catch(Exception e){
            System.out.println("Errore durante la cancellazione della canzone dalla playlist: " + e.getMessage());
            return false;
        }
        finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (eliminaCanzone != null) {
                eliminaCanzone.close();
            }
        }
    }

    public static void main(String[] args) throws RemoteException {

        /**faccio partire il server**/

        System.out.println("Server in preparazione: ");
        ServerImpl sevimpl = new ServerImpl();
        Registry registro;
        try {
            registro = LocateRegistry.createRegistry(1099);
            registro.rebind("ServerEmotionalSongs", sevimpl);
            System.out.println("Server avviato");
        } catch (Exception e) {
            System.out.println("ERRORE!!! Server non Avviato");
            System.out.println(e.getMessage().toString());
        }

        /** connessione DB parte quando patrte il server**/

        ConnessioneDBImpl connection = new ConnessioneDBImpl();
        Connection dbConnection= connection.getConnection();
        if (dbConnection != null) {
            System.out.println("Connessione al database effettuata con successo");

            // Fai altre operazioni necessarie con la connessione al database
        } else {
            System.out.println("Connessione al database NON effettuata");
            // Gestisci il fallimento della connessione al database
        }

    }
}
