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
    private static String  cap;
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
    public Utente(String nome, String cognome, String codiceFiscale, String via, String numeroCivico,String cap,String comune, String provincia, String email, String userID, String password) {
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
    }

    /**
     * metodo che permette l'output della stringa nome
     * @return string rappresentante il cap
     */
    public static String  getNome(){
        return nome;
    }
    public static String getCocnome(){
        return cognome;
    }
    public static String getCodiceFiscale(){return codiceFiscale;}
    public static String getVia(){return via;}
    public static String getNumeroCivico(){return numeroCivico;}
    public static String getComun(){return comune;}
    public static String getProvincia(){return provincia;}
    public static String getCap(){return cap;}
    public static String getEmail(){return email;}
    public static String getUserID(){return userID;}
    public static String getPassword(){return password;}
    //public static String getLogin(){return userID+password;}

}

