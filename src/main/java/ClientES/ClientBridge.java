package ClientES;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import serverES.ServerInterfaceNonLoggato;
import serverES.ServerInterfaceLoggato;

public class ClientBridge {
    private static ServerInterfaceNonLoggato interfaceNonLoggato;
    private static ServerInterfaceLoggato interfaceLoggato;

    public static void initialize() throws RemoteException, NotBoundException {
        Registry registroNonLoggato = LocateRegistry.getRegistry(1099);
        interfaceNonLoggato = (ServerInterfaceNonLoggato) registroNonLoggato.lookup("ServerEmotionalSongs");

        Registry registroLoggato = LocateRegistry.getRegistry(1099);
        interfaceLoggato = (ServerInterfaceLoggato) registroLoggato.lookup("ServerEmotionalSongs");
    }

    public static void setInterfaceNonLoggato(ServerInterfaceNonLoggato intf) {
        interfaceNonLoggato = intf;
    }

    public static ServerInterfaceNonLoggato getInterfaceNonLoggato() {
        return interfaceNonLoggato;
    }
}
