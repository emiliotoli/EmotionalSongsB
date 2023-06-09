package ClientES;

import javax.print.DocFlavor;
import java.io.Serializable;

public class Utente implements Serializable {
    private static final long serialVersionUID = 1L;

    private  String nome;
    private  String cognome;
    private  String codiceFiscale;
    private  String via;
    private  String numeroCivico;
    private  String comune;
    private  String provincia;
    private  String  cap;
    private  String email;
    private  String userID;
    private  String password;



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
    public Utente(String nome, String cognome, String codiceFiscale, String via, String numeroCivico, String cap, String comune, String provincia, String email, String userID, String password) {
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

    /**
     * metodo che permette l'output della stringa nome
     * @return string rappresentante il cap
     */
    public  String  getNome(){
        return nome;
    }
    public  String getCognome(){
        return cognome;
    }
    public  String getCodiceFiscale(){return codiceFiscale;}
    public  String getVia(){return via;}
    public  String getNumeroCivico(){return numeroCivico;}
    public  String getComune(){return comune;}
    public  String getProvincia(){return provincia;}
    public  String getCap(){return cap;}
    public  String getEmail(){return email;}
    public  String getUserID(){return userID;}
    public  String getPassword(){return password;}
}

