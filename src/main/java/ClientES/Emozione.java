package ClientES;

import java.io.Serializable;

/**@author Stefano Farina
 * La classe Emozione rappresenta un'emozione assiociata ad una canzone e contiene informazioni su di essa, come il nome, il tipo, la spiegazione,
 * il punteggio e la percentuale di emozione.
 * Gli oggetti Emozione vengono utilizzati per la registrazione e la visualizzazione delle emozioni associate a una canzone.
 */
public class Emozione implements Serializable {
    private static final long serialVersionUID = 1L;

    private String nomeEmozione;
    private String tipoEmozione;
    private String spiegazioneEmozione;
    private int punteggioEmozione;
    private double percentualeEmozione;

    /**@author Stefano Farina
     * Costruttore vuoto per la classe Emozione.
     */
    public Emozione() {
    }

    /**@author Stefano Farina
     * Costruttore che permette la creazione di un oggetto Emozione passando i parametri relativi all'emozione.
     *
     * @param nome        Il nome dell'emozione.
     * @param tipo        Il tipo di emozione (es. "Positiva", "Negativa").
     * @param spiegazione Una spiegazione dettagliata dell'emozione.
     * @param valore      Il punteggio dell'emozione.
     * @param percentuale La percentuale di emozione.
     */
    public Emozione(String nome, String tipo, String spiegazione, int valore, double percentuale) {
        this.nomeEmozione = nome;
        this.tipoEmozione = tipo;
        this.spiegazioneEmozione = spiegazione;
        this.punteggioEmozione = valore;
        this.percentualeEmozione = percentuale;
    }

    /**@author Stefano Farina
     * Costruttore che permette la creazione di un oggetto Emozione passando il nome e la percentuale di emozione.
     *
     * @param nome        Il nome dell'emozione.
     * @param percentuale La percentuale di emozione.
     */
    public Emozione(String nome, double percentuale) {
        this.nomeEmozione = nome;
        this.percentualeEmozione = percentuale;
    }

    /**@author Stefano Farina
     * Metodo che restituisce il nome dell'emozione.
     *
     * @return Il nome dell'emozione.
     */
    public String getNomeEmozione() {
        return nomeEmozione;
    }


    /**@author Stefano Farina
     * Metodo che restituisce il nome dell'emozione.
     *
     * @return Il nome dell'emozione.
     */
    public String getTipoEmozione() {
        return tipoEmozione;
    }


    /**@author Stefano Farina
     * Metodo che restituisce la spiegazione dell'emozione dell'emozione.
     *
     * @return La spiegazione dell'emozione.
     */
    public String getSpiegazioneEmozione() {
        return spiegazioneEmozione;
    }


    /**@author Stefano Farina
     * Metodo che restituisce il punteggio dell' emozione dell'emozione.
     *
     * @return Il punteggio dell' emozione dell'emozione.
     */
    public int getPunteggioEmozione() {
        return punteggioEmozione;
    }


    /**@author Stefano Farina
     * Metodo che restituisce la percentuale dell'emozione dell'emozione.
     *
     * @return La percentuale dell'emozione dell'emozione.
     */
    public double getPercentualeEmozione() {
        return percentualeEmozione;
    }

}
