package ClientES;

/**
 * @author Emilio Daverio
 *         Interfaccia per la gestione dei risultati del login.
 *
 *         Questa interfaccia definisce un metodo di callback che verrà chiamato
 *         quando il risultato del tentativo di login sarà disponibile.
 */
public interface LoginCallback {

    /**
     * @author Emilio Daverio
     *         Questo metodo viene chiamato quando il risultato del tentativo di
     *         login è disponibile.
     *
     * @param success True se il login è riuscito con successo, altrimenti False.
     */
    void onLoginResult(boolean success);
}
