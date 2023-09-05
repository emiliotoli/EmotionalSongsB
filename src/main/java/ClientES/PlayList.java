package ClientES;

import javax.print.DocFlavor;
import java.io.Serializable;

/**
 * @author Stefano Farina
 *         La classe PlayList rappresenta una playlist e contiene informazioni
 *         su di essa, come il nome della playlist e l'ID dell'utente
 *         proprietario.
 *         Gli oggetti PlayList vengono utilizzati per la gestione delle
 *         playlist all'interno dell'applicazione.
 */
public class PlayList implements Serializable {
    private static final long serialVersionUID = 1L;

    private String nomePlaylist;
    private String userId;

    /**
     * @author Stefano Farina
     *         Costruttore per la classe PlayList che permette la creazione di un
     *         oggetto PlayList specificando l'ID dell'utente proprietario e il nome
     *         della playlist.
     *
     * @param user L'ID dell'utente proprietario della playlist.
     * @param name Il nome della playlist.
     */
    public PlayList(String user, String name) {
        this.userId = user;
        this.nomePlaylist = name;
    }

    /**
     * @author Stefano Farina
     *         Metodo che restituisce il nome della playlist.
     *
     * @return Il nome della playlist.
     */
    public String getnomePlalist() {
        return nomePlaylist;
    }

    /**
     * @author Stefano Farina
     *         Metodo che restituisce le informazioni della playlist.
     *
     * @return Le informazioni della playlist.
     */
    public String getInfo() {
        return userId + nomePlaylist;
    }

}
