package ClientES;

import java.io.Serializable;

/**@author Emilio Daverio
 * La classe Canzone rappresenta una canzone e contiene informazioni su di essa, come il titolo, l'autore e l'anno di pubblicazione.
 * Gli oggetti Canzone vengono utilizzati per la gestione delle canzoni all'interno dell'applicazione.
 */
public class Canzone implements Serializable {
    private static final long serialVersionUID = 1L;

    private  String titoloCanzone;
    private  String autoreCanzone;
    private  int annoCanzone;

    /**@author Emilio Daverio
     * Costruttore per la classe Canzone che permette la creazione di un oggetto Canzone specificando il titolo, l'autore e l'anno di pubblicazione.
     *
     * @param name  Il titolo della canzone.
     * @param autore  L'autore della canzone.
     * @param anno  L'anno di pubblicazione della canzone.
     */
    public Canzone (String name, String autore, int anno){
        titoloCanzone=name;
        autoreCanzone=autore;
        annoCanzone=anno;
    }

    /**@author Emilio Daverio
     * Costruttore per la classe Canzone che permette la creazione di un oggetto Canzone specificando il titolo e l'autore.
     *
     * @param name  Il titolo della canzone.
     * @param autore  L'autore della canzone.
     */
    public Canzone (String name, String autore){
        titoloCanzone=name;
        autoreCanzone=autore;
    }

    /**@author Emilio Daverio
     * Metodo che restituisce il titolo della canzone.
     *
     * @return Il titolo della canzone.
     */
    public  String getTitoloCanzone(){return titoloCanzone;}


    /**@author Emilio Daverio
     * Metodo che restituisce il nome dell'autore della canzone.
     *
     * @return Il nome dell'autore della canzone.
     */
    public  String getAutoreCanzone(){return autoreCanzone;}


    /**@author Emilio Daverio
     * Metodo che restituisce l'anno della canzone.
     *
     * @return L'anno dell'autore della canzone.
     */
    public  int getAnnoCanzone(){return annoCanzone;}
}