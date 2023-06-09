package DataBase;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.*;


public class ConnessioneDBImpl extends UnicastRemoteObject implements ConnessioneDBInterface {
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/EmotionalSongs_lab_B";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "Kira0109@!";
    private static ConnessioneDBImpl instance;

    public ConnessioneDBImpl() throws RemoteException {
        super();
    }

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


    public  Connection getConnection() throws RemoteException {
        Connection connection = null;

        try {
            connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            System.out.println("Connessione al DB riuscita");
            System.out.println("Connesso al DB");
        } catch (SQLException e) {
            System.out.println("Connessione al DB fallita");
            e.printStackTrace();
        }
        return connection;
    }
}
