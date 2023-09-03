package ClientES;

import javax.print.DocFlavor;
import java.io.Serializable;

/**@author Emilio Daverio
 * La classe Utente rappresenta un utente del sistema e contiene tutte le informazioni relative all'utente.
 * Gli oggetti Utente vengono utilizzati per la gestione dell'autenticazione e dei dati dell'utente.
 */
public class Utente implements Serializable {
    private static final long serialVersionUID = 1L;

    private String nome;
    private String cognome;
    private String codiceFiscale;
    private String via;
    private String numeroCivico;
    private String comune;
    private String provincia;
    private String cap;
    private String email;
    private String userID;
    private String password;

    /**
     * Costruttore vuoto per la classe Utente.
     */
    public Utente() {

    }

    /**@author Emilio Daverio
    * Costruttore che permette la creazione di un oggetto Utente passando i parametri privati che contengono i dati dell'utente.
    *
    * @param nome          Il nome dell'utente.
    * @param cognome       Il cognome dell'utente.
    * @param codiceFiscale Il codice fiscale dell'utente.
    * @param via           Il nome della via dell'utente.
    * @param numeroCivico  Il numero civico dell'utente.
    * @param cap           Il codice di avviamento postale dell'utente.
    * @param comune        Il comune di residenza dell'utente.
    * @param provincia     La provincia di residenza dell'utente.
    * @param email         L'indirizzo email dell'utente.
     * @param userID        L'identificativo dell'utente.
     * @param password      La password dell'utente.
            */
    public Utente(String nome, String cognome, String codiceFiscale, String via, String numeroCivico, String cap,
            String comune, String provincia, String email, String userID, String password) {
        this.nome = nome;
        this.cognome = cognome;
        this.codiceFiscale = codiceFiscale;
        this.via = via;
        this.numeroCivico = numeroCivico;
        this.cap = cap;
        this.comune = comune;
        this.provincia = provincia;
        this.email = email;
        this.userID = userID;
        this.password = password;
    }

    /**@author Emilio Daverio
     * Metodo che restituisce il nome dell'utente.
     *
     * @return Il nome dell'utente.
     */
    public String getNome() {
        return nome;
    }


    /**@author Emilio Daverio
     * Metodo che restituisce il cognome dell'utente.
     *
     * @return Il cognome dell'utente.
     */
    public String getCognome() {
        return cognome;
    }


    /**@author Emilio Daverio
     * Metodo che restituisce il codicefiscale dell'utente.
     *
     * @return Il codicefiscale dell'utente.
     */
    public String getCodiceFiscale() {
        return codiceFiscale;
    }


    /**@author Emilio Daverio
     * Metodo che restituisce la via dell'utente.
     *
     * @return La via dell'utente.
     */
    public String getVia() {
        return via;
    }


    /**@author Emilio Daverio
     * Metodo che restituisce il numero civico dell'utente.
     *
     * @return Il  numero civico dell'utente.
     */
    public String getNumeroCivico() {
        return numeroCivico;
    }


    /**@author Emilio Daverio
     * Metodo che restituisce il comune dell'utente.
     *
     * @return Il comenu dell'utente.
     */
    public String getComune() {
        return comune;
    }


    /**@author Emilio Daverio
     * Metodo che restituisce la provincia dell'utente.
     *
     * @return La provincia dell'utente.
     */
    public String getProvincia() {
        return provincia;
    }


    /**@author Emilio Daverio
     * Metodo che restituisce il cap dell'utente.
     *
     * @return Il cap dell'utente.
     */
    public String getCap() {
        return cap;
    }


    /**@author Emilio Daverio
     * Metodo che restituisce la mail dell'utente.
     *
     * @return la mail dell'utente.
     */
    public String getEmail() {
        return email;
    }


    /**@author Emilio Daverio
     * Metodo che restituisce lo userID dell'utente.
     *
     * @return lo userID dell'utente.
     */
    public String getUserID() {
        return userID;
    }


    /**@author Emilio Daverio
     * Metodo che restituisce la password dell'utente.
     *
     * @return La password dell'utente.
     */
    public String getPassword() {
        return password;
    }
}
