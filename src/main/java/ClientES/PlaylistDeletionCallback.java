package ClientES;


/**@author Stefano Farina
 * Interfaccia per la gestione dei risultati dell'eliminazione di una playlist.
 *
 * Questa interfaccia definisce un metodo di callback che verrà chiamato
 * quando il risultato del tentativo di eliminazione di una playlist sarà disponibile.
 */
public interface PlaylistDeletionCallback {

    /**
     * Questo metodo viene chiamato quando il risultato dell'eliminazione di una playlist è disponibile.
     *
     * @param result Un intero che rappresenta il risultato dell'operazione:
     *
     */
    void onPlaylistDeletionResult(int result);
}
