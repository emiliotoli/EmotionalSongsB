/**
 * La classe 'ConnessioneDBImpl' rappresenta un'implementazione concreta
 * dell'interfaccia 'ConnessioneDBInterface' per la gestione delle connessioni
 * a un database tramite JDBC (Java Database Connectivity).
 */

package DataBase;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.*;


/**
 * Questa classe implementa l'interfaccia 'ConnessioneDBInterface' e fornisce un modo per ottenere
 * una connessione al database PostgreSQL utilizzando JDBC.
 *
 * @author Emilio Daverio
 */
public class ConnessioneDBImpl extends UnicastRemoteObject implements ConnessioneDBInterface {
    //private static final String DB_URL = "jdbc:postgresql://localhost:5432/EmotionalSongs_lab_B";
    /*
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/EmotionalSongs_lab_B";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "Kira0109@!";
     */
    private static final String DB_URL = "jdbc:postgresql://172.17.0.2:5432/postgreslab";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "password";
    private static ConnessioneDBImpl instance;



    /**
     * @author Emilio Daverio
     * Costruttore della classe che crea un'istanza di 'ConnessioneDBImpl'.
     *
     * @throws RemoteException Lanciato in caso di errore nella gestione delle operazioni remote.
     */
    public ConnessioneDBImpl() throws RemoteException {
        super();
    }

    /**
     * @author Emilio Daverio
     * Metodo che restituisce un'istanza di 'ConnessioneDBImpl'.
     *
     * @return Un'istanza di 'ConnessioneDBImpl'.
     */
    public static ConnessioneDBImpl getInstance() {
        if (instance == null) {
            synchronized (ConnessioneDBImpl.class) {
                if (instance == null) {
                    try {
                        instance = new ConnessioneDBImpl();
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return instance;
    }


    /**
     * @author Emilio Daverio
     * Metodo che serve per ottenere una connessione al database PostgreSQL.
     *
     * @return  conUn oggetto 'Connection' che rappresenta una connessione al database.
     * @throws RemoteException Lanciato in caso di errore durante l'invocazione remota.
     */
    public  Connection getConnection() throws RemoteException {
        Connection connection = null;

        try {
            connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            //connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            System.out.println("Connessione al DATA_BASE riuscita");
            System.out.println("Connesso al DATA_BASE");
        } catch (SQLException e) {
            System.out.println("ERRORE-->Connessione al DATA_BASE fallita");
            e.printStackTrace();
        }
        return connection;
    }
}
