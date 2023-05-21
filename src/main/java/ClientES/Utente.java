package ClientES;

import javax.print.DocFlavor;
import java.io.Serializable;

public class Utente implements Serializable {
    private static final long serialVersionUid = 1L;

    private static String nome;
    private static String cognome;
    private static String codiceFiscale;
    private static String via;
    private static String numeroCivico;
    private static String comune;
    private static String provincia;
    private static int  cap;
    private static String email;
    private static String userID;
    private static String password;



    public Utente(){

    }

    /**
     * costruttore che permette la creazione di un oggetto Utente passando i parametri privati che contengono i dati dell'utente
     * i rispettivi valori
     * @param
     * @param
     *
     *
     */
    public Utente(String nome, String cognome, String codiceFiscale, String via, String numeroCivico, String comune, String provincia, int cap, String email, String userID, String password) {
        Utente.nome = nome;
        Utente.cognome=cognome;
        Utente.codiceFiscale=codiceFiscale;
        Utente.via=via;
        Utente.numeroCivico=numeroCivico;
        Utente.comune=comune;
        Utente.provincia=provincia;
        Utente.cap=cap;
        Utente.email=email;
        Utente.userID=userID;
        Utente.password =password;
        //Utente.password=password;


    }

    /**
     * metodo che permette l'output della stringa nome
     * @return string rappresentante il cap
     */
    public String getNome(){
        return nome;
    }

    public String getCocnome(){
        return cognome;
    }

    public String getCodiceFiscale(){return codiceFiscale;}

    public String getVia(){return via;}

    public String getNumeroCivico(){return numeroCivico;}

    public String getComun(){return comune;}

    public String getProvincia(){return provincia;}

    public int getCap(){return cap;}






}

