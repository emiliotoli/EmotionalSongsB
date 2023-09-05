package ClientES;

/**
 * @author Stefano Farina
 *         Interfaccia per la gestione dei risultati del processo di
 *         registrazione.
 *
 *         Questa interfaccia definisce un metodo di callback che verrà chiamato
 *         quando il risultato del tentativo di registrazione sarà disponibile.
 */
public interface RegistrazioneCallback {

    /**
     * @author Stefano Farina
     *         Questo metodo viene chiamato quando il processo di registrazione è
     *         stato completato.
     *
     * @param result Un intero che rappresenta il risultato del processo di
     *               registrazione:
     *
     */
    void registrazioneCompleted(int result);
}
