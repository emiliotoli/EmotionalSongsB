package ClientES;

import serverES.ServerInterface;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;


public class Client implements MetodiControlli_Client {

    Scanner scanner =new Scanner(System.in);

    //attributi
    private int varswich;
    private String TestoMenu = null;
    private final boolean loggato=false;
    private String userName;


    /**
     *
     * @param user
     * @author Emilio DAverio
     * al costrutore della classe passo come argomento lo userName dell'utente, così da essere in grado di distinguere i vari client
     */
    public Client(String user){

        this.userName=user;
    }

    /**
     * @author Emilio Daverio
     * Il metodo exec serve per creare una connessione con il Server, e gestisco le risposte che ricevo dal server
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws NotBoundException
     */
    public void exec() throws  IOException, ClassNotFoundException, NotBoundException{

        System.out.print("Inserisci lo username: ");
        String username = scanner.nextLine();
        System.out.print("Inserisci la password: ");
        String password = scanner.nextLine();


        /**comandi per collegarmi al server**/
        Registry registro = LocateRegistry.getRegistry(1099);
        ServerInterface remoteServerES = (ServerInterface) registro.lookup("ServerEmotionalSongs");

        //richiamo il metodo del Server
        remoteServerES.login(username,password);



    }

    public static void main(String[] args) throws ClassNotFoundException, IOException, NotBoundException {
        String identifier = null;
        new Client(identifier).exec();
    }
}
