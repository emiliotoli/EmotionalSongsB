/**
 * Il package 'DataBase' contiene le classi e le interfacce necessarie per gestire
 * la connessione a un database tramite RMI (Remote Method Invocation) e JDBC (Java Database Connectivity).
 */

package DataBase;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.Connection;

/**
 * L'interfaccia 'ConnessioneDBInterface' definisce un contratto remoto per
 * ottenere una connessione al database.
 * Gli oggetti che implementano questa interfaccia possono essere utilizzati per
 * ottenere connessioni remote al database
 * utilizzando RMI.
 *
 * @author Emilio Daverio
 */
public interface ConnessioneDBInterface extends Remote {

    /**
     * @author Emilio Daverio
     *         Restituisce una connessione al database.
     *
     * @return Un oggetto 'Connection' che rappresenta una connessione al database.
     * @throws RemoteException Lanciato in caso di errore durante l'invocazione
     *                         remota.
     */
    Connection getConnection() throws RemoteException;
}
