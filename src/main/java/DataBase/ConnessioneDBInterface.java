package DataBase;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.Connection;

public interface ConnessioneDBInterface extends Remote {
    Connection getConnection() throws RemoteException;
}
