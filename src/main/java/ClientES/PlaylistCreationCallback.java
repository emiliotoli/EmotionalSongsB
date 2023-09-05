package ClientES;

/**@author Emilio Daverio
 * Interfaccia per la gestione dei risultati della creazione di una playlist.
 *
 * Questa interfaccia definisce un metodo di callback che verrà chiamato
 * quando il risultato del tentativo di creazione di una playlist sarà disponibile.
 */
public interface PlaylistCreationCallback {

    /**@author Emilio Daverio
     * Questo metodo viene chiamato quando il risultato della creazione di una playlist è disponibile.
     *
     * @param result Un intero che rappresenta il risultato dell'operazione:
     *
     */
    void onPlaylistCreationResult(int result);
}
