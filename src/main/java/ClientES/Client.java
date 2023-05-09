package ClientES;



import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;
import serverES.ServerInterfaceOperazioniComuni;
import serverES.ServerInterfaceLoggato;
import serverES.ServerInterfaceNonLoggato;



public class Client implements MetodiControlli_Client {

    Scanner scanner =new Scanner(System.in);

    //attributi



    /**
     *
     * @param
     * @author Emilio DAverio
     * al costrutore della classe passo come argomento lo userName dell'utente, così da essere in grado di distinguere i vari client
     */
    public Client(){
    }

    /**
     * @author Emilio Daverio
     * Il metodo exec serve per creare una connessione con il Server, e gestisco le risposte che ricevo dal server
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws NotBoundException
     */
    public void exec() throws  IOException, ClassNotFoundException, NotBoundException{

    }

    public static void main(String[] args) throws ClassNotFoundException, IOException, NotBoundException {
        String identifier = null;
        new Client().exec();
    }
}
